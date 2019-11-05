package echomskfan.gmail.com.domain.repository

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.entity.CastEntity
import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Completable

interface IRepository {
    fun getPersonsLiveData(): LiveData<List<PersonEntity>>
    fun transferPersonsFromXmlToDbCompletable(): Completable
    fun personIdNotificationClickedCompletable(id: Int): Completable
    fun personIdFavClickedCompletable(id: Int): Completable

    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>
    fun tranferCastsFromWebToDbCompletable(personId: Int): Completable
}