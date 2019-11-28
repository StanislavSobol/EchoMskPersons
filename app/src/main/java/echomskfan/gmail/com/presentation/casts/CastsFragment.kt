package echomskfan.gmail.com.presentation.casts

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import echomskfan.gmail.com.EXTRA_PERSON_ID
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.casts.CastsScope
import echomskfan.gmail.com.di.casts.DaggerCastsComponent
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import javax.inject.Inject

class CastsFragment : BaseFragment(FragmentType.Child, R.layout.fragment_recycler_view) {

    @CastsScope
    @Inject
    internal lateinit var viewModelFactory: CastsViewModelFactory

    private lateinit var viewModel: CastsViewModel

    private val personId: Int?  by lazy { arguments?.getInt(EXTRA_PERSON_ID) }

    private val adapter: CastsAdapter by lazy { CastsAdapter(viewModel) }

    init {
        DaggerCastsComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        personId ?: run { throw IllegalStateException("personId must not be null") }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CastsViewModel::class.java)
        viewModel.personId = personId

        viewModel.getCastsLiveDataForPerson()
            .observe(viewLifecycleOwner, Observer { list ->
                viewModel.lastLoadedPageNum = if (list.isEmpty()) 0 else list.last().pageNum
                adapter.setItems(list)
            })

        savedInstanceState ?: viewModel.firstAttach()

        viewModel.startPlayLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { id -> mainActivityRouter?.navigateToPlayer(id); }
        })

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter
    }
}
