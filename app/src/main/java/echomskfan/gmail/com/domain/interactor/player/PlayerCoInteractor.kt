package echomskfan.gmail.com.domain.interactor.player

import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.presentation.player.PlayerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerCoInteractor(private val repository: IRepository) : IPlayerCoInteractor {

    override suspend fun getPlayerItem(castId: String): PlayerItem {
        return withContext(Dispatchers.IO) { repository.getPlayerItem(castId) }
    }

    override suspend fun updatePlayedTime(castId: String, progressSec: Int) {
        withContext(Dispatchers.IO) { repository.updatePlayedTime(castId, progressSec) }
    }
}