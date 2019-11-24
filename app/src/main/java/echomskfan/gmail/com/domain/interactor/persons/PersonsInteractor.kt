package echomskfan.gmail.com.domain.interactor.persons

import echomskfan.gmail.com.domain.interactor.BaseInteractor
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain

class PersonsInteractor(private val repository: IRepository) : BaseInteractor(), IPersonsInteractor {

    override fun getPersonsLiveData() = repository.getPersonsLiveData()

    override fun transferPersonsFromXmlToDb() {
        repository.transferPersonsFromXmlToDbCompletable()
            .fromIoToMain()
            .doOnError { e -> catchThrowable(e) }
            .subscribe()
            .unsubscribeOnClear()
    }

    override fun personIdNotificationClicked(id: Int) {
        repository.personIdNotificationClickedCompletable(id)
            .fromIoToMain()
            .doOnError { e -> catchThrowable(e) }
            .subscribe()
            .unsubscribeOnClear()
    }

    override fun personIdFavClicked(personId: Int) {
        repository.personIdFavClickedCompletable(personId)
            .doOnError { e -> catchThrowable(e) }
            .fromIoToMain()
            .subscribe()
            .unsubscribeOnClear()
    }
}