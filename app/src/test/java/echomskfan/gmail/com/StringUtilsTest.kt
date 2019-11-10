package echomskfan.gmail.com

import echomskfan.gmail.com.utils.toDate
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class StringUtilsTest {
    @Test
    fun toDate() {
        assertTrue("07 августа 2019, 21:05".toDate() == GregorianCalendar(2019, 8, 7, 21, 5, 0).time)
    }
}
