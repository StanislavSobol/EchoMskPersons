package echomskfan.gmail.com.presentation.persons

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.persons.DaggerPersonsComponent
import echomskfan.gmail.com.di.persons.PersonsScope
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import echomskfan.gmail.com.presentation.main.IFavMenuItemClickListener
import echomskfan.gmail.com.utils.gone
import echomskfan.gmail.com.utils.visible
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import kotlinx.android.synthetic.main.full_progress_bar_content.*
import javax.inject.Inject

class PersonsFragment : BaseFragment(FragmentType.Main, R.layout.fragment_recycler_view), IFavMenuItemClickListener {

    @PersonsScope
    @Inject
    internal lateinit var viewModelFactory: PersonsViewModelFactory

    private lateinit var viewModel: PersonsViewModel

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

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PersonsViewModel::class.java)

        subscribeToPersonsLiveData()

        viewModel.navigationLiveDate.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { idAndView ->
                mainActivityRouter?.navigateToCastsFromPersons(idAndView.first, idAndView.second)
            }
        })

        savedInstanceState ?: run { viewModel.loadData() }

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter

        viewModel.showProgressLiveData.observe(viewLifecycleOwner, Observer { showProgress(it) })

        mainActivity.favMenuItemClickListener = this
    }

    override fun onFavMenuItemClick(favOn: Boolean) {
        this.favOn = favOn
        subscribeToPersonsLiveData()
    }

    private fun subscribeToPersonsLiveData() {
        viewModel.getPersonsLiveData().removeObservers(viewLifecycleOwner)
        viewModel.getPersonsLiveData().observe(viewLifecycleOwner, Observer { list -> setItems(list) })
    }

    override fun isFavMenuItemVisible() = true

    private fun setItems(items: List<PersonListItem>) {
        if (favOn) {
            adapter.setItems(items.filter { it.fav })
        } else {
            adapter.setItems(items)
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            recyclerView.gone()
            progressBar.visible()
        } else {
            recyclerView.visible()
            progressBar.gone()
        }
    }
}
