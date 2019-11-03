package echomskfan.gmail.com.di.persons

import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.domain.interactor.persons.IPersonsInteractor
import echomskfan.gmail.com.domain.interactor.persons.PersonsInteractor
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.presentation.persons.PersonsViewModelFactory

@Module
class PersonsModule {

    @PersonsScope
    @Provides
    fun providePersonsIntercator(repository: IRepository): IPersonsInteractor {
        return PersonsInteractor(repository)
    }

    @PersonsScope
    @Provides
    fun providePersonsViewModelFactory(interactor: IPersonsInteractor): PersonsViewModelFactory {
        return PersonsViewModelFactory(interactor)
    }
}