package echomskfan.gmail.com.presentation.persons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.domain.interactor.persons.IPersonsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent

class PersonsViewModel(private val interactor: IPersonsInteractor) : BaseViewModel(interactor) {

    val navigationLiveDate = MutableLiveData<OneShotEvent<Int>>()

    fun getPersonsLiveData(): LiveData<List<PersonListItem>> {
        return Transformations.map(interactor.getPersonsLiveData()) { list -> PersonListItem.from(list) }
    }

    fun firstAttach() {
        interactor.transferPersonsFromXmlToDb()
    }

    fun itemIdNotificationClicked(id: Int) {
        interactor.personIdNotificationClicked(id)
    }

    fun itemIdFavClicked(id: Int) {
        interactor.personIdFavClicked(id)
    }

    fun itemIdClicked(id: Int) {
        navigationLiveDate.value = OneShotEvent(id)
    }
}
