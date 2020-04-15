package echomskfan.gmail.com.presentation.personinfo

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import echomskfan.gmail.com.EXTRA_PERSON_ID
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.personinfo.DaggerPersonInfoComponent
import echomskfan.gmail.com.di.persons.PersonsScope
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import kotlinx.android.synthetic.main.fragment_person_info.*
import javax.inject.Inject

class PersonInfoFragment : BaseFragment(FragmentType.Child, R.layout.fragment_person_info) {

    @PersonsScope
    @Inject
    internal lateinit var viewModelFactory: PersonInfoViewModelFactory

    private lateinit var viewModel: PersonInfoViewModel

    private val personId: Int? by lazy { arguments?.getInt(EXTRA_PERSON_ID) }

    init {
        DaggerPersonInfoComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        personId ?: run { throw IllegalStateException("personId must not be null") }

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(PersonInfoViewModel::class.java)
        viewModel.getPersonLiveData(personId!!)
            .observe(viewLifecycleOwner, Observer { item -> initViews(item) })
    }

    private fun initViews(personInfoViewEntity: PersonInfoViewEntity?) {
        if (personInfoViewEntity == null) {
            throw IllegalStateException("Cannot load the person from DB")
        }

        Picasso.with(personInfoImageView.context)
            .load(personInfoViewEntity.photoUrl)
            .into(personInfoImageView)

        personInfoFullNameTextView.text = personInfoViewEntity.fullName
        personInfoProfessionTextView.text = personInfoViewEntity.profession
        personInfoInfoTextView.text = personInfoViewEntity.info
    }

    override fun isFavMenuItemVisible() = false
}