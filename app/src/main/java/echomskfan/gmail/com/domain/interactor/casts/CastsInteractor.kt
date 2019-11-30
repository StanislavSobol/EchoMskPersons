package echomskfan.gmail.com.domain.interactor.casts

import echomskfan.gmail.com.domain.repository.IRepository
import io.reactivex.Completable

class CastsInteractor(private val repository: IRepository) : ICastsInteractor {

    override fun getCastsLiveDataForPerson(personId: Int) = repository.getCastsLiveDataForPerson(personId)

    override fun tranferCastsFromWebToDb(personId: Int, pageNum: Int): Completable {
        return repository.tranferCastsFromWebToDbCompletable(personId, pageNum)
    }

    override fun castIdFavClicked(castId: String): Completable {
        return repository.castIdFavClickedCompletable(castId)
    }
}