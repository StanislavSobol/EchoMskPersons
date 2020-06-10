package echomskfan.gmail.com.domain.repository

interface IConfigRepository {
    val isDebugPanelEnabled: Boolean
    val isDisclaimerEnabled: Boolean
    val showOnlineStateDelayMSec: Long
    val showSplashAnimation: Boolean
    val splashDelayMSec: Long
}