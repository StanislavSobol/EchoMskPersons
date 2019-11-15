package echomskfan.gmail.com.presentation.persons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.persons.DaggerPersonsComponent
import echomskfan.gmail.com.di.persons.PersonsScope
import echomskfan.gmail.com.presentation.MainActivity
import kotlinx.android.synthetic.main.persons_fragment.*
import javax.inject.Inject

class PersonsFragment : Fragment() {

    @PersonsScope
    @Inject
    internal lateinit var viewModelFactory: PersonsViewModelFactory

    private lateinit var viewModel: PersonsViewModel

    private val adapter: PersonsAdapter by lazy { PersonsAdapter(viewModel) }

    init {
        DaggerPersonsComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.persons_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PersonsViewModel::class.java)

        viewModel.getPersonsLiveData().observe(viewLifecycleOwner, Observer { list -> adapter.addItems(list) })
        viewModel.navigationLiveDate.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { id -> (requireActivity() as MainActivity).navigateToCasts(id) }
        })

        savedInstanceState ?: run { viewModel.firstAttach() }

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter
    }
}
