package echomskfan.gmail.com.domain.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.gmail.echomskfan.persons.interactor.parser.IEchoParser
import echomskfan.gmail.com.data.PersonsDao
import echomskfan.gmail.com.data.PersonsDatabase
import echomskfan.gmail.com.entity.PersonEntity
import echomskfan.gmail.com.utils.getPersonsFromXml
import io.reactivex.Completable

class Repository(
    private val appContext: Context,
    private val database: PersonsDatabase,
    private val echoParser: IEchoParser
) : IRepository {

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
}