package echomskfan.gmail.com.domain.interactor.persons

import echomskfan.gmail.com.domain.repository.IRepository
import io.reactivex.Completable

class PersonsInteractor(private val repository: IRepository) : IPersonsInteractor {

    override fun getPersonsLiveData() = repository.getPersonsLiveData()

    override fun transferPersonsFromXmlToDb(): Completable {
        return repository.transferPersonsFromXmlToDbCompletable()
//            .fromIoToMain()
//            .doOnError { e -> catchThrowable(e) }
//            .subscribe()
//            .unsubscribeOnClear()
    }

    override fun personIdNotificationClicked(personId: Int): Completable {
        return repository.personIdNotificationClickedCompletable(personId)
//            .fromIoToMain()
//            .doOnError { e -> catchThrowable(e) }
//            .subscribe()
//            .unsubscribeOnClear()
    }

    override fun personIdFavClicked(personId: Int): Completable {
        return repository.personIdFavClickedCompletable(personId)
//            .doOnError { e -> catchThrowable(e) }
//            .fromIoToMain()
//            .subscribe()
//            .unsubscribeOnClear()
    }
}