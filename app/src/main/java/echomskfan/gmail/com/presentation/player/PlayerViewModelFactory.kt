package echomskfan.gmail.com.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.player.IPlayerCoInteractor
import echomskfan.gmail.com.domain.interactor.player.IPlayerInteractor

class PlayerViewModelFactory(
    private val interactor: IPlayerInteractor,
    private val coInteractor: IPlayerCoInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerViewModel(interactor, coInteractor) as T
    }
}
