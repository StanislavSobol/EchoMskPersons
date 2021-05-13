package echomskfan.gmail.com.domain.repository

import echomskfan.gmail.com.data.db.PersonsDatabase
import javax.inject.Inject

class DebugRepository @Inject constructor(database: PersonsDatabase) : IDebugRepository {
    // TODO provide all the DAOs vie Dagger ()
    private var debugDao = database.getDebugDao()

    override fun deleteLastNevzorovCast() {
        debugDao.deleteLastCastForPerson(IDebugRepository.NEVZOROV_ID)
    }
}