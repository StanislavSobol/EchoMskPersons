package echomskfan.gmail.com.di.persons

import dagger.Component
import echomskfan.gmail.com.di.AppComponent
import echomskfan.gmail.com.presentation.persons.PersonsFragment

@PersonsScope
@Component(
    modules = [(PersonsModule::class)],
    dependencies = [(AppComponent::class)]
)
interface PersonsComponent {
    fun inject(personsFragment: PersonsFragment)
}