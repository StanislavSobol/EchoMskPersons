package echomskfan.gmail.com.di.player

import dagger.Component
import echomskfan.gmail.com.di.AppComponent
import echomskfan.gmail.com.presentation.player.PlayerFragment

@PlayerScope
@Component(
    modules = [(PlayerModule::class)],
    dependencies = [(AppComponent::class)]
)
interface PlayerComponent {
    fun inject(playerFragment: PlayerFragment)
}