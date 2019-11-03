package echomskfan.gmail.com.domain.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.data.PersonsDao
import echomskfan.gmail.com.data.PersonsDatabase
import echomskfan.gmail.com.entity.PersonEntity
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.json.JSONArray
import java.nio.charset.Charset

class Repository(private val appContext: Context, private val database: PersonsDatabase) : IRepository {

    private val personsDao: PersonsDao by lazy { database.getPersonsDao() }

    private val _personsLiveData = MutableLiveData<List<PersonEntity>>()

    private val transferPersonsFromXmlToDbCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val personIdNotificationClickedCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val personIdFavClickedCompositeDDisposable: CompositeDisposable = CompositeDisposable()

    override fun getPersonsLiveData(): LiveData<List<PersonEntity>> {
        return _personsLiveData
    }

    override fun transferPersonsFromXmlToDb() {
        transferPersonsFromXmlToDbCompositeDisposable.clear()

        Completable.create {
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

                _personsLiveData.postValue(personsDao.getAll())
            }
        }
            .fromIoToMain()
            .doOnError { e -> catchThrowable(e) }
            .subscribe()
            .toCompositeDisposable(transferPersonsFromXmlToDbCompositeDisposable)
    }

    override fun personIdNotificationClicked(id: Int) {
        personIdNotificationClickedCompositeDisposable.clear()

        Completable.create {
            personsDao.getById(id)?.let { personsDao.setNotificationById(!it.notification, id) }
            _personsLiveData.postValue(personsDao.getAll())
        }.doOnError { e -> catchThrowable(e) }
            .fromIoToMain()
            .subscribe()
            .toCompositeDisposable(personIdNotificationClickedCompositeDisposable)
    }

    override fun personIdFavClicked(id: Int) {
        personIdFavClickedCompositeDDisposable.clear()

        Completable.create {
            personsDao.getById(id)?.let { personsDao.setFavById(!it.fav, id) }
            _personsLiveData.postValue(personsDao.getAll())
        }.doOnError { e -> catchThrowable(e) }
            .fromIoToMain()
            .subscribe()
            .toCompositeDisposable(personIdFavClickedCompositeDDisposable)
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

    private fun Disposable.toCompositeDisposable(compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(this)
    }
}