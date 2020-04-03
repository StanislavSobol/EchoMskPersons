package echomskfan.gmail.com.presentation.casts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain

class CastsViewModel(private val interactor: ICastsInteractor) : BaseViewModel() {

    var lastLoadedPageNum: Int = 0
    // TODO Why do I need this property insted of useing method getCastsLiveDataForPerson with the param
    var personId: Int? = null

    val navigateToPlayerFragmentLiveData: LiveData<OneShotEvent<String>>
        get() = _navigateToPlayerFragmentLiveData


    private val _navigateToPlayerFragmentLiveData = MutableLiveData<OneShotEvent<String>>()

    fun getCastsLiveDataForPerson(): LiveData<List<CastListItem>> {
        if (personId == null) {
            personIdIsNull()
        }

        return Transformations.map(interactor.getCastsLiveDataForPerson(personId!!)) { list -> CastListItem.from(list) }
    }

    fun loadData() {
        subscribeToTransferCastsFromWebToDb()
    }

    fun playButtonClicked(castListItem: CastListItem) {
        _navigateToPlayerFragmentLiveData.value = OneShotEvent(castListItem.id)
    }

    fun itemIdFavClicked(castId: String) {
        interactor.castIdFavClicked(castId)
            .doOnError { e -> catchThrowable(e) }
            .fromIoToMain()
            .subscribe()
            .unsubscribeOnClear()
    }

    fun scrolledToBottom() {
        subscribeToTransferCastsFromWebToDb()
    }

    private fun subscribeToTransferCastsFromWebToDb() {
        if (loading) {
            return
        }

        if (personId == null) {
            personIdIsNull()
        }

        val pageNum = lastLoadedPageNum + 1

        interactor.transferCastsFromWebToDb(personId!!, pageNum)
            .fromIoToMain()
            .withProgressOnFirstPage(pageNum)
            .subscribe({}, { catchThrowable(it) })
            .unsubscribeOnClear()
    }

    private fun personIdIsNull(): Nothing {
        throw IllegalStateException("personId must be set")
    }
}
