package echomskfan.gmail.com

import android.content.Context
import org.json.JSONArray
import java.nio.charset.Charset
import kotlin.reflect.KClass

object FeatureConfiguratorExample {

    private val jsonMap = mutableMapOf<String, Any>()
    private val booleans = mutableMapOf<KClass<*>, BooleanData>()

    fun install(appContext: Context, configJsonName: String) {
        val jsonString = getConfigFileAsString(appContext, configJsonName)
        val jsonArray = JSONArray(jsonString)

        val size = jsonArray.length()

        for (i in 0 until size) {
            val jsonObject = jsonArray.getJSONObject(i)
            jsonArray.getJSONObject(i).names()?.getString(0)?.let {
                jsonMap[it] = jsonObject.get(it)
            }
        }

        fromProcessor()
    }

    fun bind(objectToBind: Any) {
        booleans[objectToBind::class]?.let {
            objectToBind::class.java.getDeclaredField(it.fieldName).run {
                isAccessible = true
                set(objectToBind, it.fieldValue)
            }
        }
    }

    private fun addBoolean(clazz: KClass<*>, paramName: String, fieldName: String) {
        jsonMap[paramName]?.let { booleans[clazz] = BooleanData(fieldName, it as Boolean) }
    }

    private fun fromProcessor() {
        // addBoolean(MApplication::class, "disclaimerEnabled", "testDisclaimerEnabled")
    }

    private fun getConfigFileAsString(appContext: Context, configJsonName: String): String {
        val inputStream = appContext.assets.open(configJsonName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charset.forName("UTF-8"))
    }


    private data class BooleanData(val fieldName: String, val fieldValue: Boolean)
}