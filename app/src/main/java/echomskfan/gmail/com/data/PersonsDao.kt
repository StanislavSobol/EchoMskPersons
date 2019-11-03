package echomskfan.gmail.com.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Single

@Dao
interface PersonsDao {

    @Query("SELECT * FROM PersonEntity")
    fun getAll(): Single<List<PersonEntity>>

    @Query("DELETE FROM PersonEntity")
    fun deleteAll()

    @Insert
    @JvmSuppressWildcards
    fun addAll(personsFromXml: List<PersonEntity>)
}