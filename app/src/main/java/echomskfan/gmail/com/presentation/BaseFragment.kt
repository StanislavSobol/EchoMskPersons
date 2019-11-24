package echomskfan.gmail.com.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import echomskfan.gmail.com.R

abstract class BaseFragment(
    private val fragmentType: FragmentType,
    @LayoutRes private val layoutId: Int
) : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }
}

enum class FragmentType {
    Main, Child, None
}