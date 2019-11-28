package echomskfan.gmail.com.data.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.*
import echomskfan.gmail.com.data.db.dao.CastsDao
import echomskfan.gmail.com.data.db.dao.PersonsDao
import echomskfan.gmail.com.data.db.entity.CastEntity
import echomskfan.gmail.com.data.db.entity.PersonEntity
import java.util.*

@Database(entities = [(PersonEntity::class), (CastEntity::class)], version = 23)
@TypeConverters(Converters::class)
abstract class PersonsDatabase : RoomDatabase() {

    abstract fun getPersonsDao(): PersonsDao

    abstract fun getCastsDao(): CastsDao

    companion object {

        private var instance: PersonsDatabase? = null

        @VisibleForTesting
        fun getTestInstance(appContext: Context): PersonsDatabase {
            if (instance == null) {
                synchronized(PersonsDatabase::class) {
                    instance = Room
                        .inMemoryDatabaseBuilder(appContext, PersonsDatabase::class.java)
                        .build()
                }
            }
            return instance!!
        }
    }
}

internal class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}