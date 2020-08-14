package echomskfan.gmail.com.di.settings

import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.presentation.settings.SettingsViewModelFactory

@Module
class SettingsModule {
//
//    @SettingsScope
//    @Provides
//    fun provideDebugPanelRepository(db: PersonsDatabase): IDebugRepository {
//        return DebugRepository(db)
//    }
//
//    @SettingsScope
//    @Provides
//    fun provideDebugPanelIntercator(debugRepository: IDebugRepository): IDebugPanelInteractor {
//        return DebugPanelInteractor(debugRepository)
//    }

    @SettingsScope
    @Provides
    fun providePersonsViewModelFactory(): SettingsViewModelFactory {
        return SettingsViewModelFactory()
    }
}