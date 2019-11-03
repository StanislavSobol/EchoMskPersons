package echomskfan.gmail.com.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import echomskfan.gmail.com.entity.PersonEntity

@Dao
interface PersonsDao {

    @Query("SELECT * FROM PersonEntity")
    fun getAll(): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(personsFromXml: List<PersonEntity>)

    @Query("DELETE FROM PersonEntity WHERE id NOT IN (:list) ")
    fun deleteNotIn(list: List<Int>)
}