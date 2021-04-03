package echomskfan.gmail.com.domain.interactor.main

import android.content.Context
import androidx.work.*
import echomskfan.gmail.com.ConfigInjector
import echomskfan.gmail.com.annotations.configinjector.ConfigParamInteger
import echomskfan.gmail.com.domain.interactor.checknew.CheckNewWorker
import echomskfan.gmail.com.domain.repository.IRepository
import java.util.concurrent.TimeUnit

class MainInteractor(private val appContext: Context, private val repository: IRepository) :
    IMainInteractor {

    @ConfigParamInteger("checkNewInitialDelayInMin")
    var checkNewInitialDelayInMin = DEFAULT_REPEAT_INTERVAL_MIN

    @ConfigParamInteger("checkNewDurationInMin")
    var checkNewDurationInMin = DEFAULT_DURATION_MIN

    init {
        ConfigInjector.inject(this)
    }

    override var isFavOn: Boolean
        get() = repository.isFavOn
        set(value) {
            repository.isFavOn = value
        }

    override fun setupWorkManager() {
        // TODO Start only if there are persons with "notification" sign. Otherwise, stop the WorkManager

        val uploadWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<CheckNewWorker>(checkNewDurationInMin.toLong(), TimeUnit.MINUTES)
                .setInitialDelay(checkNewInitialDelayInMin.toLong(), TimeUnit.MINUTES)
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
        private const val DEFAULT_REPEAT_INTERVAL_MIN = 30
        private const val DEFAULT_DURATION_MIN = 120
    }
}