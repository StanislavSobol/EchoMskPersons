package echomskfan.gmail.com.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import echomskfan.gmail.com.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun navigateToCasts(personId: Int) {
        val bundle = Bundle()
        bundle.putInt("personId", personId)
        navController.navigate(R.id.action_personsFragment_to_castsFragment, bundle)
    }

    fun navigateToPlayer(castId: String) {
        val bundle = Bundle()
        bundle.putString("date", castId)
        navController.navigate(R.id.action_castsFragment_to_playerFragment, bundle)
    }
}
