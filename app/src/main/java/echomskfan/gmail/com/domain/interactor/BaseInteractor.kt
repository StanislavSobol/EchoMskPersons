package echomskfan.gmail.com.domain.interactor

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseInteractor : IBaseInteractor {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun clear() {
        compositeDisposable
    }

    protected fun Disposable.unsubscribeOnClear() {
        compositeDisposable.add(this)
    }
}