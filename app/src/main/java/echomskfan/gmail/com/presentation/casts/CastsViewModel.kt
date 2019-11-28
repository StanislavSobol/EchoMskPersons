package echomskfan.gmail.com.presentation.casts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import echomskfan.gmail.com.domain.interactor.casts.ICastsInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.presentation.OneShotEvent
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain

// TODO Move all personId from the fragment over here
class CastsViewModel(private val interactor: ICastsInteractor) : BaseViewModel() {

    private var loading = false
    private var hasMoreItems = true
    private var currentPageNum = 1
    private val _startPlayLiveData = MutableLiveData<OneShotEvent<String>>()

    var personId: Int? = null

    val startPlayLiveData: LiveData<OneShotEvent<String>>
        get() = _startPlayLiveData

    fun getCastsLiveDataForPerson(): LiveData<List<CastListItem>> {
        if (personId == null) {
            personIdIsNull()
            return MutableLiveData<List<CastListItem>>()
        }
        return Transformations.map(interactor.getCastsLiveDataForPerson(personId!!)) { list -> CastListItem.from(list) }
    }

    fun firstAttach() {
//        interactor.tranferCastsFromWebToDb(personId, currentPageNum)          .fromIoToMain()
//            .doOnError { e -> catchThrowable(e) }
//            .subscribe()
//            .unsubscribeOnClear()
        subscribeToTransferCastsFromWebToDb()
    }

    fun playButtonClicked(castListItem: CastListItem) {
        _startPlayLiveData.value = OneShotEvent(castListItem.id)
    }

    fun itemIdFavClicked(castId: String) {
        interactor.castIdFavClicked(castId)
            .doOnError { e -> catchThrowable(e) }
            .fromIoToMain()
            .subscribe()
            .unsubscribeOnClear()
    }

    fun scrolledToBottom() {
//        if (personId == null) {
//            personIdIsNull()
//            return
//        }

        subscribeToTransferCastsFromWebToDb()


//        personId?.let { interactor.tranferCastsFromWebToDb(it, ++currentPageNum) }?: personIdIsNull()
    }

    private fun subscribeToTransferCastsFromWebToDb() {
        if (loading) {
            return
        }

        if (personId == null) {
            personIdIsNull()
            return
        }

        interactor.tranferCastsFromWebToDb(personId!!, currentPageNum)
            .fromIoToMain()
            .doOnSubscribe { loading = true } // TODO to extension
            .subscribe({
                loading = false
                currentPageNum++
            }, {
                loading = false
                catchThrowable(it)
            })
            .unsubscribeOnClear()
    }

    private fun personIdIsNull() {
        throw IllegalStateException("personId must be set")
    }
}
