package echomskfan.gmail.com.domain.repository

@Deprecated("Use KAPT generated ConfigManager")
interface IConfigRepository {
    val isDebugPanelEnabled: Boolean
    val isDisclaimerEnabled: Boolean
    val showOnlineStateDelayMSec: Long
    val showSplashAnimation: Boolean
    val splashDelayMSec: Long
}