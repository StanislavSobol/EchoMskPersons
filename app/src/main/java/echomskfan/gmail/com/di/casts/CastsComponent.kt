package echomskfan.gmail.com.di.casts

import dagger.Component
import echomskfan.gmail.com.di.AppComponent
import echomskfan.gmail.com.presentation.casts.CastsFragment

@CastsScope
@Component(
    modules = [(CastsModule::class)],
    dependencies = [(AppComponent::class)]
)
interface CastsComponent {
    fun inject(castsFragment: CastsFragment)
}