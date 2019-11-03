package echomskfan.gmail.com.domain.repository

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IRepository {
    fun getPersons(copyFromXml: Boolean): Single<List<PersonEntity>>
    fun personIdNotificationClicked(id: Int): Completable
    fun getPersonsLiveData(): LiveData<List<PersonEntity>>
    fun transferPersonsFromXmlToDb()
}