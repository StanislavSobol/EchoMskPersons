package echomskfan.gmail.com.di.personinfo

import dagger.Component
import echomskfan.gmail.com.di.AppComponent
import echomskfan.gmail.com.presentation.personinfo.PersonInfoFragment

@PersonInfoScope
@Component(
    modules = [(PersonInfoModule::class)],
    dependencies = [(AppComponent::class)]
)
interface PersonInfoComponent {
    fun inject(personInfoFragment: PersonInfoFragment)
}