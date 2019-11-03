package echomskfan.gmail.com.domain.repository

import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IRepository {
    fun copyPersonsFromXmlToDb(): Completable
    fun getPersons(): Single<List<PersonEntity>>
}