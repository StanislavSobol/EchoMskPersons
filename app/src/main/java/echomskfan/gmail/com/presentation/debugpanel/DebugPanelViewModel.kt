package echomskfan.gmail.com.presentation.debugpanel

import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelInteractor
import echomskfan.gmail.com.domain.interactor.main.IMainInteractor
import echomskfan.gmail.com.domain.interactor.main.MainInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.utils.fromIoToMain
import java.util.concurrent.TimeUnit

class DebugPanelViewModel(
    private val debugPanelInteractor: IDebugPanelInteractor,
    private val mainInteractor: IMainInteractor
) : BaseViewModel() {

    fun deleteLastNevzorovCastButtonClicked() {
        debugPanelInteractor.deleteLastNevzorovCast()
            .fromIoToMain()
            .delay(DELIBERATE_DELAY_SEC, TimeUnit.SECONDS)
            .withProgress()
            .subscribe()
            .unsubscribeOnClear()
    }

    fun workManagerActionButtonClicked() {
        (mainInteractor as MainInteractor).workManagerMainAction()
    }

    companion object {
        private const val DELIBERATE_DELAY_SEC = 1L
    }
}