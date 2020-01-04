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
    var personId: Int? = null

    val navigateToPlayerFragmentLiveData: LiveData<OneShotEvent<String>>
        get() = _navigateToPlayerFragmentLiveData
    val showProgressLiveData: LiveData<Boolean>
        get() = _showProgressLiveData

    private val _navigateToPlayerFragmentLiveData = MutableLiveData<OneShotEvent<String>>()
    private val _showProgressLiveData = MutableLiveData<Boolean>()

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

    override fun showProgress() {
        _showProgressLiveData.value = true
    }

    override fun hideProgress() {
        _showProgressLiveData.value = false
    }

    private fun subscribeToTransferCastsFromWebToDb() {
        if (loading) {
            return
        }

        if (personId == null) {
            personIdIsNull()
        }

        interactor.transferCastsFromWebToDb(personId!!, lastLoadedPageNum + 1)
            .fromIoToMain()
            .withProgress()
            .subscribe({}, { catchThrowable(it) })
            .unsubscribeOnClear()
    }

    private fun personIdIsNull(): Nothing {
        throw IllegalStateException("personId must be set")
    }
}
