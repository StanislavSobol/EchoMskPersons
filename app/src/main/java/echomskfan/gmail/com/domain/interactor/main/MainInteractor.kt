package echomskfan.gmail.com.domain.interactor.main

import android.content.Context
import android.util.Log
import androidx.work.*
import echomskfan.gmail.com.domain.repository.IRepository
import java.util.concurrent.TimeUnit

class MainInteractor(private val appContext: Context, private val repository: IRepository) :
    IMainInteractor {

    override var isFavOn: Boolean
        get() = repository.isFavOn
        set(value) {
            repository.isFavOn = value
        }

//    override fun setupWorkManager(): Completable {
//        return Completable.create { emitter ->
//            repository.getNotifiedPersonsSingle().fromIoToMain().subscribe({
//                processWorkManager(it)
//            }, {
//                catchThrowable(it)
//            })
//            emitter.onComplete()
//        }
//    }

    override fun setupWorkManager2() {
        val uploadWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build()

        WorkManager
            .getInstance(appContext)
            .enqueue(uploadWorkRequest)

        Log.d("SSS", "setupWorkManager2")
    }

//    private fun processWorkManager(persons: List<PersonEntity>) {
//        val uploadWorkRequest: WorkRequest =
//            PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
//                .build()
//
//        WorkManager
//            .getInstance(appContext)
//            .enqueue(uploadWorkRequest)
//    }

    /**
     * Must be public
     * https://stackoverflow.com/questions/52657196/android-work-manager-could-not-instantiate-worker
     */
    class MyWorker(appContext: Context, workerParams: WorkerParameters) :
        Worker(appContext, workerParams) {
        override fun doWork(): Result {
            Log.d("SSS", "WORKMANAGER works!")

            return Result.success()
        }
    }
}