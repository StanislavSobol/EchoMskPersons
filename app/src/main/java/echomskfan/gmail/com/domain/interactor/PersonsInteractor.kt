package echomskfan.gmail.com.domain.interactor

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.entity.PersonEntity

class PersonsInteractor(private val repository: IRepository) : IPersonsInteractor {

    override fun getPersonsLiveData(): LiveData<List<PersonEntity>> {
        return repository.getPersonsLiveData()
    }

    override fun transferPersonsFromXmlToDb() {
        repository.transferPersonsFromXmlToDb()
    }

    override fun personIdNotificationClicked(id: Int) {
        repository.personIdNotificationClicked(id)
    }
}