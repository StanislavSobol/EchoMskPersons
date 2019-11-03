package echomskfan.gmail.com.domain.interactor

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseInteractor {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    fun clear() {
        compositeDisposable
    }

    protected fun Disposable.unsubscribeOnClear() {
        compositeDisposable.add(this)
    }
}