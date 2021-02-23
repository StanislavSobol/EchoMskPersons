package echomskfan.gmail.com.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import echomskfan.gmail.com.FeatureConfigurator
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.annotations.featureconfigurator.FeatureToggleBoolean
import echomskfan.gmail.com.domain.interactor.config.IConfigProvider
import echomskfan.gmail.com.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

/**
 * Splash-screen activity with an animation.
 *
 * The animation can be disabled via [@IConfigProvider].
 * The activity without its ViewModel to make it light as much as possible and to avoid overengineering.
 *
 * So, [@IConfigProvider] is injected right here
 */
class SplashActivity : AppCompatActivity() {
    @Inject
    internal lateinit var configProvider: IConfigProvider

    @FeatureToggleBoolean("showSplashAnimation")
    var isShowSplashAnimation: Boolean = true

    private var realStart = false

    init {
        MApplication.getAppComponent().inject(this)
        FeatureConfigurator.bind(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        realStart = savedInstanceState == null
    }

    override fun onResume() {
        super.onResume()
        if (realStart) {
            if (isShowSplashAnimation) {
                splashActivityLogoImageView.animation = prepareSplashAnimation()
            } else {
                startMainActivity()
            }
        }
    }

    private fun prepareSplashAnimation(): Animation? {
        return AnimationUtils.loadAnimation(this, R.anim.splash_anim)
            .apply {
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {}

                    override fun onAnimationEnd(p0: Animation?) {
                        startMainActivity()
                    }

                    override fun onAnimationStart(p0: Animation?) {}
                })
            }
    }

    private fun startMainActivity() {
        Handler().postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                ActivityNavigator.applyPopAnimationsToPendingTransition(this)
            },
            configProvider.splashDelayMSec
        )
    }
}
