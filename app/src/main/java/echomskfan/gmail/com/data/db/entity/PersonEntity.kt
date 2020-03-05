package echomskfan.gmail.com.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO PersonEntity: Separate table and columns names from the class and fields names
@Entity
data class PersonEntity(
    @PrimaryKey val id: Int,
    val url: String,
    val firstName: String,
    val lastName: String,
    val profession: String,
    val info: String,
    val photoUrl: String,
    var fav: Boolean = false,
    var notification: Boolean = false
) {
    fun getFullName() = ("$firstName $lastName").trim()
}

