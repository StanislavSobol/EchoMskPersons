package echomskfan.gmail.com.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import echomskfan.gmail.com.R
import echomskfan.gmail.com.presentation.main.IMainActivityRouter
import echomskfan.gmail.com.presentation.main.MainActivity

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

    protected val mainActivity: MainActivity
        get() = requireActivity() as MainActivity

    protected val mainActivityRouter: IMainActivityRouter?
        get() = mainActivity.mainActivityRouter

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

        mainActivity.favMenuItemVisible = isFavMenuItemVisible()
        mainActivity.settingsMenuItemVisible = isSettingsMenuItemVisible()

        injectDependencies()
    }

    abstract fun isFavMenuItemVisible(): Boolean

    protected open fun isSettingsMenuItemVisible() = true

    protected abstract fun injectDependencies()
}

enum class FragmentType {
    Main, Child, None
}