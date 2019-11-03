package echomskfan.gmail.com.presentation.persons

import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.domain.interactor.IPersonsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.utils.catchThrowable

class PersonsViewModel(private val interactor: IPersonsInteractor) : BaseViewModel() {

    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val personsLiveData: MutableLiveData<List<PersonListItem>> = MutableLiveData()

    fun created(reallyCreated: Boolean) {
        if (reallyCreated) {
            interactor.copyPersonsFromXmlToDb()
        }
        interactor.getPersons()
            .subscribe({ list ->
                personsLiveData.postValue(PersonListItem.from(list))
            }, { t ->
                catchThrowable(t)
            })
            .unsubscribeOnDestroy()
    }

}
