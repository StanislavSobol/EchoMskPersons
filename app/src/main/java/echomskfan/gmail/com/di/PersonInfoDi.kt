package echomskfan.gmail.com.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import echomskfan.gmail.com.di.core.AppComponent
import echomskfan.gmail.com.di.core.ViewModelKey
import echomskfan.gmail.com.domain.interactor.personinfo.IPersonInfoInteractor
import echomskfan.gmail.com.domain.interactor.personinfo.PersonInfoInteractor
import echomskfan.gmail.com.presentation.personinfo.PersonInfoFragment
import echomskfan.gmail.com.presentation.personinfo.PersonInfoViewModel
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PersonInfoScope

@PersonInfoScope
@Component(
    modules = [(PersonInfoModule::class)],
    dependencies = [(AppComponent::class)]
)
interface PersonInfoComponent {
    fun inject(personInfoFragment: PersonInfoFragment)
}

@Module
abstract class PersonInfoModule {

    @PersonInfoScope
    @Binds
    abstract fun bindInteractor(repository: PersonInfoInteractor): IPersonInfoInteractor

    @PersonInfoScope
    @Binds
    @IntoMap
    @ViewModelKey(PersonInfoViewModel::class)
    abstract fun bindViewModel(viewModel: PersonInfoViewModel): ViewModel
}