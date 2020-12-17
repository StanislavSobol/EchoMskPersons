package echomskfan.gmail.com.di.persons

import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.domain.interactor.persons.IPersonsCoInteractor
import echomskfan.gmail.com.domain.interactor.persons.IPersonsInteractor
import echomskfan.gmail.com.domain.interactor.persons.PersonsCoInteractor
import echomskfan.gmail.com.domain.interactor.persons.PersonsInteractor
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.presentation.persons.PersonsViewModelFactory

@Module
class PersonsModule {

    @PersonsScope
    @Provides
    fun providePersonsInteractor(repository: IRepository): IPersonsInteractor {
        return PersonsInteractor(repository)
    }

    @PersonsScope
    @Provides
    fun providePersonsCoInteractor(repository: IRepository): IPersonsCoInteractor {
        return PersonsCoInteractor(repository)
    }

    @PersonsScope
    @Provides
    fun providePersonsViewModelFactory(interactor: IPersonsInteractor,
                                       coInteractor: IPersonsCoInteractor): PersonsViewModelFactory {
        return PersonsViewModelFactory(interactor, coInteractor)
    }
}