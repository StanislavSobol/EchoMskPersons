package echomskfan.gmail.com.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.domain.interactor.main.IMainInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent

class MainViewModel(private val interactor: IMainInteractor) : BaseViewModel() {

    private var isFavOn: Boolean = false
    private var firstOnlineFired: Boolean = false

    private val _favOnLiveDate = MutableLiveData<Boolean>()
    val favOnLiveDate: LiveData<Boolean>
        get() = _favOnLiveDate

    private val _debugPanelEnabledLiveDate = MutableLiveData<Boolean>()
    val debugPanelEnabledLiveDate: LiveData<Boolean>
        get() = _debugPanelEnabledLiveDate

    private val _disclaimerEnabledLiveDate = MutableLiveData<OneShotEvent<Boolean>>()
    val disclaimerEnabledLiveDate: LiveData<OneShotEvent<Boolean>>
        get() = _disclaimerEnabledLiveDate

    private val _showOnlineStateDelayMSec = MutableLiveData<OneShotEvent<Long>>()
    val showOnlineStateDelayMSec: LiveData<OneShotEvent<Long>>
        get() = _showOnlineStateDelayMSec

    private val _navigateToDebugPanelLiveDate = MutableLiveData<OneShotEvent<Unit>>()
    val navigateToDebugPanelLiveDate: LiveData<OneShotEvent<Unit>>
        get() = _navigateToDebugPanelLiveDate

    private val _goesOnlineLiveDate = MutableLiveData<OneShotEvent<Boolean>>()
    val goesOnlineLiveDate: LiveData<OneShotEvent<Boolean>>
        get() = _goesOnlineLiveDate

    fun loadMenuData() {
        isFavOn = interactor.isFavOn
        _favOnLiveDate.value = isFavOn

        _debugPanelEnabledLiveDate.value = interactor.isDebugPanelEnabled
    }

    fun loadData() {
        loadMenuData()
        _disclaimerEnabledLiveDate.value = OneShotEvent(interactor.isDisclaimerEnabled)
        _showOnlineStateDelayMSec.value = OneShotEvent(interactor.showOnlineStateDelayMSec)
    }

    fun favMenuItemClicked() {
        interactor.isFavOn = !isFavOn
        loadMenuData()
    }

    fun debugPanelMenuItemClicked() {
        _navigateToDebugPanelLiveDate.value = OneShotEvent(Unit)
    }

    fun connectivityStateChanged(online: Boolean) {
        if (firstOnlineFired) {
            _goesOnlineLiveDate.value = OneShotEvent(online)
        } else {
            if (!online) {
                _goesOnlineLiveDate.value = OneShotEvent(online)
            }
            firstOnlineFired = true
        }
    }
}
