package echomskfan.gmail.com.domain.repository

import android.content.Context
import echomskfan.gmail.com.data.PersonsDatabase
import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Completable
import org.json.JSONArray
import java.nio.charset.Charset

class Repository(private val appContext: Context, private val database: PersonsDatabase) : IRepository {

    override fun copyPersonsFromXmlToDb(): Completable {
        return Completable.create {
            database.getPersonsDao().deleteAll()
            database.getPersonsDao().addAll(getPersonsFromXml(appContext))
        }
    }

    override fun getPersons() = database.getPersonsDao().getAll()

    private fun getPersonsFromXml(context: Context): List<PersonEntity> {
        val result = mutableListOf<PersonEntity>()

        val jsonString = loadJSONFromAsset(context, "vips.json")
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
}