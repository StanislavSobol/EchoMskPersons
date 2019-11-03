package echomskfan.gmail.com.presentation.persons

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.domain.interactor.persons.IPersonsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel

class PersonsViewModel(private val interactor: IPersonsInteractor) : BaseViewModel() {

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

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
    }
}
