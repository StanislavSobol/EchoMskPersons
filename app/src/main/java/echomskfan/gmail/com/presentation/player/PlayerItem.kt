package echomskfan.gmail.com.presentation.player

data class PlayerItem(
    val castId: String,
    val personName: String,
    val personPhotoUrl: String,
    val typeSubtype: String,
    val formattedDate: String,
    val mp3Url: String,
    val mp3Duration: Int
)

