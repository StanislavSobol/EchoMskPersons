package echomskfan.gmail.com.domain.interactor.checknew

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import echomskfan.gmail.com.EXTRA_PERSON_ID
import echomskfan.gmail.com.R
import echomskfan.gmail.com.domain.repository.IRepository
import echomskfan.gmail.com.presentation.main.MainActivity
import java.util.*

class CheckNewInteractor(private val appContext: Context, private val repository: IRepository) :
    ICheckNewInteractor {

    override fun checkNewCast() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw IllegalStateException("This method must work only on background")
        }

        repository.getPersonsWithNotification().forEach { person ->
            val dbDate = repository.getMaxCastDateForPerson(person.id)
            val webCasts = repository.getCastsFromWebForPerson(person)
            val lastWebCast = webCasts.firstOrNull()
            val webDate = lastWebCast?.date
            if (lastWebCast != null && webDate != null && (dbDate == null || webDate.after(dbDate))) {
                repository.insertOrUpdateCasts(webCasts)

                // TODO Create routes from every fragment and closed app to the persons' casts list fragment
                val notificationIntent = Intent(appContext, MainActivity::class.java).apply {
                    putExtra(EXTRA_PERSON_ID, person.id)
                }

                val pendingIntent = PendingIntent.getActivity(
                    appContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
                )

                createNotificationBuilder()
                    .setSmallIcon(R.drawable.ic_baseline_cast_24)
                    .setContentTitle(
                        appContext.getString(
                            R.string.check_new_notification_title,
                            person.getFullName()
                        )
                    )
                    // TODO to string res with params
                    .setContentText("${lastWebCast.formattedDate}:  ${lastWebCast.getTypeSubtype()}")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .also {
                        NotificationManagerCompat.from(appContext)
                            .notify(NOTIFICATION_ID, it.build())
                    }

                return
            }
        }
    }

    private fun createNotificationBuilder(): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
            NotificationCompat.Builder(appContext, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(appContext)
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
                .apply {
                    description = CHANNEL_DESCRIPTION
                    enableLights(true)
                    lightColor = Color.BLUE
                }
        NotificationManagerCompat.from(appContext).createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_NAME = "Echo Msk Persons' media player channel"
        private const val CHANNEL_DESCRIPTION =
            "Echo Msk Persons' media player channel is dedicated to Echo Msk podcasts"

        private val NOTIFICATION_ID = UUID.randomUUID().mostSignificantBits.toInt()
        private val CHANNEL_ID = UUID.randomUUID().toString()
    }
}