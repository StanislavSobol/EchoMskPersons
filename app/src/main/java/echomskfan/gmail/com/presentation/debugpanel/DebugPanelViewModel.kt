package echomskfan.gmail.com.presentation.debugpanel

import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.utils.fromIoToMain
import java.util.concurrent.TimeUnit

class DebugPanelViewModel(private val interactor: IDebugPanelInteractor) : BaseViewModel() {

    fun deleteLastNevzorovCastButtonClicked() {
        interactor.deleteLastNevzorovCast()
            .fromIoToMain()
            .delay(DELIBERATE_DELAY_SEC, TimeUnit.SECONDS)
            .withProgress()
            .subscribe()
            .unsubscribeOnClear()
    }

    companion object {
        private const val DELIBERATE_DELAY_SEC = 1L
    }
}