package echomskfan.gmail.com.domain.interactor.config

interface IConfigProvider {
    val isDisclaimerEnabled: Boolean
    val showOnlineStateDelayMSec: Long
    val showSplashAnimation: Boolean
    val splashDelayMSec: Long
}