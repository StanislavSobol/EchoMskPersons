package echomskfan.gmail.com.di.debugpanel

import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.data.db.PersonsDatabase
import echomskfan.gmail.com.domain.interactor.checknew.ICheckNewInteractor
import echomskfan.gmail.com.domain.interactor.debugpanel.DebugPanelInteractor
import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelInteractor
import echomskfan.gmail.com.domain.repository.DebugRepository
import echomskfan.gmail.com.domain.repository.IDebugRepository
import echomskfan.gmail.com.presentation.debugpanel.DebugPanelViewModelFactory

@Module
class DebugPanelModule {

    @DebugPanelScope
    @Provides
    fun provideDebugPanelRepository(db: PersonsDatabase): IDebugRepository {
        return DebugRepository(db)
    }

    @DebugPanelScope
    @Provides
    fun provideDebugPanelIntercator(debugRepository: IDebugRepository): IDebugPanelInteractor {
        return DebugPanelInteractor(debugRepository)
    }

    @DebugPanelScope
    @Provides
    fun providePersonsViewModelFactory(
        debugPanelInteractor: IDebugPanelInteractor,
        checkNewInteractor: ICheckNewInteractor
    ): DebugPanelViewModelFactory {
        return DebugPanelViewModelFactory(debugPanelInteractor, checkNewInteractor)
    }
}