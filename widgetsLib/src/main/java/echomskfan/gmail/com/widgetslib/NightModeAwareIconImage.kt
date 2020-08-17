package echomskfan.gmail.com.widgetslib

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class NightModeAwareIconImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyle) {

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        val nightModeOn =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        if (nightModeOn) {
            setColorFilter(ContextCompat.getColor(context, android.R.color.white))
        }
    }
}