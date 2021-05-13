package echomskfan.gmail.com.di.core

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import echomskfan.gmail.com.data.db.PersonsDatabase
import echomskfan.gmail.com.domain.interactor.checknew.CheckNewWorker
import echomskfan.gmail.com.domain.interactor.checknew.ICheckNewInteractor
import echomskfan.gmail.com.domain.interactor.player.IPlayerCoInteractor
import echomskfan.gmail.com.domain.interactor.player.IPlayerInteractor
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.presentation.SplashActivity
import echomskfan.gmail.com.presentation.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun repository(): IRepository
    fun db(): PersonsDatabase
    fun checkNewInteractor(): ICheckNewInteractor
    fun playInteractor(): IPlayerInteractor
    fun playCoInteractor(): IPlayerCoInteractor

    fun inject(checkNewWorker: CheckNewWorker)
    fun inject(splashActivity: SplashActivity)
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(appContext: Context): Builder

        fun build(): AppComponent
    }
}