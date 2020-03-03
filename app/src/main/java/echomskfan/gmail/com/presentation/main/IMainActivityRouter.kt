package echomskfan.gmail.com.presentation.main

import android.view.View

interface IMainActivityRouter {
    fun navigateToCastsFromPersons(personId: Int, transitionView: View)
    fun navigateToPlayerFromCasts(castId: String)
    fun closePlayerFragment()
    fun navigateToPlayerAndResumePlaying(castId: String)
}