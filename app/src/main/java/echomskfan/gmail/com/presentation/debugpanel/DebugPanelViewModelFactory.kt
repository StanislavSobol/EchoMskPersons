package echomskfan.gmail.com.presentation.debugpanel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.checknew.ICheckNewInteractor
import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelCoInteractor
import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelInteractor

class DebugPanelViewModelFactory(
    private val debugPanelInteractor: IDebugPanelInteractor,
    private val debugPanelCoInteractor: IDebugPanelCoInteractor,
    private val checkNewInteractor: ICheckNewInteractor
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DebugPanelViewModel(debugPanelInteractor, debugPanelCoInteractor, checkNewInteractor) as T
    }
}
