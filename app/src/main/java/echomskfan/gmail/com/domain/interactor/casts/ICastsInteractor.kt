package echomskfan.gmail.com.domain.interactor.casts

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.domain.interactor.IBaseInteractor
import echomskfan.gmail.com.entity.CastEntity

interface ICastsInteractor : IBaseInteractor {
    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>
}