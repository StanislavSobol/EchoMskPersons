package echomskfan.gmail.com.presentation.casts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import echomskfan.gmail.com.BuildConfig
import echomskfan.gmail.com.ConfigInjector
import echomskfan.gmail.com.annotations.configinjector.ConfigParamBoolean
import echomskfan.gmail.com.domain.interactor.casts.ICastsCoInteractor
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain
import kotlinx.coroutines.launch
import javax.inject.Inject


class CastsViewModel @Inject constructor(
    private val interactor: ICastsInteractor,
    private val coInteractor: ICastsCoInteractor
) : BaseViewModel() {

    @ConfigParamBoolean("clickOnCastToWebEnabled")
    var clickOnCastToWebEnabled = false

    var lastLoadedPageNum: Int = 0

    // TODO Why do I need this property instead of using method getCastsLiveDataForPerson with the param
    var personId: Int? = null

    private val _navigateToPlayerFragmentLiveData = MutableLiveData<OneShotEvent<String>>()
    val navigateToPlayerFragmentLiveData: LiveData<OneShotEvent<String>>
        get() = _navigateToPlayerFragmentLiveData

    private val _launchChromeTabsLiveData = MutableLiveData<OneShotEvent<String>>()
    val launchChromeTabsLiveData: LiveData<OneShotEvent<String>>
        get() = _launchChromeTabsLiveData

    init {
        ConfigInjector.inject(this)
    }

    fun loadData() {
        subscribeToTransferCastsFromWebToDb()
    }

    fun getCastsLiveDataForPerson(): LiveData<List<CastListItem>> {
        if (personId == null) {
            personIdIsNull()
        }

        return Transformations.map(interactor.getCastsLiveDataForPerson(personId!!)) { list ->
            CastListItem.from(list)
        }
    }

    fun playButtonClicked(castListItem: CastListItem) {
        _navigateToPlayerFragmentLiveData.value = OneShotEvent(castListItem.id)
    }

    fun itemIdFavClicked(castId: String) {
        if (BuildConfig.COROUTINES) {
            withProgress { coInteractor.castIdFavClicked(castId) }
        } else {
            interactor.castIdFavClicked(castId)
                .doOnError { e -> catchThrowable(e) }
                .fromIoToMain()
                .subscribe()
                .unsubscribeOnClear()
        }
    }

    fun scrolledToBottom() {
        subscribeToTransferCastsFromWebToDb()
    }

    private fun subscribeToTransferCastsFromWebToDb() {
        if (loading) {
            return
        }

        val id = personId ?: personIdIsNull()

        val pageNum = lastLoadedPageNum + 1

        if (BuildConfig.COROUTINES) {
            withProgress { coInteractor.transferCastsFromWebToDb(id, pageNum) }
        } else {
            interactor.transferCastsFromWebToDb(id, pageNum)
                .fromIoToMain()
                .withProgressOnFirstPage(pageNum)
                .subscribe({}, { catchThrowable(it) })
                .unsubscribeOnClear()
        }
    }

    private fun personIdIsNull(): Nothing {
        throw IllegalStateException("personId must be set")
    }

    fun itemClicked(castId: String) {
        if (clickOnCastToWebEnabled) {
            viewModelScope.launch {
                coInteractor.getTextUrlByCastId(castId)?.let { _launchChromeTabsLiveData.postValue(OneShotEvent(it)) }
            }
        }
    }
}
