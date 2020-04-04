package echomskfan.gmail.com.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import echomskfan.gmail.com.R
import echomskfan.gmail.com.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Splash-screen activity
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                startMainActivity()
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        splashActivityLogoImageView.animation = anim
    }

    private fun startMainActivity() {
        Handler().postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
//                ActivityNavigator.applyPopAnimationsToPendingTransition(this)
            },
            SPLASH_DELAY_MS
        )
    }

    companion object {
        const val SPLASH_DELAY_MS = 500L
    }
}
