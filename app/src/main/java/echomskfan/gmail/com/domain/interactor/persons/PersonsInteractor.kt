package echomskfan.gmail.com.domain.interactor.persons

import echomskfan.gmail.com.domain.repository.IRepository
import io.reactivex.Completable

class PersonsInteractor(private val repository: IRepository) : IPersonsInteractor {

    override fun getPersonsLiveData() = repository.getPersonsLiveData()

    override fun transferPersonsFromXmlToDb(): Completable {
        return Completable.create {
            repository.transferPersonsFromXmlToDb()
            it.onComplete()
        }
    }

    override fun personIdNotificationClicked(personId: Int): Completable {
        return Completable.create {
            repository.personIdNotificationClicked(personId)
            it.onComplete()
        }
    }

    override fun personIdFavClicked(personId: Int): Completable {
        return Completable.create {
            repository.personIdFavClicked(personId)
            it.onComplete()
        }
    }
}