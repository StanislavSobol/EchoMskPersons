package echomskfan.gmail.com.domain.interactor.persons

import io.reactivex.Completable

interface IPersonsCoInteractor {
    suspend fun transferPersonsFromXmlToDb()
    suspend fun personIdNotificationClicked(personId: Int)
    suspend fun personIdFavClicked(personId: Int)
}