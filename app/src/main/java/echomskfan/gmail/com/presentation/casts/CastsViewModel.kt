package echomskfan.gmail.com.presentation.casts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent

class CastsViewModel(private val interactor: ICastsInteractor) : BaseViewModel(interactor) {

    private val _startPlayLiveData = MutableLiveData<OneShotEvent<String>>()

    val startPlayLiveData: LiveData<OneShotEvent<String>>
        get() = _startPlayLiveData

    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastListItem>> {
        return Transformations.map(interactor.getCastsLiveDataForPerson(personId)) { list -> CastListItem.from(list) }
    }

    fun firstAttach(personId: Int) {
        interactor.tranferCastsFromWebToDb(personId)
    }

    fun playButtonClicked(castListItem: CastListItem) {
        _startPlayLiveData.value = OneShotEvent(castListItem.id)
    }

    fun itemIdFavClicked(castId: String) {
        interactor.castIdFavClicked(castId)
    }
}
