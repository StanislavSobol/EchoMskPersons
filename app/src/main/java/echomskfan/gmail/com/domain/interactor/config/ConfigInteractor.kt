package echomskfan.gmail.com.domain.interactor.config

import echomskfan.gmail.com.domain.repository.IConfigRepository

class ConfigInteractor(private val configRepository: IConfigRepository) : IConfigInteractor {

    override val isDebugPanelEnabled: Boolean
        get() = configRepository.isDebugPanelEnabled

    override val isDisclaimerEnabled: Boolean
        get() = configRepository.isDisclaimerEnabled

    override val showOnlineStateDelayMSec: Long
        get() = configRepository.showOnlineStateDelayMSec
}