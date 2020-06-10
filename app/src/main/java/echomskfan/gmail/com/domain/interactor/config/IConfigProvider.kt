package echomskfan.gmail.com.domain.interactor.config

interface IConfigProvider {
    val isDebugPanelEnabled: Boolean
    val isDisclaimerEnabled: Boolean
    val showOnlineStateDelayMSec: Long
    val showSplashAnimation: Boolean
    val splashDelayMSec: Long
}