package echomskfan.gmail.com.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import echomskfan.gmail.com.domain.interactor.debugpanel.DebugPanelInteractor
import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelInteractor
import echomskfan.gmail.com.domain.repository.DebugRepository
import echomskfan.gmail.com.domain.repository.IDebugRepository
import echomskfan.gmail.com.presentation.debugpanel.DebugPanelFragment
import echomskfan.gmail.com.presentation.debugpanel.DebugPanelViewModel
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DebugPanelScope

@DebugPanelScope
@Component(
    modules = [(DebugPanelModule::class)],
    dependencies = [(AppComponent::class)]
)
interface DebugPanelComponent {
    fun inject(debugPanelFragment: DebugPanelFragment)
}

@Module
abstract class DebugPanelModule {

    @DebugPanelScope
    @Binds
    abstract fun bindRepository(repository: DebugRepository): IDebugRepository

    @DebugPanelScope
    @Binds
    abstract fun bindInteractor(interactor: DebugPanelInteractor): IDebugPanelInteractor

    @DebugPanelScope
    @Binds
    @IntoMap
    @ViewModelKey(DebugPanelViewModel::class)
    abstract fun bindViewModel(viewModel: DebugPanelViewModel): ViewModel
}