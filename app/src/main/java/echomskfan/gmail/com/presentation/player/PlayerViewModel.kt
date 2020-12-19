package echomskfan.gmail.com.presentation.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import echomskfan.gmail.com.BuildConfig
import echomskfan.gmail.com.domain.interactor.player.IPlayerCoInteractor
import echomskfan.gmail.com.domain.interactor.player.IPlayerInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val interactor: IPlayerInteractor,
    private val coInteractor: IPlayerCoInteractor
) : BaseViewModel() {

    val playerItemLiveData: LiveData<PlayerItem>
        get() = _playerItemLiveData

    private val _playerItemLiveData = MutableLiveData<PlayerItem>()

    fun loadData(castId: String?) {
        if (castId == null) {
            castIdIsNull()
        }

        if (BuildConfig.COROUTINES) {
            withProgress {
                _playerItemLiveData.postValue(coInteractor.getPlayerItem(castId))
            }
        } else {
            interactor.getPlayerItem(castId)
                .fromIoToMain()
                .subscribe(
                    {
                        _playerItemLiveData.value = it
                    },
                    {
                        catchThrowable(it)
                    }
                )
                .unsubscribeOnClear()
        }
    }

    fun playedTimeChanged(castId: String?, progressSec: Int) {
        if (castId == null) {
            castIdIsNull()
        }

        if (BuildConfig.COROUTINES) {
            viewModelScope.launch { coInteractor.updatePlayedTime(castId, progressSec) }
        } else {
            interactor.updatePlayedTime(castId, progressSec)
                .fromIoToMain()
                .subscribe()
                .unsubscribeOnClear()
        }

    }

    private fun castIdIsNull(): Nothing {
        throw IllegalStateException("castId must be set")
    }
}
