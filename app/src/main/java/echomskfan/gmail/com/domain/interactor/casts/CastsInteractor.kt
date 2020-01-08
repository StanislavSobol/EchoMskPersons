package echomskfan.gmail.com.domain.interactor.casts

import echomskfan.gmail.com.domain.repository.IRepository
import io.reactivex.Completable

class CastsInteractor(private val repository: IRepository) : ICastsInteractor {

    override fun getCastsLiveDataForPerson(personId: Int) = repository.getCastsLiveDataForPerson(personId)

    override fun transferCastsFromWebToDb(personId: Int, pageNum: Int): Completable {
        return Completable.create {
            repository.transferCastsFromWebToDb(personId, pageNum)
            it.onComplete()
        }
    }

    override fun castIdFavClicked(castId: String): Completable {
        return Completable.create {
            repository.castIdFavClicked(castId)
            it.onComplete()
        }
    }
}