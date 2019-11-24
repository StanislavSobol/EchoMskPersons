package echomskfan.gmail.com.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import echomskfan.gmail.com.data.db.entity.CastEntity
import java.util.*

@Dao
interface CastsDao {

    @Query("SELECT * FROM CastEntity WHERE date = :date")
    fun getCastByDate(date: Date): CastEntity?

    @Query("SELECT * FROM CastEntity WHERE personId = :personId ORDER BY date DESC")
    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>

//    @Query("SELECT * FROM CastEntity WHERE fullTextURL = :id")
//    fun getById(id: Int): CastEntity?

//    @Query("DELETE FROM CastEntity WHERE personId = :personId")
//    fun deleteAllForPerson(personId: Int)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    @JvmSuppressWildcards
//    fun insertAll(casts: List<CastEntity>)

    @Query("DELETE FROM CastEntity WHERE (type = '' AND subtype = '')")
    fun removeGarbage()

    @Query("SELECT * FROM CastEntity WHERE personId = :personId ORDER BY date DESC")
    fun getCastsForPerson(personId: Int): List<CastEntity>
//    @Query("SELECT * FROM CastEntity WHERE fullTextURL = :fullTextURL")
//    fun getCastByFullTextUrl(fullTextURL: String):CastEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newCast: CastEntity)

    @Query(
        """UPDATE CastEntity SET
        fullTextURL = :fullTextURL,
        type=:type, subtype=:subtype,
        shortText=:shortText,
        mp3Url=:mp3Url,
        mp3Duration=:mp3Duration
        WHERE id=:id"""
    )
    fun updateContent(
        fullTextURL: String,
        type: String,
        subtype: String,
        shortText: String,
        mp3Url: String,
        mp3Duration: Int,
        id: String
    )

}

//@Entity
//data class CastEntity(
//    @PrimaryKey
//    val date: Date,
//    val fullTextURL: String,
//    val personId: Int, // FK
//    val pageNum: Int,
//    val type: String, // Интервью
//    val subtype: String, // Персонально Ваш
//    val shortText: String,
//    val mp3Url: String,
//    val mp3Duration: Int,
//    val formattedDate: String,
//    var fav: Boolean = false // reserved
//)