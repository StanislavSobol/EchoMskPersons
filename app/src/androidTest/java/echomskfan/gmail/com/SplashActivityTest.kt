package echomskfan.gmail.com


import android.content.Context
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import echomskfan.gmail.com.presentation.SplashActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    private lateinit var appContext: Context

    @get:Rule
    var activityRule: ActivityTestRule<SplashActivity> = ActivityTestRule(SplashActivity::class.java)

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun splashActivity_textView() {
        val textView = activityRule.activity.findViewById<TextView>(R.id.splashActivityText)
        assertEquals(appContext.getString(R.string.splash_text), textView.text.toString())
        assertEquals(appContext.getColor(R.color.colorEcho4), textView.currentTextColor)
    }
}
