package echomskfan.gmail.com.domain.repository

import android.content.Context
import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.CastsDao
import echomskfan.gmail.com.data.PersonsDao
import echomskfan.gmail.com.data.PersonsDatabase
import echomskfan.gmail.com.data.parser.IEchoParser
import echomskfan.gmail.com.entity.CastEntity
import echomskfan.gmail.com.entity.PersonEntity
import echomskfan.gmail.com.utils.getPersonsFromXml
import io.reactivex.Completable

class Repository(
    private val appContext: Context,
    private val database: PersonsDatabase,
    private val echoParser: IEchoParser
) : IRepository {

    private val personsDao: PersonsDao by lazy { database.getPersonsDao() }
    private val castsDao: CastsDao by lazy { database.getCastsDao() }

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
                        id = xmlItem.id,
                        url = xmlItem.url,
                        firstName = xmlItem.firstName,
                        lastName = xmlItem.lastName,
                        profession = xmlItem.profession,
                        info = xmlItem.info
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

    override fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>> {
        return castsDao.getCastsLiveDataForPerson(personId)
    }

    override fun tranferCastsFromWebToDbCompletable(personId: Int): Completable {
        return Completable.create {
            personsDao.getById(personId)?.let {
                // TODO deal with favs
                castsDao.deleteAllForPerson(personId)
                val casts = echoParser.getCasts(it, 1)
                castsDao.insertAll(casts)
                castsDao.removeGarbage()
            }
        }
    }
}