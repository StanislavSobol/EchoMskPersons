package echomskfan.gmail.com.domain.repository

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.db.entity.CastEntity
import echomskfan.gmail.com.data.db.entity.PersonEntity
import echomskfan.gmail.com.presentation.player.PlayerItem
import io.reactivex.Completable
import io.reactivex.Single

interface IRepository {
    fun getPersonsLiveData(): LiveData<List<PersonEntity>>
    fun transferPersonsFromXmlToDbCompletable(): Completable
    fun personIdNotificationClickedCompletable(personId: Int): Completable
    fun personIdFavClickedCompletable(personId: Int): Completable

    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>
    fun transferCastsFromWebToDbCompletable(personId: Int, pageNum: Int): Completable
    fun castIdFavClickedCompletable(castId: String): Completable

    fun getPlayerItemSingle(castId: String): Single<PlayerItem>
}