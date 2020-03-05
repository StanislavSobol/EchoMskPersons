package echomskfan.gmail.com.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.domain.interactor.main.IMainInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent

class MainViewModel(private val interactor: IMainInteractor) : BaseViewModel() {

    private var isFavOn: Boolean = false

    private val _favOnLiveDate = MutableLiveData<Boolean>()
    val favOnLiveDate: LiveData<Boolean>
        get() = _favOnLiveDate

    private val _debugPanelEnabledLiveDate = MutableLiveData<Boolean>()
    val debugPanelEnabledLiveDate: LiveData<Boolean>
        get() = _debugPanelEnabledLiveDate

    private val _disclaimerEnabledLiveDate = MutableLiveData<OneShotEvent<Boolean>>()
    val disclaimerEnabledLiveDate: LiveData<OneShotEvent<Boolean>>
        get() = _disclaimerEnabledLiveDate

    private val _navigateToDebugPanelLiveDate = MutableLiveData<OneShotEvent<Unit>>()
    val navigateToDebugPanelLiveDate: LiveData<OneShotEvent<Unit>>
        get() = _navigateToDebugPanelLiveDate

    fun loadMenuData() {
        isFavOn = interactor.isFavOn
        _favOnLiveDate.value = isFavOn

        _debugPanelEnabledLiveDate.value = interactor.isDebugPanelEnabled
    }

    fun loadData() {
        loadMenuData()
        _disclaimerEnabledLiveDate.value = OneShotEvent(interactor.isDisclaimerEnabled)
    }

    fun favMenuItemClick() {
        interactor.isFavOn = !isFavOn
        loadMenuData()
    }

    fun debugPanelMenuItemClick() {
        _navigateToDebugPanelLiveDate.value = OneShotEvent(Unit)
    }
}
