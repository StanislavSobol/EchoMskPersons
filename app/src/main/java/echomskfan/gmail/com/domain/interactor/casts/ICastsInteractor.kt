package echomskfan.gmail.com.domain.interactor.casts

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.db.entity.CastEntity
import echomskfan.gmail.com.domain.interactor.IBaseInteractor

interface ICastsInteractor : IBaseInteractor {
    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>
    fun tranferCastsFromWebToDb(personId: Int)
    fun castIdFavClicked(castId: String)
}