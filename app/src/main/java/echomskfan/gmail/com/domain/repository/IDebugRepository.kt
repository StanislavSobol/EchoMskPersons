package echomskfan.gmail.com.domain.repository

interface IDebugRepository {

    fun deleteLastNevzorovCast()

    companion object {
        const val NEVZOROV_ID = 1
    }
}