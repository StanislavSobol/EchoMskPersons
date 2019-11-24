package echomskfan.gmail.com.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import echomskfan.gmail.com.utils.toDate
import java.util.*

@Entity
data class CastEntity(
    @PrimaryKey
    val id: String,
    val fullTextURL: String,
    val personId: Int, // FK
    val pageNum: Int,
    val type: String, // Интервью
    val subtype: String, // Персонально Ваш
    val shortText: String,
    val mp3Url: String,
    val mp3Duration: Int,
    val formattedDate: String,
    val date: Date,
    var fav: Boolean = false
) {
    companion object {
        fun generateKey(personId: Int, formattedDate: String) = "$personId${dateFromString(formattedDate)}"
        fun dateFromString(formattedDate: String) = formattedDate.toDate()?.let { it } ?: Date()
    }
}