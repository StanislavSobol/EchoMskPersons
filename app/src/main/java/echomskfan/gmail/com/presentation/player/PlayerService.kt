package echomskfan.gmail.com.presentation.player

import android.annotation.TargetApi
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.IdRes
import androidx.core.app.NotificationCompat
import echomskfan.gmail.com.BuildConfig
import echomskfan.gmail.com.EXTRA_PLAYER_ITEM_CAST_ID
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.presentation.main.MainActivity
import echomskfan.gmail.com.utils.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class PlayerService : Service() {

    internal var playerItem: PlayerItem? = null
    internal var playerBridge: PlayerBridge? = null

    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }
    private var intervalDisposable: Disposable? = null
    private var notificationRemoteView: RemoteViews? = null
    private var notificationBuilder: NotificationCompat.Builder? = null
    private var actionsBroadCastReceiver: BroadcastReceiver? = null

    override fun onCreate() {
        super.onCreate()

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
                            fullStop()
                            playerBridge?.notifyPlayerItemChanged()
                        }
                        else -> {
                            logError("Unknown broadcast from remote views")
                        }
                    }
                }
            }
        }

        registerReceiver(actionsBroadCastReceiver,
            IntentFilter().apply {
                addAction(RESUME_ACTION)
                addAction(PAUSE_ACTION)
                addAction(CLOSE_ACTION)
            }
        )
    }

    override fun onDestroy() {
        stop()

        if (BuildConfig.DEBUG && isInForeground()) {
            logError("Media Player service is still in foreground mode")
        }

        actionsBroadCastReceiver?.let { unregisterReceiver(it) }
        super.onDestroy()
    }

    override fun onBind(p0: Intent?) = MediaServiceBinder()

    fun play(playingItem: PlayerItem) {
        this.playerItem = playingItem
        mediaPlayer.let {
            try {
                try {
                    it.reset()
                } catch (e: IllegalStateException) {
                    catchThrowable(e)
                }
                it.setDataSource(playingItem.mp3Url)
                it.setOnPreparedListener {
                    if (playingItem.playedTimeSec > 0) {
                        it.seekTo(playingItem.playedTimeSec * 1000)
                    }
                    it.start()
                    startForeground()
                    startTracking()
                    playerBridge?.mp3Loaded()
                }
                it.prepareAsync()
            } catch (e: IOException) {
                catchThrowable(e)
            }
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
        playerItem?.let {
            val appName = applicationContext.getString(R.string.app_name)
            val notificationIntent = Intent(this, MainActivity::class.java).apply {
                putExtra(EXTRA_PLAYER_ITEM_CAST_ID, it.castId)
            }
            val pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            notificationRemoteView =
                RemoteViews(this.packageName, R.layout.media_player_notification)

            if (notificationRemoteView == null) {
                catchThrowable(IllegalStateException("MediaPlayerService: remote views are not created"))
                return
            }

            initNotificationRemoteView()

            notificationBuilder = createNotificationBuilder()
                // TODO icon to baseline version
                .setSmallIcon(R.drawable.ic_volume_up_white_24dp)
                .setContentTitle(it.typeSubtype)
                .setContent(notificationRemoteView)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)
                .setOngoing(true)

            notificationBuilder?.build().let { notification ->
                // TODO to string res with format
                notification?.tickerText = appName + " " +
                        applicationContext.getString(R.string.notification_ticket)
                startForeground(NOTIFICATION_ID, notification)
            }
        }
    }

    private fun createNotificationBuilder(): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(applicationContext)
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
                .apply {
                    description = CHANNEL_DESCRIPTION
                    enableLights(true)
                    lightColor = Color.BLUE
                    setSound(null, null)
                }

        getNotificationManager().createNotificationChannel(channel)
    }

    private fun initNotificationRemoteView() {
        fun bindButton(@IdRes resId: Int, intent: Intent, notificationView: RemoteViews) {
            val buttonPlayPendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            notificationView.setOnClickPendingIntent(resId, buttonPlayPendingIntent)
        }

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

        notificationRemoteView?.setTextViewText(
            R.id.notificationPersonTextView,
            playerItem?.personName
        )
        notificationRemoteView?.setTextViewText(
            R.id.notificationTypeSubtypeTextView,
            playerItem?.typeSubtype
        )
        notificationRemoteView?.setTextViewText(
            R.id.notificationDateTextView,
            playerItem?.formattedDate
        )
    }

    private fun startTracking() {
        val castId = playerItem?.castId ?: throw java.lang.IllegalStateException()

        intervalDisposable?.dispose()

        intervalDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .fromComputationToMain()
            .subscribe {
                if (BuildConfig.COROUTINES) {
                    // TODO PlayerSeviceScope
                    GlobalScope.launch {
                        MApplication.getAppComponent().playCoInteractor()
                            .updatePlayedTime(
                                castId,
                                mediaPlayer.currentPosition.fromMilliSecToSec()
                            )
                    }
                } else {
                    MApplication.getAppComponent().playInteractor()
                        .updatePlayedTime(castId, mediaPlayer.currentPosition.fromMilliSecToSec())
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                        .dispose()
                }

                PlayerItemVisualState.track(mediaPlayer.currentPosition)
                    .applyRemoteViewsAppearance()

                val isPlaying: Boolean = try {
                    mediaPlayer.isPlaying
                } catch (e: IllegalStateException) {
                    false
                }

                if (!isPlaying) {
                    pause()
                    intervalDisposable?.dispose()
                }

                playerBridge?.notifyPlayerItemChanged()
            }
    }

    private fun isInForeground(): Boolean {
        val manager =
            applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (javaClass.name == service.service.className) {
                if (service.foreground) {
                    return true
                }
            }
        }
        return false
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
            progressMSec.fromMilliSecToSec().fromSecToAudioDuration()
        )

        getNotificationManager().notify(NOTIFICATION_ID, notificationBuilder?.build())
    }

    private fun fullStop() {
        stop()
        PlayerItemVisualState.close()
        stopForeground(true)
        stopSelf()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager().deleteNotificationChannel(CHANNEL_ID)
        }
    }

    // TODO -> NotificationManagerCompat.from(appContext). ?
    private fun getNotificationManager(): NotificationManager {
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    inner class MediaServiceBinder : Binder() {
        val service: PlayerService
            get() = this@PlayerService
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
        private const val RESUME_ACTION = "resume_action"
        private const val PAUSE_ACTION = "pause_action"
        private const val CLOSE_ACTION = "close_action"

        private const val CHANNEL_NAME = "Echo Msk Persons' media player channel"
        private const val CHANNEL_DESCRIPTION =
            "Echo Msk Persons' media player channel is dedicated to Echo Msk podcasts"

        private val NOTIFICATION_ID = UUID.randomUUID().mostSignificantBits.toInt()
        private val CHANNEL_ID = UUID.randomUUID().toString()

        fun sendActionBroadCast(context: Context, action: String) {
            val serviceIntent = Intent()
            serviceIntent.action = action
            context.sendBroadcast(serviceIntent)
        }
    }
}