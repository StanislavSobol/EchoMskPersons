package echomskfan.gmail.com.presentation.persons

import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.domain.interactor.IPersonsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.utils.catchThrowable

class PersonsViewModel(private val interactor: IPersonsInteractor) : BaseViewModel() {

    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val personsLiveData: MutableLiveData<List<PersonListItem>> = MutableLiveData()

    fun created(firstAttach: Boolean) {
        interactor.getPersons(firstAttach)
            .subscribe({ list ->
                personsLiveData.postValue(PersonListItem.from(list))
            }, { t ->
                catchThrowable(t)
            })
            .unsubscribeOnDestroy()
    }

    fun itemIdNotificationClicked(id: Int) {
        interactor.personIdNotificationClicked(id).subscribe()
//        interactor.getPersons(false)
//            .subscribe({ list ->
//                personsLiveData.postValue(PersonListItem.from(list))
//            }, { t ->
//                catchThrowable(t)
//            })
//            .unsubscribeOnDestroy()
    }

    private fun loadAll(firstAttach: Boolean = false) {

    }

}
