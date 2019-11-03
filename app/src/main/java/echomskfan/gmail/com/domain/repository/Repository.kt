package echomskfan.gmail.com.domain.repository

import android.content.Context
import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.PersonsDao
import echomskfan.gmail.com.data.PersonsDatabase
import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Completable
import org.json.JSONArray
import java.nio.charset.Charset

class Repository(private val appContext: Context, private val database: PersonsDatabase) : IRepository {

    private val personsDao: PersonsDao by lazy { database.getPersonsDao() }

    override fun getPersonsLiveData(): LiveData<List<PersonEntity>> {
        return personsDao.getAllLiveData()
    }

    override fun transferPersonsFromXmlToDbCompletable(): Completable {
        return Completable.create {
            val xmlList = getPersonsFromXml(appContext)
            xmlList.forEach { xmlItem ->
                val dbPerson = personsDao.getById(xmlItem.id)
                dbPerson?.run {
                    personsDao.initialUpdate(
                        url = xmlItem.url,
                        firstName = xmlItem.firstName,
                        lastName = xmlItem.lastName,
                        profession = xmlItem.profession,
                        info = xmlItem.info,
                        id = xmlItem.id
                    )
                } ?: run {
                    personsDao.add(xmlItem)
                }

                val ids = mutableListOf<Int>()
                xmlList.forEach { ids.add(it.id) }
                personsDao.deleteNotIn(ids)
            }
        }
    }

    override fun personIdNotificationClickedCompletable(id: Int): Completable {
        return Completable.create {
            personsDao.getById(id)?.let { personsDao.setNotificationById(!it.notification, id) }
        }
    }

    override fun personIdFavClickedCompletable(id: Int): Completable {
        return Completable.create {
            personsDao.getById(id)?.let { personsDao.setFavById(!it.fav, id) }
        }
    }

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