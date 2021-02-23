package echomskfan.gmail.com

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.multidex.MultiDexApplication
import echomskfan.gmail.com.annotations.apptodo.AppTodoMainPackage
import echomskfan.gmail.com.di.AppComponent
import echomskfan.gmail.com.di.DaggerAppComponent

@AppTodoMainPackage
class MApplication : MultiDexApplication() {

    //    @FeatureToggleBoolean("showSplashAnimation")
    var isShowSplashAnimation = true

    //    @FeatureToggleLong("splashDelayMSec")
    var splashDelayMSec = 20

    private lateinit var daggerAppComponent: AppComponent

//    @FeatureToggleBoolean("disclaimerEnabled")
//    var testDisclaimerEnabled: Boolean = true

    override fun onCreate() {
        super.onCreate()
        instance = this

        daggerAppComponent = DaggerAppComponent
            .builder()
            .appContext(this.applicationContext)
            .build()

        FeatureConfigurator.install(appContext = instance.applicationContext, configJsonName = CONFIG_JSON_NAME)

//        FeatureConfiguratorExample.install(appContext = instance.applicationContext, configJsonName = CONFIG_JSON_NAME)
//        FeatureConfiguratorExample.bind(this)

        // FeatureConfiguratorExample.addBoolean(MApplication::class, "disclaimerEnabled", "testDisclaimerEnabled")
        // FeatureConfiguratorExample.bind(this)

//         Log.d("SSS", "isShowSplashAnimation = $isShowSplashAnimation")
//         Log.d("SSS", "splashDelayMSec = $splashDelayMSec")
    }

    fun isOnlineWithToast(showToastIfNot: Boolean): Boolean {
        val cm = instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo

        val result = netInfo != null && netInfo.isConnectedOrConnecting

        if (showToastIfNot && !result) {
            val s = instance.resources.getString(R.string.error_no_internet)
            Toast.makeText(instance, s, Toast.LENGTH_LONG).show()
        }

        return result
    }

    companion object {
        lateinit var instance: MApplication
        private const val CONFIG_JSON_NAME: String = "config.json"

        fun getAppComponent(): AppComponent {
            return instance.daggerAppComponent
        }
    }
}