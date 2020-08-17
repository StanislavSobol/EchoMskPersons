package echomskfan.gmail.com.di.settings

import dagger.Component
import echomskfan.gmail.com.di.AppComponent
import echomskfan.gmail.com.presentation.settings.SettingsFragment

@SettingsScope
@Component(
    modules = [(SettingsModule::class)],
    dependencies = [(AppComponent::class)]
)
interface SettingsComponent {
    fun inject(settingsFragment: SettingsFragment)
}