package echomskfan.gmail.com

import android.content.Context
import org.json.JSONArray
import java.nio.charset.Charset
import kotlin.reflect.KClass

@Deprecated("Only  as an example")
object FeatureConfiguratorExample {

    private val jsonMap = mutableMapOf<String, Any>()
    private val classesFields = mutableMapOf<KClass<*>, MutableSet<AnyData>>()

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
        classesFields[objectToBind::class]?.forEach {
            try {
                objectToBind::class.java.getDeclaredField(it.fieldName).run {
                    isAccessible = true
                    set(objectToBind, it.fieldValue)
                }
            } catch (e: NoSuchFieldException) {
                //
            }
        }
    }

    private fun add(clazz: KClass<*>, fieldName: String, paramName: String) {
        jsonMap[paramName]?.let {
            classesFields[clazz]
                ?.add(AnyData(fieldName, it))
                ?: run { classesFields[clazz] = mutableSetOf(AnyData(fieldName, it)) }
        }
    }

    private fun fromProcessor() {
//        add(MApplication::class, "isDisclaimerEnabled", "disclaimerEnabled")
        add(MApplication::class, "isShowSplashAnimation", "showSplashAnimation")
        add(MApplication::class, "splashDelayMSec", "splashDelayMSec")
    }

    private fun getConfigFileAsString(appContext: Context, configJsonName: String): String {
        val inputStream = appContext.assets.open(configJsonName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charset.forName("UTF-8"))
    }

    private data class AnyData(val fieldName: String, val fieldValue: Any)
}
