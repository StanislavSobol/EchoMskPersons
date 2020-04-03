package echomskfan.gmail.com.domain.interactor.personinfo

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.db.entity.PersonEntity

interface IPersonInfoInteractor {
    fun getPersonLiveData(personId: Int): LiveData<PersonEntity>
}