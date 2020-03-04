package echomskfan.gmail.com.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import echomskfan.gmail.com.*
import echomskfan.gmail.com.utils.bundleOf
import javax.inject.Inject
import javax.inject.Singleton

class MainActivity : AppCompatActivity(), IMainActivityRouter {

    @Singleton
    @Inject
    internal lateinit var viewModelFactory: MainViewModelFactory

    private var favOn = false
    private var debugPanelEnabled = false

    var favMenuItemClickListener: IFavMenuItemClickListener? = null
        set(value) {
            field = value
            viewModel.loadData()
        }

    var favMenuItemVisible: Boolean = false
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController

    init {
        MApplication.getAppComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        applyIntent(intent)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.favOnLiveDate.observe(this, Observer { favOn ->
            favMenuItemClickListener?.onFavMenuItemClick(favOn)
            this.favOn = favOn
            invalidateOptionsMenu()
        })

        viewModel.debugPanelEnabledLiveDate.observe(this, Observer {
            this.debugPanelEnabled = it
            invalidateOptionsMenu()
        })

        viewModel.navigateToCastsLiveDate.observe(this, Observer {
            it.getContentIfNotHandled()?.let { navigateToDebugPanel(); }
        })

        viewModel.loadData()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        applyIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu?.findItem(R.id.favMainMenuItem)?.let {
            it.isVisible = favMenuItemVisible
            it.setIcon(if (favOn) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border_black_24dp)
        }

        menu?.forEach {
            when (it.itemId) {
                R.id.favMainMenuItem -> {
                    it.isVisible = favMenuItemVisible
                    it.setIcon(if (favOn) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border_black_24dp)
                }
                R.id.debugPanelMainMenuItem -> {
                    it.isVisible = debugPanelEnabled
                }
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
                true
            }

            R.id.favMainMenuItem -> {
                viewModel.favMenuItemClick()
                true
            }

            R.id.debugPanelMainMenuItem -> {
                viewModel.debugPanelMenuItemClick()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun navigateToCastsFromPersons(personId: Int) {
        navController.navigate(
            R.id.action_personsFragment_to_castsFragment,
            bundleOf(EXTRA_PERSON_ID to personId)
        )
    }

    override fun navigateToPlayerFromCasts(castId: String) {
        navController.navigate(
            R.id.action_castsFragment_to_playerFragment,
            bundleOf(EXTRA_CAST_ID to castId)
        )
    }

    override fun closePlayerFragment() {
        navController.popBackStack(R.id.playerFragment, true)
    }

    override fun navigateToPlayerAndResumePlaying(castId: String) {
        closePlayerFragment()
        navController.navigate(
            R.id.playerFragment,
            bundleOf(EXTRA_CAST_ID to castId, EXTRA_PLAYER_RESUME to true)
        )
    }

    override fun navigateToDebugPanel() {
        navController.navigate(R.id.debugPanelFragment)
        Log.d("SSS", "navigateToDebugPanel")
    }

    private fun applyIntent(intent: Intent?) {
        if (intent != null) {
            val castId: String? = intent.getStringExtra(EXTRA_PLAYER_ITEM_CAST_ID)
            castId?.let { navigateToPlayerAndResumePlaying(it) }
        }
    }
}
