package echomskfan.gmail.com.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.domain.interactor.main.IMainInteractor
import echomskfan.gmail.com.presentation.BaseViewModel

class MainViewModel(private val interactor: IMainInteractor) : BaseViewModel() {

    private var isFavOn: Boolean = false

    private val _favOnLiveDate = MutableLiveData<Boolean>()

    val favOnLiveDate: LiveData<Boolean>
        get() = _favOnLiveDate

    fun loadData() {
        isFavOn = interactor.isFavOn
        _favOnLiveDate.value = isFavOn
    }

    fun onFavMenuItemClick() {
        interactor.isFavOn = !isFavOn
        loadData()
    }

}
