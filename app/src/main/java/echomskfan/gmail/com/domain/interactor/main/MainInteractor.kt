package echomskfan.gmail.com.domain.interactor.main

import echomskfan.gmail.com.domain.repository.IRepository

class MainInteractor(private val repository: IRepository) : IMainInteractor {

    override var isFavOn: Boolean
        get() = repository.isFavOn
        set(value) {
            repository.isFavOn = value
        }
}