package echomskfan.gmail.com.presentation.player

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Binder
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.IdRes
import androidx.core.app.NotificationCompat
import echomskfan.gmail.com.R
import echomskfan.gmail.com.presentation.MainActivity
import echomskfan.gmail.com.utils.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.io.IOException
import java.util.concurrent.TimeUnit

class MediaPlayerService : Service() {

    internal var playingItem: PlayerItem? = null
    internal var playerBridge: PlayerBridge? = null

    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }
    private var intervalDisposable: Disposable? = null
    private var notificationRemoteView: RemoteViews? = null
    private var notificationBuilder: NotificationCompat.Builder? = null
    private var actionsBroadCastReceiver: BroadcastReceiver? = null

    override fun onCreate() {
        super.onCreate()

        val filter = IntentFilter()
        filter.addAction(RESUME_ACTION)
        filter.addAction(PAUSE_ACTION)
        filter.addAction(CLOSE_ACTION)

        actionsBroadCastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.action.let {
                    when (it) {
                        RESUME_ACTION -> {
                            resume()
                            PlayerItemVisualState.resume().applyRemoteViewsAppearance()
                            playerBridge?.notifyPlayerItemChanged()
                        }
                        PAUSE_ACTION -> {
                            pause()
                            PlayerItemVisualState.pause().applyRemoteViewsAppearance()
                            playerBridge?.notifyPlayerItemChanged()
                        }
                        CLOSE_ACTION -> {
                            stop()
                            PlayerItemVisualState.close()
                            stopForeground(true)
                            stopSelf()
                            playerBridge?.notifyPlayerItemChanged()
                        }
                        else -> {
                            logInfo("Unknown broadcast from remote views")
                        }
                    }
                }
            }
        }
        registerReceiver(actionsBroadCastReceiver, filter)
    }

    override fun onDestroy() {
        stop()
        actionsBroadCastReceiver?.let { unregisterReceiver(it) }
        logInfo("onDestroy service")
        super.onDestroy()
    }

    override fun onBind(p0: Intent?) = MediaServiceBinder()

    fun play(playingItem: PlayerItem) {
        this.playingItem = playingItem
        try {
            try {
                mediaPlayer.reset()
            } catch (e: IllegalStateException) {
                catchThrowable(e)
            }
            mediaPlayer.setDataSource(playingItem.mp3Url)
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
                startForeground()
                startTracking()
            }
            mediaPlayer.prepareAsync()
        } catch (e: IOException) {
            catchThrowable(e)
        }

        PlayerItemVisualState.play()
    }

    fun stop() {
        mediaPlayer.stop()
        PlayerItemVisualState.stop().applyRemoteViewsAppearance()
        intervalDisposable?.dispose()
        playerBridge?.notifyPlayerItemChanged()
    }

    fun pause() {
        mediaPlayer.pause()
        PlayerItemVisualState.pause().applyRemoteViewsAppearance()
        intervalDisposable?.dispose()
        playerBridge?.notifyPlayerItemChanged()
    }

    fun resume() {
        mediaPlayer.start()
        PlayerItemVisualState.resume().applyRemoteViewsAppearance()
        startTracking()
        playerBridge?.notifyPlayerItemChanged()
    }

    fun seekTo(progress: Int) {
        mediaPlayer.seekTo(progress * 1000)
    }

    private fun startForeground() {
        if (playingItem != null) {
            val appName = applicationContext.getString(R.string.app_name)
            val notificationIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

            notificationRemoteView = RemoteViews(this.packageName, R.layout.media_player_notification)

            if (notificationRemoteView == null) {
                catchThrowable(IllegalStateException("MediaPlayerService: remote views are not created"))
                return
            }

            initNotificationRemoteView()

            notificationBuilder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.drawable.ic_baseline_play_white_24dp)
                .setContentTitle(appName)
                .setContentText(playingItem?.typeSubtype)
                .setContent(notificationRemoteView)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)

            notificationBuilder?.build()?.let {
                it.flags = it.flags or Notification.FLAG_ONGOING_EVENT
                it.tickerText = appName + " " +
                        applicationContext.getString(R.string.notification_ticket)
                startForeground(NOTIFICATION_ID, it)
            }
        }
    }

    private fun initNotificationRemoteView() {
        bindButton(
            R.id.notificationPlayButton,
            Intent(this, NotificationPlayButtonHandler::class.java),
            notificationRemoteView!!
        )

        bindButton(
            R.id.notificationPauseButton,
            Intent(this, NotificationPauseButtonHandler::class.java),
            notificationRemoteView!!
        )

        bindButton(
            R.id.notificationCloseButton,
            Intent(this, NotificationCloseButtonHandler::class.java),
            notificationRemoteView!!
        )

        notificationRemoteView?.setTextViewText(R.id.notificationPersonTextView, playingItem?.personName)
        notificationRemoteView?.setTextViewText(R.id.notificationTypeSubtypeTextView, playingItem?.typeSubtype)
        notificationRemoteView?.setTextViewText(R.id.notificationDateTextView, playingItem?.formattedDate)
    }

    private fun bindButton(@IdRes resId: Int, intent: Intent, notificationView: RemoteViews) {
        val buttonPlayPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        notificationView.setOnClickPendingIntent(resId, buttonPlayPendingIntent)
    }

    private fun startTracking() {
        intervalDisposable?.dispose()

        intervalDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .fromComputationToMain()
            .subscribe {
                if (playerBridge == null) {
                    logError("PlayerBridge got lost")
                    intervalDisposable?.dispose()
                    return@subscribe
                }

                PlayerItemVisualState.track(mediaPlayer.currentPosition).applyRemoteViewsAppearance()

                val isPlaying: Boolean = try {
                    mediaPlayer.isPlaying
                } catch (e: IllegalStateException) {
                    false
                }

                if (!isPlaying) {
                    stopForeground(true)
                    stopSelf()
                    intervalDisposable?.dispose()
                }

                playerBridge?.notifyPlayerItemChanged()
            }
    }

    private fun updateNotification() {
        val mNotificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder?.build())
    }

    private fun PlayerItemVisualState.applyRemoteViewsAppearance() {
        if (isPlaying) {
            notificationRemoteView?.setViewVisibility(R.id.notificationPlayButton, View.GONE)
            notificationRemoteView?.setViewVisibility(R.id.notificationPauseButton, View.VISIBLE)
        } else {
            notificationRemoteView?.setViewVisibility(R.id.notificationPlayButton, View.VISIBLE)
            notificationRemoteView?.setViewVisibility(R.id.notificationPauseButton, View.GONE)
        }

        notificationRemoteView?.setTextViewText(
            R.id.notificationTimeCodeTextView,
            progressMSec.fromMSecSec().fromSecToAudioDuration()
        )

        updateNotification()
    }

    inner class MediaServiceBinder : Binder() {
        val service: MediaPlayerService
            get() = this@MediaPlayerService
    }

    class NotificationPlayButtonHandler : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            sendActionBroadCast(context, RESUME_ACTION)
        }
    }

    class NotificationPauseButtonHandler : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            sendActionBroadCast(context, PAUSE_ACTION)
        }
    }

    class NotificationCloseButtonHandler : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            sendActionBroadCast(context, CLOSE_ACTION)
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val RESUME_ACTION = "RESUME_ACTION"
        private const val PAUSE_ACTION = "PAUSE_ACTION"
        private const val CLOSE_ACTION = "CLOSE_ACTION"

        fun sendActionBroadCast(context: Context, action: String) {
            val serviceIntent = Intent()
            serviceIntent.action = action
            context.sendBroadcast(serviceIntent)
        }
    }
}