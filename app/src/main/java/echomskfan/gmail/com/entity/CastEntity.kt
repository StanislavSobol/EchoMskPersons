package echomskfan.gmail.com.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CastEntity(
    @PrimaryKey
    val fullTextURL: String,
    val personId: Int, // FK
    val type: String, // Интервью
    val subtype: String, // Персонально Ваш
    val shortText: String,
    val mp3Url: String,
    val mp3Duration: Int,
    val formattedDate: String,
    val pageNum: Int,
    var fav: Boolean = false // reserved
)