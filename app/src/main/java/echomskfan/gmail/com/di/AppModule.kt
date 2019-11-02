package echomskfan.gmail.com.di

import android.content.Context
import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.domain.repository.Repository
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppContext(appContext: Context) = appContext

    @Singleton
    @Provides
    fun provideRepository(): IRepository {
        return Repository()
    }
}