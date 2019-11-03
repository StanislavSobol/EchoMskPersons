package echomskfan.gmail.com.presentation.persons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.domain.interactor.IPersonsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel

class PersonsViewModel(private val interactor: IPersonsInteractor) : BaseViewModel() {

    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()

    //val personsLiveData: MutableLiveData<List<PersonListItem>> = MutableLiveData()

    fun getPersonsLiveData(): LiveData<List<PersonListItem>> {
        return Transformations.map(interactor.getPersonsLiveData()) { list -> PersonListItem.from(list) }
    }

    fun created() {
        interactor.transferPersonsFromXmlToDb()


//        interactor.getPersons(firstAttach)
//            .subscribe({ list ->
//                personsLiveData.postValue(PersonListItem.from(list))
//            }, { t ->
//                catchThrowable(t)
//            })
//            .unsubscribeOnDestroy()
    }

//    fun createdLd(firstAttach: Boolean) {
//        personsLiveData. interactor.getPersonsLd(firstAttach)
//    }

    fun itemIdNotificationClicked(id: Int) {
        interactor.personIdNotificationClickedEx(id)
        //    interactor.personIdNotificationClicked(id).subscribe()
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
