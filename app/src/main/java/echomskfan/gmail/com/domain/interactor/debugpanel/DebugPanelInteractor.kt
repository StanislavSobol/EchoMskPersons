package echomskfan.gmail.com.domain.interactor.debugpanel

import echomskfan.gmail.com.domain.repository.IDebugRepository
import io.reactivex.Completable

class DebugPanelInteractor(private val debugRepository: IDebugRepository) : IDebugPanelInteractor {

    override fun deleteLastNevzorovCast(): Completable {
        return Completable.create {
            debugRepository.deleteLastNevzorovCast()
            it.onComplete()
        }
    }
}