package echomskfan.gmail.com.presentation.personinfo

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import echomskfan.gmail.com.EXTRA_PERSON_ID
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.DaggerPersonInfoComponent
import echomskfan.gmail.com.di.core.ViewModelFactory
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import kotlinx.android.synthetic.main.fragment_person_info.*
import javax.inject.Inject

class PersonInfoFragment : BaseFragment(FragmentType.Child, R.layout.fragment_person_info) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PersonInfoViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(PersonInfoViewModel::class.java) }

    private val personId: Int? by lazy { arguments?.getInt(EXTRA_PERSON_ID) }

    init {
        DaggerPersonInfoComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        personId ?: run { throw IllegalStateException("personId must not be null") }
        viewModel.getPersonLiveData(personId!!)
            .observe(viewLifecycleOwner, Observer { item -> initViews(item) })
    }

    override fun isFavMenuItemVisible() = false

    private fun initViews(personInfoViewEntity: PersonInfoViewEntity?) {
        if (personInfoViewEntity == null) {
            throw IllegalStateException("Cannot load the person from DB")
        }

        personInfoImageView.imageUrl = personInfoViewEntity.photoUrl
        personInfoFullNameTextView.text = personInfoViewEntity.fullName
        personInfoProfessionTextView.text = personInfoViewEntity.profession
        personInfoInfoTextView.text = personInfoViewEntity.info
    }
}