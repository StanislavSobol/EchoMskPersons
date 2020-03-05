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

    @Query("SELECT * FROM CastEntity WHERE personId = :personId ORDER BY date DESC")
    fun getAllLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>

    @Query("SELECT * FROM CastEntity WHERE date = :date AND personId=:personId")
    fun getCastByDateAndPersonId(date: Date, personId: Int): CastEntity?

    @Query("SELECT * FROM CastEntity WHERE id = :id")
    fun getById(id: String): CastEntity?

    @Query("DELETE FROM CastEntity WHERE (type = '' AND subtype = '')")
    fun removeGarbage()

    @Query("SELECT * FROM CastEntity WHERE personId = :personId ORDER BY date DESC")
    fun getByPersonId(personId: Int): List<CastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newCast: CastEntity)

    @Query(
        """UPDATE CastEntity SET
        fullTextURL = :fullTextURL,
        type=:type,
        subtype=:subtype,
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

    @Query("UPDATE CastEntity SET fav=:fav WHERE id=:id")
    fun setFavById(fav: Boolean, id: String)
}
