package echomskfan.gmail.com.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import echomskfan.gmail.com.BuildConfig

class SharedPrefs(private val appContext: Context) : ISharedPrefs {

    private val sharedPrefs: SharedPreferences
        get() = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val data: MutableMap<String, Any> = HashMap()

    override var isFavOn: Boolean
        get() {
            if (sharedPrefs.contains(FAV_ON_ITEM)) {
                data[FAV_ON_ITEM] = sharedPrefs.getBoolean(FAV_ON_ITEM, false)
            } else {
                data[FAV_ON_ITEM] = false
            }
            return data[FAV_ON_ITEM] as Boolean
        }
        set(value) {
            data[FAV_ON_ITEM] = value
            sharedPrefs.edit()
                .apply { putBoolean(FAV_ON_ITEM, value).apply() }
        }

    companion object {
        @VisibleForTesting
        const val PREFS_NAME = BuildConfig.APPLICATION_ID + ".prefs"
        private const val FAV_ON_ITEM = "favOn"
    }
}