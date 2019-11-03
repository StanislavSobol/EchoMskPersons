package echomskfan.gmail.com.presentation.casts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor

class CastsViewModelFactory(private val interactor: ICastsInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CastsViewModel(interactor) as T
    }
}
