package echomskfan.gmail.com.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonEntity(
    @PrimaryKey val id: Int,
    val url: String,
    val firstName: String,
    val lastName: String,
    val profession: String,
    val info: String,
    val photoUrl: String
)

