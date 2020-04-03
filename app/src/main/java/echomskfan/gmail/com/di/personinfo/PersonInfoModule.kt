package echomskfan.gmail.com.di.personinfo

import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.domain.interactor.personinfo.IPersonInfoInteractor
import echomskfan.gmail.com.domain.interactor.personinfo.PersonInfoInteractor
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.presentation.personinfo.PersonInfoViewModelFactory

@Module
class PersonInfoModule {

    @PersonInfoScope
    @Provides
    fun providePersonInfoIntercator(repository: IRepository): IPersonInfoInteractor {
        return PersonInfoInteractor(repository)
    }

    @PersonInfoScope
    @Provides
    fun providePersonInfoViewModelFactory(interactor: IPersonInfoInteractor): PersonInfoViewModelFactory {
        return PersonInfoViewModelFactory(interactor)
    }
}