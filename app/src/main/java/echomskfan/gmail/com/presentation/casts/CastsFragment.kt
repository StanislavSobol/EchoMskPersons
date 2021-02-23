package echomskfan.gmail.com.presentation.casts

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corelib.visibleOrGone
import echomskfan.gmail.com.EXTRA_PERSON_ID
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.annotations.featurenavigator.ForFeatureNavigator
import echomskfan.gmail.com.di.casts.CastsScope
import echomskfan.gmail.com.di.casts.DaggerCastsComponent
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import echomskfan.gmail.com.presentation.main.IFavMenuItemClickListener
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import kotlinx.android.synthetic.main.full_progress_bar_content.*
import javax.inject.Inject

@ForFeatureNavigator(enabled = true)
class CastsFragment :
    BaseFragment(fragmentType = FragmentType.Child, layoutId = R.layout.fragment_recycler_view),
    IFavMenuItemClickListener {

    @CastsScope
    @Inject
    internal lateinit var viewModelFactory: CastsViewModelFactory

    private lateinit var viewModel: CastsViewModel

    private val personId: Int? by lazy { arguments?.getInt(EXTRA_PERSON_ID) }

    private val adapter: CastsAdapter by lazy { CastsAdapter(viewModel) }

    private var favOn: Boolean = false

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
        viewModel.personId = personId // TODO Put the Id to Dagger 2 (Provider ?)

        subscribeToCastsLiveDataForPerson()

        savedInstanceState ?: viewModel.loadData()

        viewModel.navigateToPlayerFragmentLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { id -> mainActivityRouter?.navigateToPlayerFromCasts(id); }
        })

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter

        viewModel.showProgressLiveData.observe(viewLifecycleOwner, Observer { showProgress(it) })

        mainActivity.favMenuItemClickListener = this
    }

    override fun isFavMenuItemVisible() = true

    override fun onFavMenuItemClick(favOn: Boolean) {
        this.favOn = favOn
        subscribeToCastsLiveDataForPerson()
    }

    private fun subscribeToCastsLiveDataForPerson() {
        viewModel.getCastsLiveDataForPerson().removeObservers(viewLifecycleOwner)
        viewModel.getCastsLiveDataForPerson().observe(viewLifecycleOwner, Observer { list ->
            setItems(list)
            viewModel.lastLoadedPageNum = if (list.isEmpty()) 0 else list.last().pageNum
        })
    }

    private fun setItems(items: List<CastListItem>) {
        if (favOn) {
            adapter.setItems(items.filter { it.fav }, favsOn = true)
        } else {
            adapter.setItems(items)
        }
    }

    private fun showProgress(show: Boolean) {
        progressBar.visibleOrGone(show)
        recyclerView.visibleOrGone(!show)
    }
}
