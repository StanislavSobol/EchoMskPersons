package echomskfan.gmail.com.presentation.debugpanel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelInteractor
import echomskfan.gmail.com.domain.interactor.main.IMainInteractor

class DebugPanelViewModelFactory(
    private val debugPanelInteractor: IDebugPanelInteractor,
    private val mainInteractor: IMainInteractor
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DebugPanelViewModel(debugPanelInteractor, mainInteractor) as T
    }
}
