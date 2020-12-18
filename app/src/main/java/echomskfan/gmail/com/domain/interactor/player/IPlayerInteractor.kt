package echomskfan.gmail.com.domain.interactor.player

import echomskfan.gmail.com.presentation.player.PlayerItem
import io.reactivex.Single

interface IPlayerInteractor {
    fun getPlayerItem(castId: String): Single<PlayerItem>
}