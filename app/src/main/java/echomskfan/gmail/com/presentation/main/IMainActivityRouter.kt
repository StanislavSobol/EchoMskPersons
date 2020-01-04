package echomskfan.gmail.com.presentation.main

interface IMainActivityRouter {
    fun navigateToCastsFromPersons(personId: Int)
    fun navigateToPlayerFromCasts(castId: String)
    fun closePlayerFragment()
    fun navigateToPlayerAndResumePlaying(castId: String)
}