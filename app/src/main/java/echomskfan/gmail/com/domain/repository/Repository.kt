package echomskfan.gmail.com.domain.repository

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.db.PersonsDatabase
import echomskfan.gmail.com.data.db.entity.CastEntity
import echomskfan.gmail.com.data.db.entity.PersonEntity
import echomskfan.gmail.com.data.parser.IEchoParser
import echomskfan.gmail.com.data.prefs.ISharedPrefs
import echomskfan.gmail.com.domain.assetextractor.IAssetExtractor
import echomskfan.gmail.com.presentation.player.PlayerItem
import java.util.*

class Repository(
    private val assetExtractor: IAssetExtractor,
    private val database: PersonsDatabase,
    private val echoParser: IEchoParser,
    private val sharedPrefs: ISharedPrefs
) : IRepository {

    // TODO provide all the DAOs vie Dagger ()
    private val personsDao by lazy { database.getPersonsDao() }
    private val castsDao by lazy { database.getCastsDao() }

    override fun getPersonsLiveData(): LiveData<List<PersonEntity>> {
        return personsDao.getAllLiveData()
    }

    override fun transferPersonsFromXmlToDb() {
        val xmlList = assetExtractor.getPersons()
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

    override fun personIdNotificationClicked(personId: Int) {
        personsDao.getById(personId)
            ?.let { personsDao.setNotificationById(!it.notification, personId) }
    }

    override fun personIdFavClicked(personId: Int) {
        personsDao.getById(personId)?.let { personsDao.setFavById(!it.fav, personId) }
    }

    override fun castIdFavClicked(castId: String) {
        castsDao.getById(castId)?.let { castsDao.setFavById(!it.fav, castId) }
    }

    override fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>> {
        return castsDao.getAllLiveDataForPerson(personId)
    }

    override fun transferCastsFromWebToDb(personId: Int, pageNum: Int) {
        // TODO use insertOrUpdateCasts method
        personsDao.getById(personId)?.let {
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
                        mp3Duration = newCast.mp3DurationSec,
                        id = newCast.id
                    )
                } ?: castsDao.insert(newCast)

            }
            castsDao.removeGarbage()
        }
    }

    override fun getPlayerItem(castId: String): PlayerItem {
        val castEntity = castsDao.getById(castId)
        var personEntity: PersonEntity? = null
        castEntity?.let { cast -> personEntity = personsDao.getById(cast.personId) }

        if (castEntity != null && personEntity != null) {
            return PlayerItem(
                castId = castEntity.id,
                personName = personEntity!!.getFullName(),
                personPhotoUrl = personEntity!!.photoUrl,
                typeSubtype = castEntity.getTypeSubtype(),
                formattedDate = castEntity.formattedDate,
                mp3Url = castEntity.mp3Url,
                mp3Duration = castEntity.mp3DurationSec
            )
        } else {
            throw IllegalStateException("Not enough info about the cast or the person")
        }
    }

    override fun getPersonLiveData(personId: Int): LiveData<PersonEntity> {
        return personsDao.getPersonLiveData(personId)
    }

    override fun getPersonsWithNotification(): List<PersonEntity> {
        return personsDao.getPersonsWithNotification()
    }

    override fun getMaxCastDateForPerson(personId: Int): Date {
        return castsDao.getMaxCastDateForPerson(personId)

    }

    override fun getCastsFromWebForPerson(personEntity: PersonEntity): List<CastEntity> {
        return echoParser.getCasts(personEntity)
    }

    override fun insertOrUpdateCasts(newCasts: List<CastEntity>) {
        newCasts.forEach { newCast ->
            castsDao.getCastByDateAndPersonId(newCast.date, newCast.personId)?.let {
                castsDao.updateContent(
                    fullTextURL = newCast.fullTextURL,
                    type = newCast.type,
                    subtype = newCast.subtype,
                    shortText = newCast.shortText,
                    mp3Url = newCast.mp3Url,
                    mp3Duration = newCast.mp3DurationSec,
                    id = newCast.id
                )
            } ?: castsDao.insert(newCast)

        }
        castsDao.removeGarbage()
    }

    override fun updatePlayedTime(castId: String, progressSec: Int) {
        castsDao.updatePlayedTime(castId, progressSec)
    }

    override var isFavOn: Boolean
        get() = sharedPrefs.isFavOn
        set(value) {
            sharedPrefs.isFavOn = value
        }
}