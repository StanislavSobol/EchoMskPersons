package echomskfan.gmail.com.domain.interactor.casts

import echomskfan.gmail.com.domain.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class CastsCoInteractor @Inject constructor(private val repository: IRepository) : ICastsCoInteractor {

    override suspend fun transferCastsFromWebToDb(personId: Int, pageNum: Int) {
        withContext(Dispatchers.IO) { repository.transferCastsFromWebToDb(personId, pageNum) }
    }

    override suspend fun castIdFavClicked(castId: String) {
        withContext(Dispatchers.IO) { repository.castIdFavClicked(castId) }
    }

    override suspend fun getTextUrlByCastId(castId: String): String? {
        var result: String?
        withContext(Dispatchers.IO) { result = repository.getTextUrlByCastId(castId) }
        return result
    }
}