package echomskfan.gmail.com.domain.interactor.persons

import echomskfan.gmail.com.domain.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersonsCoInteractor(private val repository: IRepository) : IPersonsCoInteractor {
    override suspend fun transferPersonsFromXmlToDb() {
        withContext(Dispatchers.IO) {
            repository.transferPersonsFromXmlToDb()
        }
    }
}