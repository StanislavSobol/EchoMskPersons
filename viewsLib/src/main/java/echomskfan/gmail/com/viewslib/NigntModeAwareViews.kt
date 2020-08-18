package echomskfan.gmail.com.viewslib

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

class NightModeAwareButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyle) {

    override fun setBackground(background: Drawable?) {
        super.setBackground(background)
        if (isNightMode()) {
            this.background?.let {
                DrawableCompat.setTint(it, Color.WHITE);
            }
        }
    }
}

class NightModeAwareIconImage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyle) {

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        if (isNightMode()) {
            setColorFilter(ContextCompat.getColor(context, android.R.color.white))
        }
    }
}

// TODO to common lib
private fun isNightMode() =
    AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES