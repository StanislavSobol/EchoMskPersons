package echomskfan.gmail.com.presentation.player

import echomskfan.gmail.com.presentation.casts.CastListItem
import java.io.Serializable

data class PlayerItem(
    val typeSubtype: String,
    val mp3Url: String,
    val mp3Duration: Int
) : Serializable {

    companion object {
        const val EXTRA_KEY = "PlayerItem"

        fun from(castListItem: CastListItem) = PlayerItem(
            typeSubtype = castListItem.typeSubtype,
            mp3Url = castListItem.mp3Url,
            mp3Duration = castListItem.mp3Duration
        )
    }
}


