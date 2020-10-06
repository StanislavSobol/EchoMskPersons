package echomskfan.gmail.com.domain.interactor.checknew

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import echomskfan.gmail.com.MApplication
import javax.inject.Inject

/**
 * Must be public and not inner
 * https://stackoverflow.com/questions/52657196/android-work-manager-could-not-instantiate-worker
 */
@Suppress("SpellCheckingInspection")
class CheckNewWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    @Inject
    lateinit var checkNewInteractor: ICheckNewInteractor

    init {
        MApplication.getAppComponent().inject(this)
    }

    override fun doWork(): Result {
        Log.d("SSS", "CheckNewWorker doWork checkNewInteractor = $checkNewInteractor")
        checkNewInteractor.checkNewCast()
        return Result.success()
    }
}