package echomskfan.gmail.com.domain.interactor

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IPersonsInteractor {
    fun getPersons(copyFromXml: Boolean = false): Single<List<PersonEntity>>
    fun personIdNotificationClicked(id: Int): Completable
    fun getPersonsLiveData(): LiveData<List<PersonEntity>>
    fun transferPersonsFromXmlToDb()
}