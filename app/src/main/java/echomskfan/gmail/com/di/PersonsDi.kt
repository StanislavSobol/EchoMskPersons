package echomskfan.gmail.com.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import echomskfan.gmail.com.domain.interactor.persons.IPersonsCoInteractor
import echomskfan.gmail.com.domain.interactor.persons.IPersonsInteractor
import echomskfan.gmail.com.domain.interactor.persons.PersonsCoInteractor
import echomskfan.gmail.com.domain.interactor.persons.PersonsInteractor
import echomskfan.gmail.com.presentation.persons.PersonsFragment
import echomskfan.gmail.com.presentation.persons.PersonsViewModel
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PersonsScope

@PersonsScope
@Component(
    modules = [(PersonsModule::class)],
    dependencies = [(AppComponent::class)]
)
interface PersonsComponent {
    fun inject(personsFragment: PersonsFragment)
}

@Module
abstract class PersonsModule {

    @PersonsScope
    @Binds
    abstract fun bindInteractor(interactor: PersonsInteractor): IPersonsInteractor

    @PersonsScope
    @Binds
    abstract fun bindCoInteractor(interactor: PersonsCoInteractor): IPersonsCoInteractor

    @PersonsScope
    @Binds
    @IntoMap
    @ViewModelKey(PersonsViewModel::class)
    abstract fun bindViewModel(viewModel: PersonsViewModel): ViewModel
}