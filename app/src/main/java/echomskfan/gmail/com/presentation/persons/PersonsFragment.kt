package echomskfan.gmail.com.presentation.persons

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corelib.visibleOrGone
import echomskfan.gmail.com.EXTRA_PERSON_ID
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.DaggerPersonsComponent
import echomskfan.gmail.com.di.ViewModelFactory
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import echomskfan.gmail.com.presentation.main.IFavMenuItemClickListener
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import kotlinx.android.synthetic.main.full_progress_bar_content.*
import javax.inject.Inject

class PersonsFragment : BaseFragment(FragmentType.Main, R.layout.fragment_recycler_view), IFavMenuItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PersonsViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(PersonsViewModel::class.java) }

    private val adapter: PersonsAdapter by lazy { PersonsAdapter(viewModel) }

    private var favOn: Boolean = false

    init {
        DaggerPersonsComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeToPersonsLiveData()

        viewModel.navigateToCastsLiveDate.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { id -> mainActivityRouter?.navigateToCastsFromPersons(id) }
        })

        viewModel.navigateToPersonInfoLiveDate.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()
                ?.let { id -> mainActivityRouter?.navigateToPersonInfoFromPersons(id) }
        })

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter

        viewModel.showProgressLiveData.observe(viewLifecycleOwner, Observer { showProgress(it) })

        mainActivity.favMenuItemClickListener = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.intent?.getIntExtra(EXTRA_PERSON_ID, 0)?.let {
            if (it != 0) {
                mainActivityRouter?.navigateToCastsFromPersons(it)
            }
            mainActivity.intent = null
        }
    }

    override fun isFavMenuItemVisible() = true

    override fun onFavMenuItemClick(favOn: Boolean) {
        this.favOn = favOn
        subscribeToPersonsLiveData()
    }

    private fun subscribeToPersonsLiveData() {
        viewModel.getPersonsLiveData().removeObservers(viewLifecycleOwner)
        viewModel.getPersonsLiveData()
            .observe(viewLifecycleOwner, Observer { list -> setItems(list) })
    }

    private fun setItems(items: List<PersonListItem>) {
        if (favOn) {
            adapter.setItems(items.filter { it.fav })
        } else {
            adapter.setItems(items)
        }
    }

    private fun showProgress(show: Boolean) {
        progressBar.visibleOrGone(show)
        recyclerView.visibleOrGone(!show)
    }
}
