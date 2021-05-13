package echomskfan.gmail.com.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.corelib.isNightMode
import com.example.corelib.setDefaultNightMode
import echomskfan.gmail.com.presentation.BaseViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor() : BaseViewModel() {

    private val _nightModeLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val nightModeLiveData: LiveData<Boolean>
        get() = _nightModeLiveData

    init {
        applyNightModeLiveData()
    }

    fun onNightModeSwitchChecked(checked: Boolean) {
        setDefaultNightMode(checked)
        applyNightModeLiveData()
    }

    private fun applyNightModeLiveData() {
        _nightModeLiveData.value = isNightMode()
    }
}