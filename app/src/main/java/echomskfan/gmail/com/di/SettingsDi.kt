package echomskfan.gmail.com.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import echomskfan.gmail.com.di.core.AppComponent
import echomskfan.gmail.com.di.core.ViewModelKey
import echomskfan.gmail.com.presentation.settings.SettingsFragment
import echomskfan.gmail.com.presentation.settings.SettingsViewModel
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingsScope

@SettingsScope
@Component(
    modules = [(SettingsModule::class)],
    dependencies = [(AppComponent::class)]
)
interface SettingsComponent {
    fun inject(settingsFragment: SettingsFragment)
}

@Module
abstract class SettingsModule {

    @SettingsScope
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindViewModel(viewModel: SettingsViewModel): ViewModel
}