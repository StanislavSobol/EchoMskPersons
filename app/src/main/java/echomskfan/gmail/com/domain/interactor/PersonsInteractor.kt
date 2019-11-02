package echomskfan.gmail.com.domain.interactor

import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.entity.PersonEntity
import io.reactivex.Single

class PersonsInteractor(private val repository: IRepository) : IPersonsInteractor {

    override fun getPersons(transferFromXml: Boolean): Single<List<PersonEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}