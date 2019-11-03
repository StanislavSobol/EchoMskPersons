package echomskfan.gmail.com.domain.interactor

import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IPersonsInteractor {
    fun getPersons(copyFromXml: Boolean = false): Single<List<PersonEntity>>
    fun personIdNotificationClicked(id: Int): Completable
}