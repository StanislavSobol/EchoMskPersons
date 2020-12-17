package echomskfan.gmail.com.di.casts

import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.domain.interactor.casts.CastsCoInteractor
import echomskfan.gmail.com.domain.interactor.casts.CastsInteractor
import echomskfan.gmail.com.domain.interactor.casts.ICastsCoInteractor
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.presentation.casts.CastsViewModelFactory

@Module
class CastsModule {

    @CastsScope
    @Provides
    fun provideCastsIntercator(repository: IRepository): ICastsInteractor {
        return CastsInteractor(repository)
    }

    @CastsScope
    @Provides
    fun provideCastsCoIntercator(repository: IRepository): ICastsCoInteractor {
        return CastsCoInteractor(repository)
    }

    @CastsScope
    @Provides
    fun providePersonsViewModelFactory(interactor: ICastsInteractor,
                                       coInteractor: ICastsCoInteractor): CastsViewModelFactory {
        return CastsViewModelFactory(interactor, coInteractor)
    }
}