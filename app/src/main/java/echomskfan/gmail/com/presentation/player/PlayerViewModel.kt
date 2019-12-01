package echomskfan.gmail.com.presentation.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import echomskfan.gmail.com.domain.interactor.player.IPlayerInteractor
import echomskfan.gmail.com.presentation.BaseViewModel
import echomskfan.gmail.com.utils.catchThrowable
import echomskfan.gmail.com.utils.fromIoToMain

class PlayerViewModel(private val interactor: IPlayerInteractor) : BaseViewModel() {

    val playerItemLiveData: LiveData<PlayerItem>
        get() = _playerItemLiveData

    private val _playerItemLiveData = MutableLiveData<PlayerItem>()

/*
    fun getPlayerLiveDataForCast(castId: Int?): LiveData<List<PlayerItem>> {
        if (castId == null) {
            castIdIsNull()
        }

//        val playerItemSingle: Single<PlayerItem> = interactor.getPlayerItemSingle(castId)

        interactor.getPlayerItemSingle(castId)
            .fromIoToMain()
            .subscribe(
                {
                    _playerItemLiveData.value = it
                },
                {
                    catchThrowable(it)
                }
            )
            .unsubscribeOnClear()

//        val singleCas: Single<CastEntity> = interactor.singleCas(castId)
//
//        val singlePe: Single<PersonEntity> = interactor.singlePe(castId)
//
//        var castEntity: CastEntity? = null
//
//
//        singleCas.fromIoToMain()
//            .subscribe(
//                {
//                    castEntity = it
//                },
//                {
//                    catchThrowable(it)
//                }
//            ).unsubscribeOnClear()


        //  val personEntity: PersonEntity = interactor.getPerson


        // return interactor.getPlayerLiveDataForCast(castId)

//        if (castId == null) {
//            castIdIsNull()
//        }
//
//        return Transformations.map(interactor.getPlayerLiveDataForCast(castId)) { list -> PlayerItem.from(list) }
    }
    */


    fun firstAttach(castId: String?) {
        if (castId == null) {
            castIdIsNull()
        }

//        val playerItemSingle: Single<PlayerItem> = interactor.getPlayerItemSingle(castId)

        interactor.getPlayerItemSingle(castId)
            .fromIoToMain()
            .subscribe(
                {
                    _playerItemLiveData.value = it
                },
                {
                    catchThrowable(it)
                }
            )
            .unsubscribeOnClear()
    }


    private fun castIdIsNull(): Nothing {
        throw IllegalStateException("castId must be set")
    }
}
