package echomskfan.gmail.com.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import echomskfan.gmail.com.entity.CastEntity

@Dao
interface CastsDao {

    @Query("SELECT * FROM CastEntity WHERE personId = :personId ORDER BY date DESC")
    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>

//    @Query("SELECT * FROM CastEntity WHERE fullTextURL = :id")
//    fun getById(id: Int): CastEntity?

    @Query("DELETE FROM CastEntity WHERE personId = :personId")
    fun deleteAllForPerson(personId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(casts: List<CastEntity>)

    @Query("DELETE FROM CastEntity WHERE (type = '' AND subtype = '') OR shortText = ''")
    fun removeGarbage()
}