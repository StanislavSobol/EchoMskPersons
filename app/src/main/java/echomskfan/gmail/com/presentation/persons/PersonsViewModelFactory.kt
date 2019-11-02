package echomskfan.gmail.com.presentation.persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.IPersonsInteractor

class PersonsViewModelFactory(private val interactor: IPersonsInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonsViewModelFactory(interactor) as T
    }
}
