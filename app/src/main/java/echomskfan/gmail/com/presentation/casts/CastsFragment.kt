package echomskfan.gmail.com.presentation.casts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.casts.CastsScope
import echomskfan.gmail.com.di.casts.DaggerCastsComponent
import kotlinx.android.synthetic.main.persons_fragment.*
import javax.inject.Inject

class CastsFragment : Fragment() {

    @CastsScope
    @Inject
    internal lateinit var viewModelFactory: CastsViewModelFactory

    private lateinit var viewModel: CastsViewModel

    private val adapter: CastsAdapter by lazy { CastsAdapter(viewModel) }

    init {
        DaggerCastsComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.persons_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CastsViewModel::class.java)
        viewModel.getCastsLiveData()?.observe(this, Observer { list -> adapter.addItems(list) })
        savedInstanceState ?: run { viewModel.firstAttach() }

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter

        val personId = arguments?.getInt("personId")

        Log.d("SSS", "personId = $personId")
    }
}
