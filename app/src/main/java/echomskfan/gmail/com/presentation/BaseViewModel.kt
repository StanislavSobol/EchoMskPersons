package echomskfan.gmail.com.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected var loading = false
        private set

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun Disposable.unsubscribeOnClear() {
        compositeDisposable.add(this)
    }

    protected fun Completable.withProgress(): Completable {
        return doOnSubscribe { showProgress() }
            .doOnComplete { hideProgress() }
            .doOnError { hideProgress() }
    }

    open protected fun showProgress() {
        loading = true
    }

    open protected fun hideProgress() {
        loading = false
    }
}