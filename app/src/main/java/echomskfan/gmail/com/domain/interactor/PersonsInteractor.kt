package echomskfan.gmail.com.domain.interactor

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.entity.PersonEntity
import echomskfan.gmail.com.utils.fromIoToMain

class PersonsInteractor(private val repository: IRepository) : IPersonsInteractor {

    override fun transferPersonsFromXmlToDb() {
        repository.transferPersonsFromXmlToDb()
    }

    override fun getPersonsLiveData(): LiveData<List<PersonEntity>> {
        return repository.getPersonsLiveData()
    }

    override fun personIdNotificationClickedEx(id: Int) {
        repository.personIdNotificationClickedEx(id)
    }

    override fun getPersons(copyFromXml: Boolean) = repository.getPersons(copyFromXml).fromIoToMain()
    override fun personIdNotificationClicked(id: Int) = repository.personIdNotificationClicked(id).fromIoToMain()
}