package echomskfan.gmail.com.presentation.personinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.domain.interactor.personinfo.IPersonInfoInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import javax.inject.Inject

class PersonInfoViewModel @Inject constructor(private val interactor: IPersonInfoInteractor) : BaseViewModel() {

    fun getPersonLiveData(personId: Int): LiveData<PersonInfoViewEntity> {
        return Transformations.map(interactor.getPersonLiveData(personId)) { item ->
            PersonInfoViewEntity.from(
                item
            )
        }
    }
}