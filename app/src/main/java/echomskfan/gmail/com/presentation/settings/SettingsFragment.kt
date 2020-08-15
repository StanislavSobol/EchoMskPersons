package echomskfan.gmail.com.presentation.settings

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.annotations.featurenavigator.ForFeatureNavigator
import echomskfan.gmail.com.di.debugpanel.DebugPanelScope
import echomskfan.gmail.com.di.settings.DaggerSettingsComponent
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@ForFeatureNavigator(enabled = true)
class SettingsFragment : BaseFragment(FragmentType.None, R.layout.fragment_settings) {

    @DebugPanelScope
    @Inject
    internal lateinit var viewModelFactory: SettingsViewModelFactory

    init {
        DaggerSettingsComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    override fun isFavMenuItemVisible() = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
            .apply {
                nightModeLiveData.observe(
                    viewLifecycleOwner,
                    Observer { checked -> nightModeSwitch.isChecked = checked })
            }
            .run {
                nightModeSwitch.setOnCheckedChangeListener { _, checked ->
                    onNightModeSwitchChecked(checked)
                }
            }
    }
}