package echomskfan.gmail.com.di.debugpanel

import dagger.Component
import echomskfan.gmail.com.di.AppComponent
import echomskfan.gmail.com.presentation.debugpanel.DebugPanelFragment

@DebugPanelScope
@Component(
    modules = [(DebugPanelModule::class)],
    dependencies = [(AppComponent::class)]
)
interface DebugPanelComponent {
    fun inject(debugPanelFragment: DebugPanelFragment)
}