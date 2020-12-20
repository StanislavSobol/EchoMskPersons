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
    private var playerService: PlayerService? = null
    private var playerItem: PlayerItem? = null
    private var bound = false

    fun bindServiceAndPlay(playerItem: PlayerItem) {
        this.playerItem = playerItem
        playerService ?: bindService()
    }

    fun bindServiceAndResume() {
        playerItem = null
        playerService ?: bindService()
    }

    private fun bindService() {
        getMainActivity().let {
            mediaPlayerServiceIntent = Intent(it, PlayerService::class.java)
            mediaPlayerServiceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName, service: IBinder) {
                    playerService = (service as PlayerService.MediaServiceBinder).service
                    playerService!!.playerBridge = this@PlayerBridge
                    if (playerItem != null) {
                        playerService?.play(playerItem!!)
                    } else {
                        playerItem = playerService?.playerItem
                        playerService?.resume()
                    }

                    bound = true
                }

                override fun onServiceDisconnected(name: ComponentName) {
                    playerService = null
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
        playerFragment.notifyPlayerItemChanged()
        if (PlayerItemVisualState.isClosed) {
            mediaPlayerServiceConnection?.let { getMainActivity().unbindService(it) }
        }
    }

    fun seekTo(progress: Int) {
        playerService?.seekTo(progress)
    }

    fun resume() {
        playerService?.resume()
    }

    fun pause() {
        playerService?.pause()
    }

    fun unbindService() {
        if (bound) {
            mediaPlayerServiceConnection?.let {
                playerService?.playerBridge = null
                try {
                    bound = false
                    getMainActivity().unbindService(it)
                } catch (e: IllegalArgumentException) {
                    catchThrowable(e)
                }
            }
        }
    }

    fun mp3Loaded() {
        playerFragment.mp3Loaded()
    }

    private fun getMainActivity() = playerFragment.requireActivity() as MainActivity
}