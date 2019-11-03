package echomskfan.gmail.com.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import echomskfan.gmail.com.entity.PersonEntity

@Dao
interface PersonsDao {

    @Query("SELECT * FROM PersonEntity")
    fun getAll(): List<PersonEntity>

    @Query("SELECT * FROM PersonEntity WHERE id = :id")
    fun getById(id: Int): PersonEntity?

    @Query("UPDATE PersonEntity SET url = :url, firstName= :firstName, lastName= :lastName , profession = :profession, info =:info WHERE id=:id")
    fun initialUpdate(url: String, firstName: String, lastName: String, profession: String, info: String, id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(item: PersonEntity)

    @Query("DELETE FROM PersonEntity WHERE id NOT IN (:list) ")
    fun deleteNotIn(list: List<Int>)

    @Query("UPDATE PersonEntity SET notification = :notification WHERE id=:id")
    fun setNotificationById(notification: Boolean, id: Int)

    @Query("SELECT * FROM PersonEntity")
    fun getAllLd(): LiveData<List<PersonEntity>>

}