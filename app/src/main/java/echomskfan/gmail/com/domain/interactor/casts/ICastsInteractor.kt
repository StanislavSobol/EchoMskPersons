package echomskfan.gmail.com.domain.interactor.casts

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.db.entity.CastEntity
import io.reactivex.Completable

interface ICastsInteractor {
    fun getCastsLiveDataForPerson(personId: Int): LiveData<List<CastEntity>>
    fun tranferCastsFromWebToDb(personId: Int, pageNum: Int): Completable
    fun castIdFavClicked(castId: String): Completable
}