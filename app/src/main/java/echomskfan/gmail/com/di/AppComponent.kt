package echomskfan.gmail.com.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import echomskfan.gmail.com.MApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(body: MApplication)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun appContext(appContext: Context): Builder
    }
}