package echomskfan.gmail.com.presentation.casts

import androidx.annotation.VisibleForTesting
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
    private val coInteractor: ICastsCoInteractor,
    private val personId: Int
) : BaseViewModel() {

    @ConfigParamBoolean("clickOnCastToWebEnabled")
    var clickOnCastToWebEnabled = false

    var lastLoadedPageNum: Int = 0

    private val _navigateToPlayerFragmentLiveData = MutableLiveData<OneShotEvent<String>>()
    val navigateToPlayerFragmentLiveData: LiveData<OneShotEvent<String>>
        get() = _navigateToPlayerFragmentLiveData

    private val _launchChromeTabsLiveData = MutableLiveData<OneShotEvent<String>>()
    val launchChromeTabsLiveData: LiveData<OneShotEvent<String>>
        get() = _launchChromeTabsLiveData

    init {
        try {
            ConfigInjector.inject(this)
        } catch (ignoreForTests: Exception) {

        }
    }

    fun loadData() {
        subscribeToTransferCastsFromWebToDb()
    }

    fun getCastsLiveDataForPerson(): LiveData<List<CastListItem>> {
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

    fun itemClicked(castId: String) {
        if (clickOnCastToWebEnabled) {
            viewModelScope.launch {
                coInteractor.getTextUrlByCastId(castId)?.let { _launchChromeTabsLiveData.postValue(OneShotEvent(it)) }
            }
        }
    }

    @VisibleForTesting
    internal fun subscribeToTransferCastsFromWebToDb() {
        if (loading) {
            return
        }

        val pageNum = lastLoadedPageNum + 1

        if (BuildConfig.COROUTINES) {
            withProgress { coInteractor.transferCastsFromWebToDb(personId, pageNum) }
        } else {
            interactor.transferCastsFromWebToDb(personId, pageNum)
                .fromIoToMain()
                .withProgressOnFirstPage(pageNum)
                .subscribe({}, { catchThrowable(it) })
                .unsubscribeOnClear()
        }
    }
}
