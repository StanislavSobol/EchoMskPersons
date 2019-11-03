package echomskfan.gmail.com.domain.interactor

import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Single

interface IPersonsInteractor {
    fun getPersons(copyFromXml: Boolean): Single<List<PersonEntity>>
}