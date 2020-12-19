package echomskfan.gmail.com.domain.interactor.player

import echomskfan.gmail.com.presentation.player.PlayerItem
import io.reactivex.Completable
import io.reactivex.Single

interface IPlayerInteractor {
    fun getPlayerItem(castId: String): Single<PlayerItem>
    fun updatePlayedTime(castId: String, progressSec: Int): Completable
}