package echomskfan.gmail.com.presentation.main

import android.view.View

interface IMainActivityRouter {
    fun navigateToCastsFromPersons(personId: Int)
    fun navigateToPersonInfoFromPersons(personId: Int, sharedView: View?)
    fun navigateToPlayerFromCasts(castId: String)
    fun closePlayerFragment()
    fun navigateToPlayerAndResumePlaying(castId: String)
    fun navigateToDebugPanel()
}