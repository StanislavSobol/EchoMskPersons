package echomskfan.gmail.com.domain.interactor.debugpanel

import echomskfan.gmail.com.domain.repository.IDebugRepository
import io.reactivex.Completable
import javax.inject.Inject

class DebugPanelInteractor @Inject constructor(private val debugRepository: IDebugRepository) : IDebugPanelInteractor {

    override fun deleteLastNevzorovCast(): Completable {
        return Completable.create {
            debugRepository.deleteLastNevzorovCast()
            it.onComplete()
        }
    }
}