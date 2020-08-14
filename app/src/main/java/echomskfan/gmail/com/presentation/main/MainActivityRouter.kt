package echomskfan.gmail.com.presentation.main

import android.content.Context
import androidx.navigation.NavController
import echomskfan.gmail.com.*
import echomskfan.gmail.com.annotationlib.FeatureNavigator
import echomskfan.gmail.com.presentation.casts.CastsFragment
import echomskfan.gmail.com.presentation.debugpanel.DebugPanelFragment
import echomskfan.gmail.com.presentation.personinfo.PersonInfoFragment
import echomskfan.gmail.com.presentation.player.PlayerFragment
import echomskfan.gmail.com.presentation.settings.SettingsFragment
import echomskfan.gmail.com.utils.bundleOf

internal class MainActivityRouter(
    appContext: Context, private val navController: NavController
) : IMainActivityRouter {

    init {
        FeatureNavigator.appContext = appContext
        FeatureNavigator.isBuildConfigDebug = BuildConfig.DEBUG
    }

    override fun navigateToCastsFromPersons(personId: Int) {
        FeatureNavigator.navigate(CastsFragment::class.java) {
            navController.navigate(
                R.id.action_personsFragment_to_castsFragment,
                bundleOf(EXTRA_PERSON_ID to personId)
            )
        }
    }

    override fun navigateToPersonInfoFromPersons(personId: Int) {
        FeatureNavigator.navigate(PersonInfoFragment::class.java) {
            navController.navigate(
                R.id.action_personsFragment_to_personInfoFragment,
                bundleOf(EXTRA_PERSON_ID to personId)
            )
        }
    }

    override fun navigateToPlayerFromCasts(castId: String) {
        FeatureNavigator.navigate(PersonInfoFragment::class.java) {
            navController.navigate(
                R.id.action_castsFragment_to_playerFragment,
                bundleOf(EXTRA_CAST_ID to castId)
            )
        }
    }

    override fun closePlayerFragment() {
        navController.popBackStack(R.id.playerFragment, true)
    }

    override fun navigateToPlayerAndResumePlaying(castId: String) {
        closePlayerFragment()
        FeatureNavigator.navigate(PlayerFragment::class.java) {
            navController.navigate(
                R.id.playerFragment,
                bundleOf(EXTRA_CAST_ID to castId, EXTRA_PLAYER_RESUME to true)
            )
        }
    }

    override fun navigateToDebugPanel() {
        FeatureNavigator.navigate(DebugPanelFragment::class.java) {
            navController.navigate(R.id.debugPanelFragment)
        }
    }

    override fun navigateToSettings() {
        FeatureNavigator.navigate(SettingsFragment::class.java) {
            navController.navigate(R.id.action_personsFragment_to_settingsFragment)
        }
    }
}