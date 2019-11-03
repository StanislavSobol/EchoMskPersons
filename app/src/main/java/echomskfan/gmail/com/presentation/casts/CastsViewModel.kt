package echomskfan.gmail.com.presentation.casts

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel

class CastsViewModel(private val interactor: ICastsInteractor) : BaseViewModel(interactor) {

    fun getCastsLiveData(): LiveData<List<CastListItem>>? {
        return null
        // return Transformations.map(interactor.getCastsLiveData()) { list -> CastListItem.from(list) }
    }

    fun firstAttach() {
//        interactor.transferPersonsFromXmlToDb()
    }

    fun itemIdNotificationClicked(id: Int) {
//        interactor.personIdNotificationClicked(id)
    }

    fun itemIdFavClicked(id: Int) {
//        interactor.personIdFavClicked(id)
    }

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
    }
}
