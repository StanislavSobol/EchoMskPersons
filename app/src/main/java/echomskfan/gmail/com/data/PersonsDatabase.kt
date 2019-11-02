package echomskfan.gmail.com.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import echomskfan.gmail.com.entity.PersonEntity

@Database(entities = [(PersonEntity::class)], version = 2)
abstract class PersonsDatabase : RoomDatabase() {

//   abstract fun getPersonsDao():PersonsDao

    companion object {
        private const val DB_NAME = "db"

        private var instance: PersonsDatabase? = null

        fun getInstance(appContext: Context): PersonsDatabase {
            if (instance == null) {
                synchronized(PersonsDatabase::class) {
                    instance = Room
                        .databaseBuilder(appContext, PersonsDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }

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