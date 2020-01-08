package echomskfan.gmail.com

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import echomskfan.gmail.com.data.prefs.SharedPrefs.Companion.PREFS_NAME
import echomskfan.gmail.com.presentation.main.MainActivity
import echomskfan.gmail.com.utils.logWarning
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var appContext: Context

    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
            override fun beforeActivityLaunched() {
                clearSharedPrefs(InstrumentationRegistry.getInstrumentation().targetContext)
                super.beforeActivityLaunched()
            }
        }

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun mainActivity_isDisplayed() {
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()))
    }

    @Test
    fun mainActivity_actionBar() {
        onView(withId(R.id.action_bar)).check(matches(isDisplayed()))
        onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.action_bar))))
    }

    @Test
    fun mainActivity_favMenuItem_isDisplayed() {
        onView(withId(R.id.mainMenuItemFav)).check(matches(isDisplayed()))
    }

    @Test
    fun mainActivity_favMenuItem_click() {
        onView(withId(R.id.mainMenuItemFav)).check(matches(withActionIconDrawable(R.drawable.ic_favorite_border_black_24dp)))
        onView(withId(R.id.mainMenuItemFav)).perform(click())
        onView(withId(R.id.mainMenuItemFav)).check(matches(withActionIconDrawable(R.drawable.ic_favorite_white_24dp)))
    }

    private fun withActionIconDrawable(@DrawableRes resourceId: Int): Matcher<View> {
        return object : BoundedMatcher<View, ActionMenuItemView>(ActionMenuItemView::class.java) {
            override fun describeTo(description: Description?) {
                description?.appendText("Image drawable resource $resourceId")
            }

            override fun matchesSafely(item: ActionMenuItemView?): Boolean {
                val actualIcon = item?.itemData?.icon
                val expectedIcon = appContext.getDrawable(resourceId)
                if (actualIcon == null || expectedIcon == null) {
                    return false
                }
                return actualIcon.constantState == expectedIcon.constantState ||
                        actualIcon.getBitmap().sameAs(expectedIcon.getBitmap())
            }
        }
    }

    private fun Drawable.getBitmap(): Bitmap {
        val result: Bitmap
        if (this is BitmapDrawable) {
            logWarning("Why do I still use a bitmap drawable?")
            result = this.bitmap
        } else {
            var width = this.intrinsicWidth
            var height = this.intrinsicHeight
            if (width <= 0) {
                width = 1
            }
            if (height <= 0) {
                height = 1
            }

            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(result)
            this.setBounds(0, 0, canvas.width, canvas.height)
            this.draw(canvas)
        }
        return result
    }

    private fun clearSharedPrefs(appContext: Context) {
        appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .apply {
                clear()
                commit()
            }
    }
}
