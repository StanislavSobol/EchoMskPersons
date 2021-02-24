package echomskfan.gmail.com.domain.interactor.casts

interface ICastsCoInteractor {
    suspend fun transferCastsFromWebToDb(personId: Int, pageNum: Int)
    suspend fun castIdFavClicked(castId: String)
    suspend fun getTextUrlByCastId(castId: String): String?
}