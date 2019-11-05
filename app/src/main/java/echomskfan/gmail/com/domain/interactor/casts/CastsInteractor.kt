package echomskfan.gmail.com.domain.interactor.casts

import echomskfan.gmail.com.domain.interactor.BaseInteractor
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain

class CastsInteractor(private val repository: IRepository) : BaseInteractor(), ICastsInteractor {

    override fun getCastsLiveDataForPerson(personId: Int) = repository.getCastsLiveDataForPerson(personId)

    override fun tranferCastsFromWebToDb(personId: Int) {
        repository.tranferCastsFromWebToDbCompletable(personId)
            .fromIoToMain()
            .doOnError { e -> catchThrowable(e) }
            .subscribe()
            .unsubscribeOnClear()
    }
}