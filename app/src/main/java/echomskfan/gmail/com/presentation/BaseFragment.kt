package echomskfan.gmail.com.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import echomskfan.gmail.com.R

/**
 * Class to store all common features for diff. classes placed on MainActivity.
 *
 * No need to keep all constructor-passed parameters in arguments
 * because the default constructor is called from every child empty constructor every time the child fragment is recreated
 */
abstract class BaseFragment(
    private val fragmentType: FragmentType,
    @LayoutRes private val layoutId: Int
) : Fragment() {

    protected val mainActivityRouter: IMainActivityRouter?
        get() {
            val activity = requireActivity()
            return if (activity is IMainActivityRouter) {
                activity
            } else {
                null
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

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
}

enum class FragmentType {
    Main, Child, None
}