package echomskfan.gmail.com.domain.interactor.config

interface IConfigProvider {
    val showOnlineStateDelayMSec: Long
    val showSplashAnimation: Boolean
    val splashDelayMSec: Long
}