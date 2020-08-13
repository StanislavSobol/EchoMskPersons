package echomskfan.gmail.com

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import echomskfan.gmail.com.annotationlib.FeatureNavigator
import echomskfan.gmail.com.presentation.casts.CastsFragment
import echomskfan.gmail.com.presentation.debugpanel.DebugPanelFragment
import echomskfan.gmail.com.presentation.personinfo.PersonInfoFragment
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.*
import org.powermock.api.mockito.PowerMockito.mockStatic
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(Toast::class)
class FeatureNavigatorTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockLambda: () -> Unit

    @Mock
    private lateinit var mockToast: Toast

    @Before
    fun setup() {
        mockStatic(Toast::class.java)
        `when`(
            Toast.makeText(
                eq(mockContext),
                anyString() as CharSequence,
                eq(Toast.LENGTH_LONG)
            )
        ).thenReturn(mockToast)

        with(FeatureNavigator) {
            appContext = mockContext
            isBuildConfigDebug = true
            addAllowedElement(CLASS_NOT_ONLY_FOR_DEBUG.name, false)
            addAllowedElement(CLASS_ONLY_FOR_DEBUG.name, true)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun appContext_is_null() {
        with(FeatureNavigator) {
            appContext = null
            navigate(CLASS_NOT_ONLY_FOR_DEBUG) {}
        }
    }

    @Test(expected = IllegalStateException::class)
    fun isBuildConfigDebug_is_null() {
        with(FeatureNavigator) {
            isBuildConfigDebug = null
            navigate(CLASS_NOT_ONLY_FOR_DEBUG) {}
        }
    }

    @Test
    fun isBuildConfigDebug_true_onlyForDebug_false() {
        with(FeatureNavigator) {
            isBuildConfigDebug = true
            assertTrue(isFeatureEnabled(CLASS_NOT_ONLY_FOR_DEBUG))
        }
    }

    @Test
    fun isBuildConfigDebug_true_onlyForDebug_true() {
        with(FeatureNavigator) {
            isBuildConfigDebug = true
            assertTrue(isFeatureEnabled(CLASS_ONLY_FOR_DEBUG))
        }
    }

    @Test
    fun isBuildConfigDebug_false_onlyForDebug_false() {
        with(FeatureNavigator) {
            isBuildConfigDebug = false
            assertTrue(isFeatureEnabled(CLASS_NOT_ONLY_FOR_DEBUG))
        }
    }

    @Test
    fun isBuildConfigDebug_false_onlyForDebug_true() {
        with(FeatureNavigator) {
            isBuildConfigDebug = false
            assertFalse(isFeatureEnabled(CLASS_ONLY_FOR_DEBUG))
        }
    }

    @Test
    fun navigate_ok_feature_enabled() {
        FeatureNavigator.navigate(CLASS_NOT_ONLY_FOR_DEBUG, mockLambda)

        verify(mockLambda).invoke()
        verifyNoMoreInteractions(mockLambda)
        verifyNoMoreInteractions(mockToast)
    }

    @Test
    fun navigate_feature_disabled_onlyForDebug() {
        with(FeatureNavigator) {
            isBuildConfigDebug = false
            navigate(CLASS_ONLY_FOR_DEBUG, mockLambda)
        }
        verifyNoMoreInteractions(mockLambda)
        verify(mockToast).setGravity(Gravity.CENTER, 0, 0)
        verify(mockToast).show()
        verifyNoMoreInteractions(mockToast)
    }

    @Test
    fun navigate_feature_absent() {
        with(FeatureNavigator) {
            isBuildConfigDebug = false
            navigate(CLASS_ABSENT_IN_ALLOWED_ELEMENTS_LIST, mockLambda)
        }
        verifyNoMoreInteractions(mockLambda)
        verify(mockToast).setGravity(Gravity.CENTER, 0, 0)
        verify(mockToast).show()
        verifyNoMoreInteractions(mockToast)
    }

    companion object {
        private val CLASS_NOT_ONLY_FOR_DEBUG = CastsFragment::class.java
        private val CLASS_ONLY_FOR_DEBUG = DebugPanelFragment::class.java
        private val CLASS_ABSENT_IN_ALLOWED_ELEMENTS_LIST = PersonInfoFragment::class.java
    }
}