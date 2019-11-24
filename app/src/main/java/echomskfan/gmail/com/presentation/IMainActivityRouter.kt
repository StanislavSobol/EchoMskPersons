package echomskfan.gmail.com.presentation

interface IMainActivityRouter {
    fun navigateToCasts(personId: Int)
    fun navigateToPlayer(castId: String)
}