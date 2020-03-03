package echomskfan.gmail.com.domain.repository

import android.content.Context
import org.json.JSONArray
import java.nio.charset.Charset

//class ConfigRepository @Inject constructor(private val appContext: Context) : IConfigRepository {
class ConfigRepository(private val appContext: Context) :
    IConfigRepository {

    private val props: Props

    init {
        val jsonString = getString(CONFIG_JSON_NAME)
        val jsonArray = JSONArray(jsonString)

        val size = jsonArray.length()
        val map = mutableMapOf<String, Any>()

        for (i in 0 until size) {
            val jsonObject = jsonArray.getJSONObject(i)
            jsonArray.getJSONObject(i).names()?.getString(0)?.let {
                map[it] = jsonObject.get(it)
            }

//            val jsonObject = jsonArray.getJSONObject(i)
//            val name = jsonObject.names()?.getString(0)
//            val value = jsonObject.get(name)
//            val value2 = jsonObject.get(name)

        }

        props = Props(map)


    }

    override fun isDebugPanelEnabled() = props.debugPanelEnabled

    private fun getString(assetName: String): String {
        val inputStream = appContext.assets.open(assetName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charset.forName("UTF-8"))
    }

    inner class Props(map: Map<String, Any>) {
        val debugPanelEnabled: Boolean by map
        val materialDesignEnabled: Boolean by map
    }

    companion object {
        private const val CONFIG_JSON_NAME: String = "config.json"
    }
}