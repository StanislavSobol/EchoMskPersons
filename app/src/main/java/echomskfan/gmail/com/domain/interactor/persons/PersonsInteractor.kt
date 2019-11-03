package echomskfan.gmail.com.domain.interactor.persons

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.domain.interactor.BaseInteractor
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.entity.PersonEntity
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain

class PersonsInteractor(private val repository: IRepository) : BaseInteractor(),
    IPersonsInteractor {

    override fun getPersonsLiveData(): LiveData<List<PersonEntity>> {
        return repository.getPersonsLiveData()
    }

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

    override fun personIdFavClicked(id: Int) {
        repository.personIdFavClickedCompletable(id)
            .doOnError { e -> catchThrowable(e) }
            .fromIoToMain()
            .subscribe()
            .unsubscribeOnClear()
    }
}