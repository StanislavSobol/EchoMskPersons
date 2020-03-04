package echomskfan.gmail.com.di.debugpanel

import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.data.db.PersonsDatabase
import echomskfan.gmail.com.domain.interactor.debugpanel.DebugPanelInteractor
import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelInteractor
import echomskfan.gmail.com.domain.repository.DebugPanelRepository
import echomskfan.gmail.com.domain.repository.IDebugPanelRepository
import echomskfan.gmail.com.presentation.debugpanel.DebugPanelViewModelFactory

@Module
class DebugPanelModule {

    @DebugPanelScope
    @Provides
    fun provideDebugPanelRepository(db: PersonsDatabase): IDebugPanelRepository {
        return DebugPanelRepository(db)
    }

    @DebugPanelScope
    @Provides
    fun provideDebugPanelIntercator(debugPanelRepository: IDebugPanelRepository): IDebugPanelInteractor {
        return DebugPanelInteractor(debugPanelRepository)
    }

    @DebugPanelScope
    @Provides
    fun providePersonsViewModelFactory(interactor: IDebugPanelInteractor): DebugPanelViewModelFactory {
        return DebugPanelViewModelFactory(interactor)
    }
}