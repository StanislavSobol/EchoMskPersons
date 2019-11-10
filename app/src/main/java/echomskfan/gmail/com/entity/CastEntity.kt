package echomskfan.gmail.com.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class CastEntity(
    @PrimaryKey
    val fullTextURL: String,
    val personId: Int, // FK
    val pageNum: Int,
    val type: String, // Интервью
    val subtype: String, // Персонально Ваш
    val shortText: String,
    val mp3Url: String,
    val mp3Duration: Int,
    val formattedDate: String,
    val date: Date? = null,
    var fav: Boolean = false // reserved
)