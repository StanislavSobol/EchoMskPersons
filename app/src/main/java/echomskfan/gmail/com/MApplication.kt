package echomskfan.gmail.com

import android.app.Application
import echomskfan.gmail.com.di.AppComponent
import echomskfan.gmail.com.di.DaggerAppComponent

class MApplication : Application() {

    private lateinit var daggerAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        daggerAppComponent = DaggerAppComponent
            .builder()
            .appContext(this.applicationContext)
            .build()
        daggerAppComponent.inject(this)
    }

    companion object {
        lateinit var instance: MApplication

        fun getDaggerComponents(): AppComponent {
            return instance.daggerAppComponent
        }
    }
}