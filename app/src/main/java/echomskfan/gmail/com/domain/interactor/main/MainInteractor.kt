package echomskfan.gmail.com.domain.interactor.main

import android.content.Context
import androidx.work.*
import echomskfan.gmail.com.domain.interactor.checknew.CheckNewWorker
import echomskfan.gmail.com.domain.repository.IRepository
import java.util.concurrent.TimeUnit

class MainInteractor(private val appContext: Context, private val repository: IRepository) :
    IMainInteractor {

    override var isFavOn: Boolean
        get() = repository.isFavOn
        set(value) {
            repository.isFavOn = value
        }

    override fun setupWorkManager() {
        // TODO Start only if there are persons with "notification" sign. Otherwise, stop the WorkManager

        val uploadWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<CheckNewWorker>(REPEAT_INTERVAL_MIN, TimeUnit.MINUTES)
                .setInitialDelay(DURATION_MIN, TimeUnit.MINUTES)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()

        WorkManager
            .getInstance(appContext)
            .enqueue(uploadWorkRequest)
    }

    companion object {
        // TODO Move it to the config
        private const val REPEAT_INTERVAL_MIN = 15L

        // TODO Move it to the config
        private const val DURATION_MIN = 1L
    }
}