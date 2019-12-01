package echomskfan.gmail.com.domain.repository

import android.content.Context
import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.db.PersonsDatabase
import echomskfan.gmail.com.data.db.dao.CastsDao
import echomskfan.gmail.com.data.db.dao.PersonsDao
import echomskfan.gmail.com.data.db.entity.CastEntity
import echomskfan.gmail.com.data.db.entity.PersonEntity
import echomskfan.gmail.com.data.parser.IEchoParser
import echomskfan.gmail.com.presentation.player.PlayerItem
import echomskfan.gmail.com.utils.getPersonsFromXml
import io.reactivex.Completable
import io.reactivex.Single

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

    override fun personIdNotificationClickedCompletable(personId: Int): Completable {
        return Completable.create {
            personsDao.getById(personId)?.let { personsDao.setNotificationById(!it.notification, personId) }
        }
    }

    override fun personIdFavClickedCompletable(personId: Int): Completable {
        return Completable.create {
            personsDao.getById(personId)?.let { personsDao.setFavById(!it.fav, personId) }
        }
    }

    override fun castIdFavClickedCompletable(castId: String): Completable {
        return Completable.create {
            castsDao.getById(castId)?.let { castsDao.setFavById(!it.fav, castId) }
        }
    }

    override fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>> {
        return castsDao.getAllLiveDataForPerson(personId)
    }

    override fun transferCastsFromWebToDbCompletable(personId: Int, pageNum: Int): Completable {
        return Completable.create {
            personsDao.getById(personId)?.let {
                //    castsDao.deleteLastForPerson(personId)

                val newCasts = echoParser.getCasts(it, pageNum)
                newCasts.forEach { newCast ->
                    val oldCast = castsDao.getCastByDateAndPersonId(newCast.date, personId)
                    oldCast?.let {
                        castsDao.updateContent(
                            fullTextURL = newCast.fullTextURL,
                            type = newCast.type,
                            subtype = newCast.subtype,
                            shortText = newCast.shortText,
                            mp3Url = newCast.mp3Url,
                            mp3Duration = newCast.mp3Duration,
                            id = newCast.id
                        )
                    } ?: castsDao.insert(newCast)

                }
                castsDao.removeGarbage()
//                castsDao.getByPersonId(personId).forEach { item -> Log.d("SSS", "id = ${item.id}") }
            }
            it.onComplete()
        }
    }

    override fun getPlayerItemSingle(castId: String): Single<PlayerItem> {
        return Single.create {
            val castEntity = castsDao.getById(castId)
            var personEntity: PersonEntity? = null
            castEntity?.let { cast -> personEntity = personsDao.getById(cast.personId) }

            if (castEntity != null && personEntity != null) {
                it.onSuccess(
                    PlayerItem(
                        personName = personEntity!!.getFullName(),
                        personPhotoUrl = personEntity!!.photoUrl,
                        typeSubtype = castEntity.getTypeSubtype(),
                        formattedDate = castEntity.formattedDate,
                        mp3Url = castEntity.mp3Url,
                        mp3Duration = castEntity.mp3Duration
                    )
                )
            } else {
                it.onError(IllegalStateException("Not enough info about the cast or the person"))
            }
        }
    }

}