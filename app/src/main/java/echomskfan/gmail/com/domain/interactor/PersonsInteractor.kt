package echomskfan.gmail.com.domain.interactor

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.entity.PersonEntity
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class PersonsInteractor(private val repository: IRepository) : IPersonsInteractor {

    private val transferPersonsFromXmlToDbCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val personIdNotificationClickedCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val personIdFavClickedCompositeDDisposable: CompositeDisposable = CompositeDisposable()

    override fun getPersonsLiveData(): LiveData<List<PersonEntity>> {
        return repository.getPersonsLiveData()
    }

    override fun transferPersonsFromXmlToDb() {
        transferPersonsFromXmlToDbCompositeDisposable.clear()

        repository.transferPersonsFromXmlToDbCompletable()
            .fromIoToMain()
            .doOnError { e -> catchThrowable(e) }
            .subscribe()
            .toCompositeDisposable(transferPersonsFromXmlToDbCompositeDisposable)
    }

    override fun personIdNotificationClicked(id: Int) {
        personIdNotificationClickedCompositeDisposable.clear()

        repository.personIdNotificationClickedCompletable(id)
            .fromIoToMain()
            .doOnError { e -> catchThrowable(e) }
            .subscribe()
            .toCompositeDisposable(personIdNotificationClickedCompositeDisposable)
    }

    override fun personIdFavClicked(id: Int) {
        personIdFavClickedCompositeDDisposable.clear()

        repository.personIdFavClickedCompletable(id)
            .doOnError { e -> catchThrowable(e) }
            .fromIoToMain()
            .subscribe()
            .toCompositeDisposable(personIdFavClickedCompositeDDisposable)
    }

    private fun Disposable.toCompositeDisposable(compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(this)
    }
}