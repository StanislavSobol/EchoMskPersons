package echomskfan.gmail.com.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.main.IMainInteractor

class MainViewModelFactory(val interactor: IMainInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(interactor) as T
    }
}
