package echomskfan.gmail.com.presentation.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.presentation.BaseViewModel

class SettingsViewModel : BaseViewModel() {

    private val _nightModeLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val nightModeLiveData: LiveData<Boolean>
        get() = _nightModeLiveData

    init {
        applyNightModeLiveData()
    }

    fun onNightModeSwitchChecked(checked: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (checked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        applyNightModeLiveData()
    }

    private fun applyNightModeLiveData() {
        _nightModeLiveData.value =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }

}