package echomskfan.gmail.com.presentation.persons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.persons.IPersonsCoInteractor
import echomskfan.gmail.com.domain.interactor.persons.IPersonsInteractor

class PersonsViewModelFactory(private val interactor: IPersonsInteractor,
                              private val coInteractor: IPersonsCoInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonsViewModel(interactor, coInteractor) as T
    }
}
