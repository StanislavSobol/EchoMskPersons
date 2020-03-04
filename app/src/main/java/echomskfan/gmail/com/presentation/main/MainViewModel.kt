package echomskfan.gmail.com.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.domain.interactor.main.IMainInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent

class MainViewModel(private val interactor: IMainInteractor) : BaseViewModel() {

    private var isFavOn: Boolean = false

    private val _favOnLiveDate = MutableLiveData<Boolean>()
    private val _debugPanelEnabledLiveDate = MutableLiveData<Boolean>()
    private val _navigateToCastsLiveDate = MutableLiveData<OneShotEvent<Unit>>()

    val favOnLiveDate: LiveData<Boolean>
        get() = _favOnLiveDate

    val debugPanelEnabledLiveDate: LiveData<Boolean>
        get() = _debugPanelEnabledLiveDate

    val navigateToCastsLiveDate: LiveData<OneShotEvent<Unit>>
        get() = _navigateToCastsLiveDate

    fun loadData() {
        isFavOn = interactor.isFavOn
        _favOnLiveDate.value = isFavOn
        _debugPanelEnabledLiveDate.value = interactor.isDebugPanelEnabled
    }

    fun favMenuItemClick() {
        interactor.isFavOn = !isFavOn
        loadData()
    }

    fun debugPanelMenuItemClick() {
        _navigateToCastsLiveDate.value = OneShotEvent(Unit)
    }
}
