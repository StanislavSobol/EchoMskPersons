package echomskfan.gmail.com.presentation.debugpanel

import echomskfan.gmail.com.BuildConfig
import echomskfan.gmail.com.domain.interactor.checknew.ICheckNewInteractor
import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelCoInteractor
import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.utils.fromIoToMain
import io.reactivex.Completable
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class DebugPanelViewModel(
    private val debugPanelInteractor: IDebugPanelInteractor,
    private val debugPanelCoInteractor: IDebugPanelCoInteractor,
    private val checkNewInteractor: ICheckNewInteractor

) : BaseViewModel() {

    fun deleteLastNevzorovCastButtonClicked() {
        if (BuildConfig.COROUTINES) {
            withProgress {
                debugPanelCoInteractor.deleteLastNevzorovCast()
                delay(DELIBERATE_DELAY_SEC * 1000)
            }
        } else {
            debugPanelInteractor.deleteLastNevzorovCast()
                .fromIoToMain()
                .delay(DELIBERATE_DELAY_SEC, TimeUnit.SECONDS)
                .withProgress()
                .subscribe()
                .unsubscribeOnClear()
        }
    }

    fun workManagerActionButtonClicked() {
        Completable.create {
            checkNewInteractor.checkNewCast()
            it.onComplete()
        }
            .fromIoToMain()
            .withProgress()
            .subscribe()
            .unsubscribeOnClear()
    }

    companion object {
        private const val DELIBERATE_DELAY_SEC = 1L
    }
}