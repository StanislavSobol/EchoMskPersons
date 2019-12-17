package echomskfan.gmail.com.presentation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import echomskfan.gmail.com.EXTRA_CAST_ID
import echomskfan.gmail.com.EXTRA_PERSON_ID
import echomskfan.gmail.com.R
import echomskfan.gmail.com.utils.logInfo

class MainActivity : AppCompatActivity(), IMainActivityRouter {


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        applyIntent(intent)
        logInfo("onCreate")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        applyIntent(intent)
        logInfo("onNewIntent")
    }

    private fun applyIntent(intent: Intent?) {
        if (intent != null) {
            val castId: String? = intent.getStringExtra(PLAYER_ITEM_CAST_ID)
            castId?.let { navigateToPlayer(it) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navController.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun navigateToCastsFromPersons(personId: Int) {
        val bundle = Bundle().apply { putInt(EXTRA_PERSON_ID, personId) }
        navController.navigate(R.id.action_personsFragment_to_castsFragment, bundle)
    }

    override fun navigateToPlayerFromCasts(castId: String) {
        val bundle = Bundle().apply { putString(EXTRA_CAST_ID, castId) }
        navController.navigate(R.id.action_castsFragment_to_playerFragment, bundle)
    }

    override fun closePlayerFragment() {
        navController.popBackStack(R.id.playerFragment, true)
    }

    override fun navigateToPlayer(castId: String) {
        closePlayerFragment()
        val bundle = Bundle().apply { putString(EXTRA_CAST_ID, castId) }
        navController.navigate(R.id.playerFragment, bundle)
    }

    companion object {
        const val PLAYER_ITEM_CAST_ID = "PLAYER_ITEM_CAST_ID"
    }
}
