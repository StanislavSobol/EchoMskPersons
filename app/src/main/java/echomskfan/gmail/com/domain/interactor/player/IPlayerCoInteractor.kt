package echomskfan.gmail.com.domain.interactor.player

import echomskfan.gmail.com.presentation.player.PlayerItem

interface IPlayerCoInteractor {
    suspend fun getPlayerItem(castId: String): PlayerItem
    suspend fun updatePlayedTime(castId: String, progressSec: Int)
}