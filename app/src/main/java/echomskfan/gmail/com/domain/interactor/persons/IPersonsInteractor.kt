package echomskfan.gmail.com.domain.interactor.persons

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.db.entity.PersonEntity
import echomskfan.gmail.com.domain.interactor.IBaseInteractor

interface IPersonsInteractor : IBaseInteractor {
    fun getPersonsLiveData(): LiveData<List<PersonEntity>>
    fun transferPersonsFromXmlToDb()
    fun personIdNotificationClicked(id: Int)
    fun personIdFavClicked(id: Int)
}