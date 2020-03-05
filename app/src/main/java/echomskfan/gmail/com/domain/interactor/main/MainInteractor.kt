package echomskfan.gmail.com.domain.interactor.main

import echomskfan.gmail.com.domain.repository.IConfigRepository
import echomskfan.gmail.com.domain.repository.IRepository

class MainInteractor(
    private val repository: IRepository,
    private val configRepository: IConfigRepository
) : IMainInteractor {

    override var isFavOn: Boolean
        get() = repository.isFavOn
        set(value) {
            repository.isFavOn = value
        }

    override val isDebugPanelEnabled: Boolean
        get() = configRepository.isDebugPanelEnabled

    override val isDisclaimerEnabled: Boolean
        get() = configRepository.isDisclaimerEnabled
}