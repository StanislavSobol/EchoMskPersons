package echomskfan.gmail.com.presentation.persons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.BuildConfig
import echomskfan.gmail.com.domain.interactor.persons.IPersonsCoInteractor
import echomskfan.gmail.com.domain.interactor.persons.IPersonsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain
import javax.inject.Inject

class PersonsViewModel @Inject constructor(
    private val interactor: IPersonsInteractor,
    private val coInteractor: IPersonsCoInteractor
) : BaseViewModel() {

    private val _navigateToCastsLiveDate = MutableLiveData<OneShotEvent<Int>>()
    val navigateToCastsLiveDate: LiveData<OneShotEvent<Int>>
        get() = _navigateToCastsLiveDate

    private val _navigateToPersonInfoLiveDate = MutableLiveData<OneShotEvent<Int>>()
    val navigateToPersonInfoLiveDate: LiveData<OneShotEvent<Int>>
        get() = _navigateToPersonInfoLiveDate

    init {
        if (BuildConfig.COROUTINES) {
            withProgress { coInteractor.transferPersonsFromXmlToDb() }
        } else {
            interactor.transferPersonsFromXmlToDb()
                .fromIoToMain()
                .withProgress()
                .doOnError { e -> catchThrowable(e) } // TODO to extension
                .subscribe()
                .unsubscribeOnClear()
        }
    }

    fun getPersonsLiveData(): LiveData<List<PersonListItem>> {
        return Transformations.map(interactor.getPersonsLiveData()) { list ->
            PersonListItem.from(
                list
            )
        }
    }

    fun personItemNotificationClicked(id: Int) {
        if (BuildConfig.COROUTINES) {
            withProgress { coInteractor.personIdNotificationClicked(id) }
        } else {
            interactor.personIdNotificationClicked(id)
                .fromIoToMain()
                .doOnError { e -> catchThrowable(e) }
                .subscribe()
                .unsubscribeOnClear()
        }
    }

    fun personItemFavClicked(id: Int) {
        if (BuildConfig.COROUTINES) {
            withProgress { coInteractor.personIdFavClicked(id) }
        } else {
            interactor.personIdFavClicked(id)
                .fromIoToMain()
                .doOnError { e -> catchThrowable(e) }
                .subscribe()
                .unsubscribeOnClear()
        }
    }

    fun personItemClicked(id: Int) {
        _navigateToCastsLiveDate.value = OneShotEvent(id)
    }

    fun personItemInfoClicked(id: Int) {
        _navigateToPersonInfoLiveDate.value = OneShotEvent(id)
    }
}
