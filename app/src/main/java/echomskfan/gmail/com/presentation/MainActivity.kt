package echomskfan.gmail.com.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import echomskfan.gmail.com.R
import echomskfan.gmail.com.presentation.player.MediaPlayerService
import echomskfan.gmail.com.presentation.player.PlayerItem

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun navigateToCasts(castId: Int) {
        val bundle = Bundle()
        bundle.putInt("personId", castId)
        navController.navigate(R.id.action_personsFragment_to_castsFragment, bundle)
    }

    fun startPlay(playerItem: PlayerItem) {
        val intent = Intent(this, MediaPlayerService::class.java)
        intent.putExtra(PlayerItem.EXTRA_KEY, playerItem)
        startService(intent)
    }
}
