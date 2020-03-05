package echomskfan.gmail.com.data.db.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DebugDao {

    @Query("DELETE FROM CastEntity WHERE date = (select max(date) from CastEntity where personId=:personId)")
    fun deleteLastCastForPerson(personId: Int)
}