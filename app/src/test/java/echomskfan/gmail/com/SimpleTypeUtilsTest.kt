package echomskfan.gmail.com

import echomskfan.gmail.com.utils.fromSecToAudioDuration
import echomskfan.gmail.com.utils.toDate
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class SimpleTypeUtilsTest {
    @Test
    fun string_toDate() {
        assertEquals(GregorianCalendar(2019, 8, 7, 21, 5, 0).time, "07 августа 2019, 21:05".toDate())
    }

    @Test
    fun int_fromSecToAudioDuration() {
        assertEquals("00:00", (-3658).fromSecToAudioDuration())
        assertEquals("00:00", (-1).fromSecToAudioDuration())
        assertEquals("00:00", 0.fromSecToAudioDuration())
        assertEquals("00:36", 36.fromSecToAudioDuration())
        assertEquals("01:03", 63.fromSecToAudioDuration())
        assertEquals("01:04:54", 3894.fromSecToAudioDuration())
    }
}
