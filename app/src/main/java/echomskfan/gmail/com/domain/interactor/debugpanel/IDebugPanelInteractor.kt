package echomskfan.gmail.com.domain.interactor.debugpanel

import io.reactivex.Completable

interface IDebugPanelInteractor {
    fun deleteLastNevzorovCast(): Completable
}