package echomskfan.gmail.com.domain.interactor

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.entity.PersonEntity

interface IPersonsInteractor {
    fun getPersonsLiveData(): LiveData<List<PersonEntity>>
    fun transferPersonsFromXmlToDb()
    fun personIdNotificationClicked(id: Int)
    fun personIdFavClicked(id: Int)
}