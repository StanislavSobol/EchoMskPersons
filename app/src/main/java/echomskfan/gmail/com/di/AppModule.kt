package echomskfan.gmail.com.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.data.PersonsDatabase
import echomskfan.gmail.com.data.parser.EchoParser
import echomskfan.gmail.com.data.parser.IEchoParser
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.domain.repository.Repository
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providePersonsDatabase(appContext: Context): PersonsDatabase {
        return Room
            .databaseBuilder(appContext, PersonsDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideEchoParser(): IEchoParser {
        return EchoParser()
    }

    @Singleton
    @Provides
    fun provideRepository(appContext: Context, database: PersonsDatabase, echoParser: IEchoParser): IRepository {
        return Repository(appContext, database, echoParser)
    }

    companion object {
        private const val DB_NAME = "db"
    }
}