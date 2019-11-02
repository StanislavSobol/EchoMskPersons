package echomskfan.gmail.com.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import echomskfan.gmail.com.R

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
        Handler().postDelayed({ startActivity(Intent(this, MainActivity::class.java)) }, 3000)
    }
}
