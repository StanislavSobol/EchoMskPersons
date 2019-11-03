package echomskfan.gmail.com.utils

import android.content.Context
import echomskfan.gmail.com.entity.PersonEntity
import org.json.JSONArray
import java.nio.charset.Charset

private const val PERSONS_JSON_NAME: String = "vips.json"

fun getPersonsFromXml(context: Context): List<PersonEntity> {
    val result = mutableListOf<PersonEntity>()

    val jsonString = loadJSONFromAsset(context, PERSONS_JSON_NAME)
    val jsonArray = JSONArray(jsonString)

    val size = jsonArray.length()

    for (i in 0 until size) {
        val jsonObject = jsonArray.getJSONObject(i)
        result.add(
            PersonEntity(
                jsonObject.get("id") as Int,
                jsonObject.get("url") as String,
                jsonObject.get("firstName") as String,
                jsonObject.get("lastName") as String,
                jsonObject.get("profession") as String,
                jsonObject.get("info") as String,
                jsonObject.get("photoUrl") as String
            )
        )
    }

    return result
}

private fun loadJSONFromAsset(context: Context, assetName: String): String {
    val json: String
    val `is` = context.assets.open(assetName)
    val size = `is`.available()
    val buffer = ByteArray(size)
    `is`.read(buffer)
    `is`.close()
    json = String(buffer, Charset.forName("UTF-8"))
    return json
}