package echomskfan.gmail.com.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import echomskfan.gmail.com.domain.repository.IRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

//    fun inject(application: MApplication)

    fun repository(): IRepository

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun appContext(appContext: Context): Builder
    }
}