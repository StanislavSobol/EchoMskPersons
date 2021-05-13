package echomskfan.gmail.com.presentation.settings

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.DaggerSettingsComponent
import echomskfan.gmail.com.di.core.ViewModelFactory
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment(FragmentType.None, R.layout.fragment_settings) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: SettingsViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java) }

    init {
        DaggerSettingsComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel
            .run {
                nightModeLiveData.observe(viewLifecycleOwner, Observer { checked -> nightModeSwitch.isChecked = checked })
                nightModeSwitch.setOnCheckedChangeListener { _, checked -> onNightModeSwitchChecked(checked) }
            }
    }

    override fun isFavMenuItemVisible() = false

    override fun isSettingsMenuItemVisible() = false
}