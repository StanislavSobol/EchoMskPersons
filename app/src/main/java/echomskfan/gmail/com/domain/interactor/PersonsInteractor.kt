package echomskfan.gmail.com.domain.interactor

import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.utils.fromIoToMain

class PersonsInteractor(private val repository: IRepository) : IPersonsInteractor {
    override fun getPersons(copyFromXml: Boolean) = repository.getPersons(copyFromXml).fromIoToMain()
    override fun personIdNotificationClicked(id: Int) = repository.personIdNotificationClicked(id).fromIoToMain()

}