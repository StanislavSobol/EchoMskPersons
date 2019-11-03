package echomskfan.gmail.com.di.casts

import dagger.Component
import echomskfan.gmail.com.di.AppComponent

@CastsScope
@Component(
    modules = [(CastsModule::class)],
    dependencies = [(AppComponent::class)]
)
interface CastsComponent {
}