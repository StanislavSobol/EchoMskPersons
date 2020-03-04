package echomskfan.gmail.com.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.domain.interactor.main.IMainInteractor
import echomskfan.gmail.com.presentation.BaseViewModel

class MainViewModel(private val interactor: IMainInteractor) : BaseViewModel() {

    private var isFavOn: Boolean = false

    private val _favOnLiveDate = MutableLiveData<Boolean>()
    private val _debugPanelEnabledLiveDate = MutableLiveData<Boolean>()

    val favOnLiveDate: LiveData<Boolean>
        get() = _favOnLiveDate

    val debugPanelEnabledLiveDate: LiveData<Boolean>
        get() = _debugPanelEnabledLiveDate

    fun loadData() {
        isFavOn = interactor.isFavOn
        _favOnLiveDate.value = isFavOn
        _debugPanelEnabledLiveDate.value = interactor.isDebugPanelEnabled

        Log.d("SSS", "interactor.isDebugPanelEnabled = " + interactor.isDebugPanelEnabled)
    }

    fun onFavMenuItemClick() {
        interactor.isFavOn = !isFavOn
        loadData()
    }

}
