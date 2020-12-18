package echomskfan.gmail.com.domain.interactor.player

import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.presentation.player.PlayerItem
import io.reactivex.Single

class PlayerInteractor(private val repository: IRepository) : IPlayerInteractor {

    override fun getPlayerItem(castId: String): Single<PlayerItem> {
        return Single.create {
            it.onSuccess(repository.getPlayerItem(castId))
        }
    }
}