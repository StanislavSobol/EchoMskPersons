package echomskfan.gmail.com.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import echomskfan.gmail.com.R

abstract class BaseFragment(private val fragmentType: FragmentType) : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.let {
                when (fragmentType) {
                    FragmentType.Main -> {
                        it.setDisplayHomeAsUpEnabled(false)
                    }
                    FragmentType.Child -> {
                        it.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
                        it.setDisplayHomeAsUpEnabled(true)
                    }
                    FragmentType.None -> {
                        it.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
                        it.setDisplayHomeAsUpEnabled(true)
                    }
                }
            }
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            if (activity is MainActivity) {
//                (activity as MainActivity).back()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}

enum class FragmentType {
    Main, Child, None
}