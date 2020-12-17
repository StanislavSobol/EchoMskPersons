package echomskfan.gmail.com.domain.interactor.debugpanel

import echomskfan.gmail.com.domain.repository.IDebugRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DebugPanelCoInteractor(private val debugRepository: IDebugRepository) :
    IDebugPanelCoInteractor {

    override suspend fun deleteLastNevzorovCast() {
        withContext(Dispatchers.IO) {
            debugRepository.deleteLastNevzorovCast()
        }
    }
}