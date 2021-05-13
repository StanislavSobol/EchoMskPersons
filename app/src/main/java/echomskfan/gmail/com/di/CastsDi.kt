package echomskfan.gmail.com.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import echomskfan.gmail.com.di.core.AppComponent
import echomskfan.gmail.com.di.core.ViewModelKey
import echomskfan.gmail.com.domain.interactor.casts.CastsCoInteractor
import echomskfan.gmail.com.domain.interactor.casts.CastsInteractor
import echomskfan.gmail.com.domain.interactor.casts.ICastsCoInteractor
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor
import echomskfan.gmail.com.presentation.casts.CastsFragment
import echomskfan.gmail.com.presentation.casts.CastsViewModel
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CastsScope

@CastsScope
@Component(
    modules = [(CastsModule::class)],
    dependencies = [(AppComponent::class)]
)
interface CastsComponent {
    fun inject(castsFragment: CastsFragment)
}

@Module
abstract class CastsModule {

    @CastsScope
    @Binds
    abstract fun bindInteractor(interactor: CastsInteractor): ICastsInteractor

    @CastsScope
    @Binds
    abstract fun bindCoInteractor(interactor: CastsCoInteractor): ICastsCoInteractor

    @CastsScope
    @Binds
    @IntoMap
    @ViewModelKey(CastsViewModel::class)
    abstract fun bindViewModel(viewModel: CastsViewModel): ViewModel
}