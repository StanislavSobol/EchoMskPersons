package echomskfan.gmail.com.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import echomskfan.gmail.com.utils.catchThrowable
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val showProgressLiveData: LiveData<Boolean>
        get() = _showProgressLiveData

    private val _showProgressLiveData = MutableLiveData<Boolean>()

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

    protected fun Completable.withProgressOnFirstPage(pageNum: Int): Completable {
        return doOnSubscribe { if (pageNum == 1) showProgress() }
            .doOnComplete { hideProgress() }
            .doOnError { hideProgress() }
    }

    protected open fun showProgress() {
        loading = true
        _showProgressLiveData.postValue(true)
    }

    protected open fun hideProgress() {
        loading = false
        _showProgressLiveData.postValue(false)
    }


    protected fun withProgress(function: () -> Job) {
        showProgress()
        try {
            viewModelScope.launch { function.invoke() }
        } catch (e: Exception) {
            catchThrowable(e)
        } finally {
            hideProgress()
        }
    }

}