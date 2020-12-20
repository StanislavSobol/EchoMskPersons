package echomskfan.gmail.com.di.player

import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.domain.interactor.player.IPlayerCoInteractor
import echomskfan.gmail.com.domain.interactor.player.IPlayerInteractor
import echomskfan.gmail.com.presentation.player.PlayerViewModelFactory

@Module
class PlayerModule {
    @PlayerScope
    @Provides
    fun providePlayerViewModelFactory(
        interactor: IPlayerInteractor,
        coInteractor: IPlayerCoInteractor
    ): PlayerViewModelFactory {
        return PlayerViewModelFactory(interactor, coInteractor)
    }
}