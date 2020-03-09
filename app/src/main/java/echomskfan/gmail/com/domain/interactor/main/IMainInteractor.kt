package echomskfan.gmail.com.domain.interactor.main

interface IMainInteractor {
    var isFavOn: Boolean
    val isDebugPanelEnabled: Boolean
    val isDisclaimerEnabled: Boolean
    val showOnlineStateDelayMSec: Long
}