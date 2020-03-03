package echomskfan.gmail.com.presentation.persons

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.domain.interactor.persons.IPersonsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain

class PersonsViewModel(private val interactor: IPersonsInteractor) : BaseViewModel() {

    private val _navigationLiveDate = MutableLiveData<OneShotEvent<Pair<Int, View>>>()

    val navigationLiveDate: LiveData<OneShotEvent<Pair<Int, View>>>
        get() = _navigationLiveDate

    fun getPersonsLiveData(): LiveData<List<PersonListItem>> {
        return Transformations.map(interactor.getPersonsLiveData()) { list -> PersonListItem.from(list) }
    }

    fun loadData() {
        interactor.transferPersonsFromXmlToDb()
            .fromIoToMain()
            .withProgress()
            .doOnError { e -> catchThrowable(e) } // TODO to extension
            .subscribe()
            .unsubscribeOnClear()
    }

    fun itemIdNotificationClicked(id: Int) {
        interactor.personIdNotificationClicked(id)
            .fromIoToMain()
            .doOnError { e -> catchThrowable(e) }
            .subscribe()
            .unsubscribeOnClear()
    }

    fun itemIdFavClicked(id: Int) {
        interactor.personIdFavClicked(id)
            .fromIoToMain()
            .doOnError { e -> catchThrowable(e) }
            .subscribe()
            .unsubscribeOnClear()
    }

    fun itemIdClicked(id: Int, transitionView: View) {
        _navigationLiveDate.value = OneShotEvent(Pair(id, transitionView))
    }
}
