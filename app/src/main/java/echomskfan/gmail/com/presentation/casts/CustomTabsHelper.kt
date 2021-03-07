package echomskfan.gmail.com.presentation.casts

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import echomskfan.gmail.com.R

// [https://developers.google.com/web/android/custom-tabs/implementation-guide]
// [https://developer.chrome.com/docs/multidevice/android/customtabs/]
internal class CustomTabsHelper(context: Context, url: String) {
    init {
        val builder = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(
                CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(context.getColor(R.color.colorPrimary))
                    .build()
            )
            .setStartAnimations(context, R.anim.slide_in_right_anim, R.anim.slide_out_left_anim)
            .setExitAnimations(context, R.anim.slide_out_left_anim, R.anim.slide_in_right_anim)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}