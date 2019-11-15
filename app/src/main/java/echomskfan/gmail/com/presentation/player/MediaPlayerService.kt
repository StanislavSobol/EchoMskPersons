package echomskfan.gmail.com.presentation.player

import android.app.Service
import android.content.Intent
import android.os.IBinder
import echomskfan.gmail.com.utils.catchThrowable

class MediaPlayerService : Service() {

    private var playerItem: PlayerItem? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        playerItem = intent?.getSerializableExtra(PlayerItem.EXTRA_KEY) as PlayerItem?
        playerItem ?: run {
            try {
                throw IllegalStateException("playerItem must not be null")
            } catch (e: IllegalStateException) {
                catchThrowable(e)
            }
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? = null
}