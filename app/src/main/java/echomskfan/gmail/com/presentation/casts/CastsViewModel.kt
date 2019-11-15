package echomskfan.gmail.com.presentation.casts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent
import echomskfan.gmail.com.presentation.player.PlayerItem

class CastsViewModel(private val interactor: ICastsInteractor) : BaseViewModel(interactor) {

    private val _startPlayLiveData = MutableLiveData<OneShotEvent<PlayerItem>>()

    val startPlayLiveData: LiveData<OneShotEvent<PlayerItem>>
        get() = _startPlayLiveData

    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastListItem>> {
        return Transformations.map(interactor.getCastsLiveDataForPerson(personId)) { list -> CastListItem.from(list) }
    }

    fun firstAttach(personId: Int) {
        interactor.tranferCastsFromWebToDb(personId)
    }

    fun playButtonClicked(castListItem: CastListItem) {
        _startPlayLiveData.value = OneShotEvent(PlayerItem.from(castListItem))
    }
}
