package echomskfan.gmail.com.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.domain.interactor.player.IPlayerInteractor

class PlayerViewModelFactory(private val interactor: IPlayerInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerViewModel(interactor) as T
    }
}
