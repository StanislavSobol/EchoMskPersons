package echomskfan.gmail.com.presentation.persons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.persons.DaggerPersonsComponent
import echomskfan.gmail.com.di.persons.PersonsScope
import javax.inject.Inject

class PersonsFragment : Fragment() {

    @PersonsScope
    @Inject
    internal lateinit var viewModelFactory: PersonsViewModelFactory

    private lateinit var viewModel: PersonsViewModel

    init {
        DaggerPersonsComponent.builder().build().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.persons_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PersonsViewModel::class.java)
    }

    companion object {
        fun newInstance() = PersonsFragment()
    }
}
