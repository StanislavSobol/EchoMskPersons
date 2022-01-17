package echomskfan.gmail.com.domain.assetextractor

import android.content.Context
import androidx.annotation.VisibleForTesting
import echomskfan.gmail.com.data.db.entity.PersonEntity
import org.json.JSONArray
import java.nio.charset.Charset

class AssetExtractor(private val appContext: Context) : IAssetExtractor {

    override fun getPersons(): List<PersonEntity> {
        val result = mutableListOf<PersonEntity>()

        val jsonString = getString(PERSONS_JSON_NAME)
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

    override fun getCastsForPersonAndPage(personId: Int, pageNum: Int): String {
        return getString("$PERSON_CASTS_PREFIX$personId$PERSON_CASTS_SEPARATOR$pageNum$HTML_EXT")
    }

    @VisibleForTesting
    internal fun getString(assetName: String): String {
        // TODO .use
        val inputStream = appContext.assets.open(assetName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        //    inputStream.close()
        return String(buffer, Charset.forName("UTF-8"))
    }

    private companion object {
        const val PERSONS_JSON_NAME: String = "persons.json"
        const val PERSON_CASTS_PREFIX: String = "person_page_"
        const val PERSON_CASTS_SEPARATOR: String = "_"
        const val HTML_EXT: String = ".html"
    }
}