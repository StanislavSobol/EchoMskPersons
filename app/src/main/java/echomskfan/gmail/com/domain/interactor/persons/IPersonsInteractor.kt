package echomskfan.gmail.com.domain.interactor.persons

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.db.entity.PersonEntity
import io.reactivex.Completable

interface IPersonsInteractor {
    fun getPersonsLiveData(): LiveData<List<PersonEntity>>
    fun transferPersonsFromXmlToDb(): Completable
    fun personIdNotificationClicked(personId: Int): Completable
    fun personIdFavClicked(personId: Int): Completable
}