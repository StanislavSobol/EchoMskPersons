package echomskfan.gmail.com.presentation.main

import androidx.navigation.NavController
import echomskfan.gmail.com.EXTRA_CAST_ID
import echomskfan.gmail.com.EXTRA_PERSON_ID
import echomskfan.gmail.com.EXTRA_PLAYER_RESUME
import echomskfan.gmail.com.R

internal class MainActivityRouter(private val navController: NavController) : IMainActivityRouter {

    override fun navigateToCastsFromPersons(personId: Int) {
        navController.navigate(
            R.id.action_personsFragment_to_castsFragment,
            com.example.corelib.bundleOf(EXTRA_PERSON_ID to personId)
        )
    }

    override fun navigateToPersonInfoFromPersons(personId: Int) {
        navController.navigate(
            R.id.action_personsFragment_to_personInfoFragment,
            com.example.corelib.bundleOf(EXTRA_PERSON_ID to personId)
        )
    }

    override fun navigateToPlayerFromCasts(castId: String) {
        navController.navigate(
            R.id.action_castsFragment_to_playerFragment,
            com.example.corelib.bundleOf(EXTRA_CAST_ID to castId)
        )
    }

    override fun closePlayerFragment() {
        navController.popBackStack(R.id.playerFragment, true)
    }

    override fun navigateToPlayerAndResumePlaying(castId: String) {
        closePlayerFragment()
        navController.navigate(
            R.id.playerFragment,
            com.example.corelib.bundleOf(
                EXTRA_CAST_ID to castId,
                EXTRA_PLAYER_RESUME to true
            )
        )
    }

    override fun navigateToDebugPanel() {
        navController.navigate(R.id.debugPanelFragment)
    }

    override fun navigateToSettings() {
        navController.navigate(R.id.settingsFragment)
    }
}