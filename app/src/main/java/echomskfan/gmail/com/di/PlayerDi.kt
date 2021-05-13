package echomskfan.gmail.com.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap
import echomskfan.gmail.com.presentation.player.PlayerFragment
import echomskfan.gmail.com.presentation.player.PlayerViewModel
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PlayerScope

@PlayerScope
@Component(
    modules = [(PlayerModule::class)],
    dependencies = [(AppComponent::class)]
)
interface PlayerComponent {
    fun inject(playerFragment: PlayerFragment)
}

@Module
abstract class PlayerModule {

    @PlayerScope
    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel::class)
    abstract fun bindViewModel(viewModel: PlayerViewModel): ViewModel
}