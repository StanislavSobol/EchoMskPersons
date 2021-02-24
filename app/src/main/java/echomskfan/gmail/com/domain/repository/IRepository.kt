package echomskfan.gmail.com.domain.repository

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.db.entity.CastEntity
import echomskfan.gmail.com.data.db.entity.PersonEntity
import echomskfan.gmail.com.presentation.player.PlayerItem
import java.util.*

interface IRepository {
    fun getPersonsLiveData(): LiveData<List<PersonEntity>>

    // TODO move this logic to the proper interactor
    fun transferPersonsFromXmlToDb()

    // TODO rename according the repo functionality
    fun personIdNotificationClicked(personId: Int)

    // TODO rename according the repo functionality
    fun personIdFavClicked(personId: Int)

    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>

    // TODO move this logic to the proper interactor
    fun transferCastsFromWebToDb(personId: Int, pageNum: Int)

    // TODO rename according the repo functionality
    fun castIdFavClicked(castId: String)

    fun getPlayerItem(castId: String): PlayerItem

    fun getPersonLiveData(personId: Int): LiveData<PersonEntity>

    fun getPersonsWithNotification(): List<PersonEntity>

    fun getMaxCastDateForPerson(personId: Int): Date?

    fun getCastsFromWebForPerson(personEntity: PersonEntity): List<CastEntity>

    fun insertOrUpdateCasts(newCasts: List<CastEntity>)

    fun updatePlayedTime(castId: String, progressSec: Int)

    fun getTextUrlByCastId(castId: String): String?

    // todo maybe to a brand new KAPT shared prefs
    var isFavOn: Boolean
}