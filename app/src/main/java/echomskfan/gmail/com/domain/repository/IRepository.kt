package echomskfan.gmail.com.domain.repository

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.annotations.apptodo.AppTodoMinor
import echomskfan.gmail.com.data.db.entity.CastEntity
import echomskfan.gmail.com.data.db.entity.PersonEntity
import echomskfan.gmail.com.presentation.player.PlayerItem

interface IRepository {
    fun getPersonsLiveData(): LiveData<List<PersonEntity>>
    fun transferPersonsFromXmlToDb()
    fun personIdNotificationClicked(personId: Int)
    fun personIdFavClicked(personId: Int)

    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>
    fun transferCastsFromWebToDb(personId: Int, pageNum: Int)
    fun castIdFavClicked(castId: String)

    fun getPlayerItem(castId: String): PlayerItem

    fun getPersonLiveData(personId: Int): LiveData<PersonEntity>

    @AppTodoMinor("IRepository: Move it in a brand new repo ISharedPrefsRepository")
    var isFavOn: Boolean
}