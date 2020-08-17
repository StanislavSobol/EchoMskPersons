package echomskfan.gmail.com.widgetslib

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.drawable.DrawableCompat

class NightModeAwareButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyle) {

    override fun setBackground(background: Drawable?) {
        super.setBackground(background)
        val nightModeOn =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        if (nightModeOn) {
            this.background?.let {
                DrawableCompat.setTint(it, Color.WHITE);
            }
        }
    }
}