package echomskfan.gmail.com.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.ConfigInjector
import echomskfan.gmail.com.annotations.configinjector.ConfigParamBoolean
import echomskfan.gmail.com.annotations.configinjector.ConfigParamInteger
import echomskfan.gmail.com.domain.interactor.main.IMainInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent

class MainViewModel(private val interactor: IMainInteractor) : BaseViewModel() {

    @ConfigParamBoolean("disclaimerEnabled")
    var isDisclaimerEnabled = true

    @ConfigParamInteger("showOnlineStateDelayMSec")
    var showOnlineStateDelayMSec = 0

    private var isFavOn: Boolean = false
    private var firstOnlineFired: Boolean = false

    private val _favOnLiveDate = MutableLiveData<Boolean>()
    val favOnLiveDate: LiveData<Boolean>
        get() = _favOnLiveDate

    private val _disclaimerEnabledLiveDate = MutableLiveData<OneShotEvent<Boolean>>()
    val disclaimerEnabledLiveDate: LiveData<OneShotEvent<Boolean>>
        get() = _disclaimerEnabledLiveDate

    private val _showOnlineStateDelayMSecLiveData = MutableLiveData<OneShotEvent<Long>>()
    val showOnlineStateDelayMSecLiveData: LiveData<OneShotEvent<Long>>
        get() = _showOnlineStateDelayMSecLiveData

    private val _navigateToSettingLiveDate = MutableLiveData<OneShotEvent<Unit>>()
    val navigateToSettingsLiveDate: LiveData<OneShotEvent<Unit>>
        get() = _navigateToSettingLiveDate

    private val _navigateToDebugPanelLiveDate = MutableLiveData<OneShotEvent<Unit>>()
    val navigateToDebugPanelLiveDate: LiveData<OneShotEvent<Unit>>
        get() = _navigateToDebugPanelLiveDate

    private val _goesOnlineLiveDate = MutableLiveData<OneShotEvent<Boolean>>()
    val goesOnlineLiveDate: LiveData<OneShotEvent<Boolean>>
        get() = _goesOnlineLiveDate

    init {
        ConfigInjector.bind(this)
        loadMenuData()
        _disclaimerEnabledLiveDate.value = OneShotEvent(isDisclaimerEnabled)
        _showOnlineStateDelayMSecLiveData.value = OneShotEvent(showOnlineStateDelayMSec.toLong())
    }

    override fun onCleared() {
        interactor.setupWorkManager()
        super.onCleared()
    }

    fun loadMenuData() {
        isFavOn = interactor.isFavOn
        _favOnLiveDate.value = isFavOn
    }

    fun favMenuItemClicked() {
        interactor.isFavOn = !isFavOn
        loadMenuData()
    }

    fun settingsMenuItemClicked() {
        _navigateToSettingLiveDate.value = OneShotEvent(Unit)
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
