package echomskfan.gmail.com.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import echomskfan.gmail.com.data.db.PersonsDatabase
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.presentation.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun repository(): IRepository
    fun db(): PersonsDatabase

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun appContext(appContext: Context): Builder
    }
}