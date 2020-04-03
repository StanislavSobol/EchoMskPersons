package echomskfan.gmail.com.presentation.personinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.personinfo.IPersonInfoInteractor

class PersonInfoViewModelFactory(private val interactor: IPersonInfoInteractor) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PersonInfoViewModel(interactor) as T
    }
}