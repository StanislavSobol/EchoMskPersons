package echomskfan.gmail.com.presentation.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.domain.interactor.player.IPlayerInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain

class PlayerViewModel(private val interactor: IPlayerInteractor) : BaseViewModel() {

    val playerItemLiveData: LiveData<PlayerItem>
        get() = _playerItemLiveData

    private val _playerItemLiveData = MutableLiveData<PlayerItem>()

    fun loadData(castId: String?) {
        if (castId == null) {
            castIdIsNull()
        }

        interactor.getPlayerItemSingle(castId)
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

    private fun castIdIsNull(): Nothing {
        throw IllegalStateException("castId must be set")
    }
}
