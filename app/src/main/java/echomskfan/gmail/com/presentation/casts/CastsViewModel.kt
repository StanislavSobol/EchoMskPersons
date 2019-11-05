package echomskfan.gmail.com.presentation.casts

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel

class CastsViewModel(private val interactor: ICastsInteractor) : BaseViewModel(interactor) {

    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastListItem>> {
        return Transformations.map(interactor.getCastsLiveDataForPerson(personId)) { list -> CastListItem.from(list) }
    }

    fun firstAttach(personId: Int) {
        interactor.tranferCastsFromWebToDb(personId)
    }
}
