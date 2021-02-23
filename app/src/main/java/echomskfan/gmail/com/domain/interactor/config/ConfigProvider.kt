package echomskfan.gmail.com.domain.interactor.config

import echomskfan.gmail.com.domain.repository.IConfigRepository

class ConfigProvider(private val configRepository: IConfigRepository) : IConfigProvider {

    override val showOnlineStateDelayMSec: Long
        get() = configRepository.showOnlineStateDelayMSec

    override val showSplashAnimation: Boolean
        get() = configRepository.showSplashAnimation

    override val splashDelayMSec: Long
        get() = configRepository.splashDelayMSec
}