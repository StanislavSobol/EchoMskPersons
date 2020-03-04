package echomskfan.gmail.com.presentation.debugpanel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelInteractor

class DebugPanelViewModelFactory(private val interactor: IDebugPanelInteractor) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DebugPanelViewModel(interactor) as T
    }
}
