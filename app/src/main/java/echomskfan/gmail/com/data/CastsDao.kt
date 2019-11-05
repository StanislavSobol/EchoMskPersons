package echomskfan.gmail.com.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import echomskfan.gmail.com.entity.CastEntity

@Dao
interface CastsDao {

    @Query("SELECT * FROM CastEntity WHERE personId = :personId")
    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>

//    @Query("SELECT * FROM CastEntity WHERE fullTextURL = :id")
//    fun getById(id: Int): CastEntity?

    @Query("DELETE FROM CastEntity WHERE personId = :personId")
    fun deleteAllForPerson(personId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(casts: List<CastEntity>)

//
//    @Query("UPDATE PersonEntity SET url = :url, firstName= :firstName, lastName= :lastName , profession = :profession, info =:info WHERE id=:id")
//    fun initialUpdate(url: String, firstName: String, lastName: String, profession: String, info: String, id: Int)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun add(item: PersonEntity)
//
//    @Query("DELETE FROM PersonEntity WHERE id NOT IN (:list) ")
//    fun deleteNotIn(list: List<Int>)
//
//    @Query("UPDATE PersonEntity SET notification = :notification WHERE id=:id")
//    fun setNotificationById(notification: Boolean, id: Int)
//
//    @Query("UPDATE PersonEntity SET fav = :fav WHERE id=:id")
//    fun setFavById(fav: Boolean, id: Int)
}