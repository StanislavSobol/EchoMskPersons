package echomskfan.gmail.com.domain.repository

import android.content.Context
import org.json.JSONArray
import java.nio.charset.Charset

class ConfigRepository(private val appContext: Context) : IConfigRepository {

    private val propertiesDelegate: PropertiesDelegate

    init {
        val jsonString = getConfigFileAsString()
        val jsonArray = JSONArray(jsonString)

        val size = jsonArray.length()
        val map = mutableMapOf<String, Any>()

        for (i in 0 until size) {
            val jsonObject = jsonArray.getJSONObject(i)
            jsonArray.getJSONObject(i).names()?.getString(0)?.let {
                map[it] = jsonObject.get(it)
            }
        }

        propertiesDelegate = PropertiesDelegate(map)
    }


    private fun getConfigFileAsString(): String {
        val inputStream = appContext.assets.open(CONFIG_JSON_NAME)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charset.forName("UTF-8"))
    }

    inner class PropertiesDelegate(map: Map<String, Any>) {
    }

    companion object {
        private const val CONFIG_JSON_NAME: String = "config.json"
    }
}