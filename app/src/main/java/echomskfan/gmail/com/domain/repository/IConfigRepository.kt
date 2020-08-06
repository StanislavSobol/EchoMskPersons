package echomskfan.gmail.com.domain.repository

/**
 * All feature toggles must be provided via FeatureNavigator using KAPT
 */
interface IConfigRepository {
    val isDisclaimerEnabled: Boolean
    val showOnlineStateDelayMSec: Long
    val showSplashAnimation: Boolean
    val splashDelayMSec: Long
}