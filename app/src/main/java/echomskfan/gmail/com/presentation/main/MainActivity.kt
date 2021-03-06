package echomskfan.gmail.com.presentation.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.corelib.gone
import com.example.corelib.visible
import echomskfan.gmail.com.*
import echomskfan.gmail.com.annotations.configinjector.ConfigParamBoolean
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Singleton

class MainActivity : AppCompatActivity() {

    var favMenuItemClickListener: IFavMenuItemClickListener? = null
        set(value) {
            field = value
            viewModel.loadMenuData()
        }

    var favMenuItemVisible: Boolean = false
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    var settingsMenuItemVisible: Boolean = false
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    @ConfigParamBoolean("debugPanelEnabledForDebugBuild")
    var debugPanelEnabledForDebugBuild = false

    val mainActivityRouter: IMainActivityRouter
        get() = router

    @Singleton
    @Inject
    internal lateinit var viewModelFactory: MainViewModelFactory

    private var favOn = false
    private var connectivityBroadcastReceiver: BroadcastReceiver? = null
    private var showOnlineStateDelayMSec = 0L

    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    private lateinit var router: MainActivityRouter

    init {
        MApplication.getAppComponent().inject(this)
        ConfigInjector.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        router = MainActivityRouter(navController)

        applyIntent(intent)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.favOnLiveDate.observe(this, Observer { favOn ->
            favMenuItemClickListener?.onFavMenuItemClick(favOn)
            this.favOn = favOn
            invalidateOptionsMenu()
        })

        viewModel.navigateToSettingsLiveDate.observe(this, Observer {
            it.getContentIfNotHandled()?.let { router.navigateToSettings(); }
        })

        viewModel.navigateToDebugPanelLiveDate.observe(this, Observer {
            it.getContentIfNotHandled()?.let { router.navigateToDebugPanel(); }
        })

        viewModel.disclaimerEnabledLiveDate.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    showDisclaimer()
                }
            }
        })

        viewModel.goesOnlineLiveDate.observe(this, Observer {
            it.getContentIfNotHandled()?.let { online -> showConnectivityState(online) }
        })

        viewModel.showOnlineStateDelayMSecLiveData.observe(this, Observer {
            it.getContentIfNotHandled()?.let { delay -> showOnlineStateDelayMSec = delay }
        })
    }

    override fun onStart() {
        super.onStart()

        connectivityBroadcastReceiver = ConnectivityStateBroadcastReceiver()

        registerReceiver(connectivityBroadcastReceiver,
            IntentFilter().apply {
                addAction(CONNECTIVITY_CHANGE_ACTION)
                priority = 100
            })
    }

    override fun onStop() {
        connectivityBroadcastReceiver?.let { unregisterReceiver(it) }
        super.onStop()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        applyIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        menu?.forEach {
            when (it.itemId) {
                R.id.favMainMenuItem -> {
                    it.isVisible = favMenuItemVisible
                    it.setIcon(if (favOn) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border_black_24dp)
                }
                R.id.settingsMainMenuItem -> {
                    it.isVisible = settingsMenuItemVisible
                }
                R.id.debugPanelMainMenuItem -> {
                    it.isVisible = BuildConfig.DEBUG && debugPanelEnabledForDebugBuild
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
                viewModel.favMenuItemClicked()
                true
            }
            R.id.settingsMainMenuItem -> {
                viewModel.settingsMenuItemClicked()
                true
            }
            R.id.debugPanelMainMenuItem -> {
                viewModel.debugPanelMenuItemClicked()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun applyIntent(intent: Intent?) {
        intent?.getStringExtra(EXTRA_PLAYER_ITEM_CAST_ID)?.let {
            router.navigateToPlayerAndResumePlaying(it)
        }
    }

    private fun showDisclaimer() {
        AlertDialog.Builder(this).create().let {
            it.setTitle(R.string.disclaimer_title)
            it.setMessage(getString(R.string.disclaimer_text))
            it.setButton(
                AlertDialog.BUTTON_NEUTRAL,
                getString(android.R.string.ok)
            ) { dialog, _ -> dialog.dismiss() }
            it.show()
        }
    }

    private inner class ConnectivityStateBroadcastReceiver : BroadcastReceiver() {

        private var firstReceiving = true

        override fun onReceive(context: Context?, intent: Intent?) {
            if (firstReceiving) {
                firstReceiving = false
            } else {
                val cm =
                    context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                viewModel.connectivityStateChanged(cm?.isActiveNetworkMetered ?: false)
            }
        }
    }

    private fun showConnectivityState(online: Boolean) {
        connectivityStatusTextView.visible()
        connectivityStatusTextView.setText(if (online) R.string.online else R.string.offline)
        val color = resources.getColor(if (online) R.color.onlineGreen else R.color.offlineRed)
        connectivityStatusTextView.setBackgroundColor(color)
        if (online && showOnlineStateDelayMSec > 0) {
            Handler().postDelayed({ connectivityStatusTextView.gone() }, showOnlineStateDelayMSec)
        }
    }

    companion object {
        private const val CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    }
}
