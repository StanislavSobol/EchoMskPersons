package echomskfan.gmail.com.presentation.main

interface IMainActivityRouter {
    fun navigateToCastsFromPersons(personId: Int)
    fun navigateToPersonInfoFromPersons(personId: Int)
    fun navigateToPlayerFromCasts(castId: String)
    fun closePlayerFragment()
    fun navigateToPlayerAndResumePlaying(castId: String)
    fun navigateToDebugPanel()
    fun navigateToSettings()
}