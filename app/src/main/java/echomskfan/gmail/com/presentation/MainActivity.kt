package echomskfan.gmail.com.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import echomskfan.gmail.com.*
import echomskfan.gmail.com.utils.bundleOf
import echomskfan.gmail.com.utils.logInfo

class MainActivity : AppCompatActivity(), IMainActivityRouter {

    var favMenuItemClickListener: IFavMenuItemClickListener? = null

    var favMenuItemVisible: Boolean = false
        set(value) {
            field = value
            if (!value) {
                favMenuItemClickListener = null
            }
            invalidateOptionsMenu()
        }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu?.findItem(R.id.mainMenuFav)?.isVisible = favMenuItemVisible
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                true
            }

            R.id.mainMenuFav -> {
                favMenuItemClickListener?.onFavMenuItemClick(true)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun navigateToCastsFromPersons(personId: Int) {
        navController.navigate(R.id.action_personsFragment_to_castsFragment, bundleOf(EXTRA_PERSON_ID to personId))
    }

    override fun navigateToPlayerFromCasts(castId: String) {
        navController.navigate(R.id.action_castsFragment_to_playerFragment, bundleOf(EXTRA_CAST_ID to castId))
    }

    override fun closePlayerFragment() {
        navController.popBackStack(R.id.playerFragment, true)
    }

    override fun navigateToPlayerAndResumePlaying(castId: String) {
        closePlayerFragment()
        navController.navigate(R.id.playerFragment, bundleOf(EXTRA_CAST_ID to castId, EXTRA_PLAYER_RESUME to true))
    }

    private fun applyIntent(intent: Intent?) {
        if (intent != null) {
            val castId: String? = intent.getStringExtra(EXTRA_PLAYER_ITEM_CAST_ID)
            castId?.let { navigateToPlayerAndResumePlaying(it) }
        }
    }
}
