package echomskfan.gmail.com.presentation.debugpanel

import android.app.ProgressDialog
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.debugpanel.DaggerDebugPanelComponent
import echomskfan.gmail.com.di.debugpanel.DebugPanelScope
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import kotlinx.android.synthetic.main.fragment_debug_panel.*
import javax.inject.Inject

class DebugPanelFragment : BaseFragment(FragmentType.None, R.layout.fragment_debug_panel) {

    @DebugPanelScope
    @Inject
    internal lateinit var viewModelFactory: DebugPanelViewModelFactory

    private lateinit var viewModel: DebugPanelViewModel
    private var progressDialog: ProgressDialog? = null

    init {
        DaggerDebugPanelComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    override fun isFavMenuItemVisible() = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DebugPanelViewModel::class.java)

        viewModel.showProgressLiveData.observe(viewLifecycleOwner, Observer { showProgress(it) })

        deleteLastNevzorovButton.setOnClickListener { viewModel.deleteLastNevzorovCastButtonClicked() }
        workManagerActionButton.setOnClickListener { viewModel.workManagerActionButtonClicked() }
    }

    // TODO Replace it with CustomButton with progress bar or smth
    private fun showProgress(show: Boolean) {
        if (show) {
            progressDialog = ProgressDialog(requireActivity())
            progressDialog?.let {
                it.setMessage("Wait a second...")
                it.show()
            }
        } else {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }
}