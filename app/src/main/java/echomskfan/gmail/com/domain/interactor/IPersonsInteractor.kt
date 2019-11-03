package echomskfan.gmail.com.domain.interactor

import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Single

interface IPersonsInteractor {
    fun copyPersonsFromXmlToDb()
    fun getPersons(): Single<List<PersonEntity>>
}