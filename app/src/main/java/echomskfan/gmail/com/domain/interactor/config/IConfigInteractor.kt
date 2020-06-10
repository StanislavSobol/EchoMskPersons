package echomskfan.gmail.com.domain.interactor.config

interface IConfigInteractor {
    val isDebugPanelEnabled: Boolean
    val isDisclaimerEnabled: Boolean
    val showOnlineStateDelayMSec: Long
}