package echomskfan.gmail.com.domain.interactor.personinfo

import androidx.lifecycle.LiveData
import echomskfan.gmail.com.data.db.entity.PersonEntity
import echomskfan.gmail.com.domain.repository.IRepository
import javax.inject.Inject

class PersonInfoInteractor @Inject constructor(private val repository: IRepository) : IPersonInfoInteractor {
    override fun getPersonLiveData(personId: Int): LiveData<PersonEntity> {
        return repository.getPersonLiveData(personId)
    }
}