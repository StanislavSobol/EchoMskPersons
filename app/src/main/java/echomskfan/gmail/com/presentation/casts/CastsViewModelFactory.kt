package echomskfan.gmail.com.presentation.casts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.casts.ICastsCoInteractor
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor

@Deprecated("kill it")
class CastsViewModelFactory(private val interactor: ICastsInteractor,
                            private val coInteractor: ICastsCoInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CastsViewModel(interactor, coInteractor) as T
    }
}
