package echomskfan.gmail.com.di.player

import dagger.Module
import dagger.Provides
import echomskfan.gmail.com.domain.interactor.player.IPlayerInteractor
import echomskfan.gmail.com.domain.interactor.player.PlayerInteractor
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.presentation.player.PlayerViewModelFactory

@Module
class PlayerModule {

    @PlayerScope
    @Provides
    fun providePlayerIntercator(repository: IRepository): IPlayerInteractor {
        return PlayerInteractor(repository)
    }

    @PlayerScope
    @Provides
    fun providePlayerViewModelFactory(interactor: IPlayerInteractor): PlayerViewModelFactory {
        return PlayerViewModelFactory(interactor)
    }
}