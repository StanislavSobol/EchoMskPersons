package echomskfan.gmail.com.domain.interactor.casts

import echomskfan.gmail.com.domain.repository.IRepository
import io.reactivex.Completable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CastsCoInteractor(private val repository: IRepository) : ICastsCoInteractor {

    override suspend fun transferCastsFromWebToDb(personId: Int, pageNum: Int) {
        withContext(Dispatchers.IO) { repository.transferCastsFromWebToDb(personId, pageNum) }
    }

    override suspend fun castIdFavClicked(castId: String) {
        withContext(Dispatchers.IO) { repository.castIdFavClicked(castId) }
    }
}