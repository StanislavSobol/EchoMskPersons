package echomskfan.gmail.com.presentation.player

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import echomskfan.gmail.com.presentation.main.MainActivity
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.logInfo

/**
 * Local class-bridge between MainActivity and the service. Located in PlayerFragment
 */
internal class PlayerBridge(private val playerFragment: PlayerFragment) {

    private var mediaPlayerServiceIntent: Intent? = null
    private var mediaPlayerServiceConnection: ServiceConnection? = null
    private var mediaPlayerService: MediaPlayerService? = null
    private var playerItem: PlayerItem? = null

    fun bindServiceAndPlay(playerItem: PlayerItem) {
        this.playerItem = playerItem
        mediaPlayerService ?: bindService()
    }

    fun bindServiceAndResume() {
        playerItem = null
        mediaPlayerService ?: bindService()
    }

    private fun bindService() {
        getMainActivity().let {
            mediaPlayerServiceIntent = Intent(it, MediaPlayerService::class.java)
            mediaPlayerServiceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName, service: IBinder) {
                    mediaPlayerService = (service as MediaPlayerService.MediaServiceBinder).service
                    mediaPlayerService!!.playerBridge = this@PlayerBridge
                    if (playerItem != null) {
                        mediaPlayerService?.play(playerItem!!)
                    } else {
                        playerItem = mediaPlayerService?.playerItem
                        mediaPlayerService?.resume()
                    }
                }

                override fun onServiceDisconnected(name: ComponentName) {
                    mediaPlayerService = null
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.startForegroundService(mediaPlayerServiceIntent)
                logInfo("bridge startForegroundService")
            } else {
                it.startService(mediaPlayerServiceIntent)
            }

            it.bindService(
                mediaPlayerServiceIntent,
                mediaPlayerServiceConnection as ServiceConnection, BIND_AUTO_CREATE
            )
        }
    }

    fun notifyPlayerItemChanged() {
        if (PlayerItemVisualState.isClosed) {
            mediaPlayerServiceConnection?.let { getMainActivity().unbindService(it) }
        }
        playerFragment.notifyPlayerItemChanged()
    }

    fun seekTo(progress: Int) {
        mediaPlayerService?.seekTo(progress)
    }

    fun resume() {
        mediaPlayerService?.resume()
    }

    fun pause() {
        mediaPlayerService?.pause()
    }

    fun unbindService() {
        mediaPlayerServiceConnection?.let {
            mediaPlayerService?.playerBridge = null
            try {
                getMainActivity().unbindService(it)
            } catch (e: IllegalArgumentException) {
                catchThrowable(e)
            }
        }
    }

    private fun getMainActivity() = playerFragment.requireActivity() as MainActivity
}