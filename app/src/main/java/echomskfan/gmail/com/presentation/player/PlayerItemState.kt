package echomskfan.gmail.com.presentation.player

internal object PlayerItemVisualState {

    var isClosed: Boolean = false
        private set

    var isPlaying: Boolean = false
        private set

    var progressMSec: Int = 0
        private set


    fun play(): PlayerItemVisualState {
        isPlaying = true
        isClosed = false
        return this
    }

    fun resume() = play()

    fun pause(): PlayerItemVisualState {
        isPlaying = false
        isClosed = false
        return this
    }

    fun track(progressMSec: Int): PlayerItemVisualState {
        this.progressMSec = progressMSec
        isPlaying = true
        isClosed = false
        return this
    }

    fun stop() = pause()

    fun close(): PlayerItemVisualState {
        isPlaying = false
        isClosed = true
        return this
    }
}
