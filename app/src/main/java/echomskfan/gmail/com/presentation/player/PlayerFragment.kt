package echomskfan.gmail.com.presentation.player

import android.os.Bundle
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.corelib.gone
import com.example.corelib.visibleOrGone
import com.example.corelib.visibleOrInvisible
import echomskfan.gmail.com.EXTRA_CAST_ID
import echomskfan.gmail.com.EXTRA_PLAYER_RESUME
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.DaggerPlayerComponent
import echomskfan.gmail.com.di.core.ViewModelFactory
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import echomskfan.gmail.com.utils.fromMilliSecToSec
import echomskfan.gmail.com.utils.fromSecToAudioDuration
import kotlinx.android.synthetic.main.fragment_player.*
import javax.inject.Inject

class PlayerFragment : BaseFragment(FragmentType.None, R.layout.fragment_player) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PlayerViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(PlayerViewModel::class.java) }

    private val castId: String? by lazy { arguments?.getString(EXTRA_CAST_ID) }
    private val resume: Boolean by lazy { arguments?.getBoolean(EXTRA_PLAYER_RESUME) ?: false }
    private val playerBridge: PlayerBridge by lazy { PlayerBridge(this) }

    // TODO onViewCreated
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.playerItemLiveData.observe(this, Observer {
            it?.let { playerItem ->
                initViews(playerItem)
                if (resume || savedInstanceState != null) {
                    playerBridge.bindServiceAndResume()
                } else {
                    playerBridge.bindServiceAndPlay(playerItem)
                }
            }
        })

        // TODO savedInstanceState == null to init in viewModels
        if (savedInstanceState == null) {
            showProgress(true)
            // TODO Provide castId via constructor of the viewModel
            viewModel.loadData(castId)
        }
    }

    override fun onDestroy() {
        playerBridge.unbindService()
        super.onDestroy()
    }

    fun notifyPlayerItemChanged() {
        if (PlayerItemVisualState.isClosed) {
            mainActivityRouter?.closePlayerFragment()
            return
        }

        val progressSec = PlayerItemVisualState.progressMSec.fromMilliSecToSec()

        if (PlayerItemVisualState.isPlaying) {
            progressTextView?.text = progressSec.fromSecToAudioDuration()
        }

        fragmentPlayerPlayButton.visibleOrGone(!PlayerItemVisualState.isPlaying)
        fragmentPlayerPauseButton.visibleOrGone(PlayerItemVisualState.isPlaying)

        playerFragmentAudioSeekBar?.progress = progressSec

        viewModel.playedTimeChanged(castId, progressSec)
    }

    override fun isFavMenuItemVisible() = false

    override fun injectDependencies() {
        DaggerPlayerComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    private fun initViews(playerItem: PlayerItem) {
        playerFragmentPersonTextView?.text = playerItem.personName
        playerFragmentTypeSubtypeTextView?.text = playerItem.typeSubtype
        playerFragmentDateTextView?.text = playerItem.formattedDate

        playerFragmentImageView.imageUrl = playerItem.personPhotoUrl

        playerFragmentAudioSeekBar.max = playerItem.mp3DurationSec
        playerFragmentAudioSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    playerBridge.seekTo(progress)
                }
            }
        })

        maxProgressTextView?.text = playerItem.mp3DurationSec.fromSecToAudioDuration()

        fragmentPlayerPlayButton?.setOnClickListener { playerBridge.resume() }
        fragmentPlayerPauseButton?.setOnClickListener { playerBridge.pause() }
    }

    fun mp3Loaded() {
        showProgress(false)
    }

    private fun showProgress(show: Boolean) {
        fragmentPlayerPlayButton.gone()
        fragmentPlayerPauseButton.visibleOrGone(!show)
        playerFragmentAudioSeekBar.visibleOrInvisible(!show)
        fragmentPlayerProgressBar.visibleOrGone(show)
    }
}