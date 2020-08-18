package echomskfan.gmail.com.viewslib

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.corelib.visibleOrGone
import kotlinx.android.synthetic.main.online_person_image_view.view.*
import java.net.MalformedURLException
import java.net.URL

class OnlinePersonImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var isCircleCropped = false
        set(value) {
            field = value
            adjustViewsVisibility()
        }

    var imageUrl: String? = null
        set(value) {
            field = try {
                URL(value)
                Glide.with(this)
                    .load(value)
                    .adjustCircleCrop()
                    .into(onlinePersonImageViewImageView)
                value
            } catch (e: MalformedURLException) {
                null
            }
            adjustViewsVisibility()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.online_person_image_view, this, true)

        attrs?.let { attrSet ->
            val typedArray = context.obtainStyledAttributes(
                attrSet,
                R.styleable.OnlinePersonImageView,
                defStyle,
                0
            )

            isCircleCropped =
                typedArray.getBoolean(R.styleable.OnlinePersonImageView_circleCropped, false)
            imageUrl = typedArray.getText(R.styleable.OnlinePersonImageView_imageUrl)?.toString()

            typedArray.recycle()
        }
    }

    private fun adjustViewsVisibility() {
        onlinePersonImageViewRectView.visibleOrGone(!isCircleCropped)
        onlinePersonImageViewCircleView.visibleOrGone(isCircleCropped)
        onlinePersonImageEmptyView.visibleOrGone(imageUrl.isNullOrBlank())
        onlinePersonImageViewImageView.visibleOrGone(!imageUrl.isNullOrBlank())
    }

    private fun <TranscodeType> RequestBuilder<TranscodeType>.adjustCircleCrop(): RequestBuilder<TranscodeType> {
        return this.apply {
            if (isCircleCropped) {
                circleCrop()
            }
        }
    }
}
