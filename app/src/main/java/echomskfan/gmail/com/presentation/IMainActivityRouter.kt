package echomskfan.gmail.com.presentation

interface IMainActivityRouter {
    fun navigateToCastsFromPersons(personId: Int)
    fun navigateToPlayerFromCasts(castId: String)
    fun closePlayerFragment()
    fun navigateToPlayer(castId: String)

}