package echomskfan.gmail.com.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.data.db.PersonsDatabase
import echomskfan.gmail.com.data.parser.EchoParser
import echomskfan.gmail.com.data.parser.IEchoParser
import echomskfan.gmail.com.data.prefs.ISharedPrefs
import echomskfan.gmail.com.data.prefs.SharedPrefs
import echomskfan.gmail.com.domain.assetextractor.AssetExtractor
import echomskfan.gmail.com.domain.assetextractor.IAssetExtractor
import echomskfan.gmail.com.domain.interactor.main.IMainInteractor
import echomskfan.gmail.com.domain.interactor.main.MainInteractor
import echomskfan.gmail.com.domain.repository.ConfigRepository
import echomskfan.gmail.com.domain.repository.IConfigRepository
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.domain.repository.Repository
import echomskfan.gmail.com.presentation.main.MainViewModelFactory
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
    fun provideAssetExtractor(appContext: Context): IAssetExtractor {
        return AssetExtractor(appContext)
    }

    @Singleton
    @Provides
    fun provideEchoParser(assetExtractor: IAssetExtractor): IEchoParser {
        return EchoParser(assetExtractor)
    }

    @Singleton
    @Provides
    fun provideSharedPrefs(appContext: Context): ISharedPrefs {
        return SharedPrefs(appContext)
    }

    @Singleton
    @Provides
    fun provideRepository(
        assetExtractor: IAssetExtractor,
        database: PersonsDatabase,
        echoParser: IEchoParser,
        sharedPrefs: ISharedPrefs
    ): IRepository {
        return Repository(assetExtractor, database, echoParser, sharedPrefs)
    }

    @Singleton
    @Provides
    fun provideConfigManager(appContext: Context): IConfigRepository {
        return ConfigRepository(appContext)
    }

    @Singleton
    @Provides
    fun provideMainInteractor(
        repository: IRepository,
        configRepository: IConfigRepository
    ): IMainInteractor {
        return MainInteractor(repository, configRepository)
    }


    @Singleton
    @Provides
    fun provideMainViewModelFactory(interactor: IMainInteractor): MainViewModelFactory {
        return MainViewModelFactory(interactor)
    }

    companion object {
        private const val DB_NAME = "db"
    }
}