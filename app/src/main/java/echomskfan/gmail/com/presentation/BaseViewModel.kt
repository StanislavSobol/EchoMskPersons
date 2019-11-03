package echomskfan.gmail.com.presentation

import androidx.lifecycle.ViewModel
import echomskfan.gmail.com.domain.interactor.IBaseInteractor

abstract class BaseViewModel(private val interactor: IBaseInteractor) : ViewModel() {

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
    }
}