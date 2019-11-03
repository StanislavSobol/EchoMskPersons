package echomskfan.gmail.com.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import echomskfan.gmail.com.entity.PersonEntity

@Database(entities = [(PersonEntity::class)], version = 6)
abstract class PersonsDatabase : RoomDatabase() {

    abstract fun getPersonsDao(): PersonsDao

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