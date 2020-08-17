package echomskfan.gmail.com.presentation.player

import android.os.Bundle
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import echomskfan.gmail.com.EXTRA_CAST_ID
import echomskfan.gmail.com.EXTRA_PLAYER_RESUME
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.annotations.featurenavigator.ForFeatureNavigator
import echomskfan.gmail.com.di.player.DaggerPlayerComponent
import echomskfan.gmail.com.di.player.PlayerScope
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import echomskfan.gmail.com.utils.*
import kotlinx.android.synthetic.main.fragment_player.*
import javax.inject.Inject

@ForFeatureNavigator(enabled = true)
class PlayerFragment : BaseFragment(FragmentType.None, R.layout.fragment_player) {

    @PlayerScope
    @Inject
    internal lateinit var viewModelFactory: PlayerViewModelFactory

    private lateinit var viewModel: PlayerViewModel

    private val castId: String? by lazy { arguments?.getString(EXTRA_CAST_ID) }
    private val resume: Boolean by lazy { arguments?.getBoolean(EXTRA_PLAYER_RESUME) ?: false }
    private val playerBridge: PlayerBridge by lazy { PlayerBridge(this) }

    init {
        DaggerPlayerComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlayerViewModel::class.java)
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

        val progressSec = PlayerItemVisualState.progressMSec.fromMSecSec()

        if (PlayerItemVisualState.isPlaying) {
            progressTextView?.text = progressSec.fromSecToAudioDuration()
            fragmentPlayerPlayButton?.gone()
            fragmentPlayerPauseButton?.visible()
        } else {
            fragmentPlayerPlayButton?.visible()
            fragmentPlayerPauseButton?.gone()
        }

        playerFragmentAudioSeekBar?.progress = progressSec
    }

    override fun isFavMenuItemVisible() = false

    private fun initViews(playerItem: PlayerItem) {
        playerFragmentPersonTextView?.text = playerItem.personName
        playerFragmentTypeSubtypeTextView?.text = playerItem.typeSubtype
        playerFragmentDateTextView?.text = playerItem.formattedDate

        Picasso.with(requireContext()).load(playerItem.personPhotoUrl).into(playerFragmentImageView)

        playerFragmentAudioSeekBar.max = playerItem.mp3Duration
        playerFragmentAudioSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    playerBridge.seekTo(progress)
                }
            }
        })

        maxProgressTextView?.text = playerItem.mp3Duration.fromSecToAudioDuration()

        fragmentPlayerPlayButton?.setOnClickListener { playerBridge.resume() }
        fragmentPlayerPauseButton?.setOnClickListener { playerBridge.pause() }
    }

    fun mp3Loaded() {
        showProgress(false)
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            fragmentPlayerPlayButton.gone()
            fragmentPlayerPauseButton.gone()
            playerFragmentAudioSeekBar.invisible()

            fragmentPlayerProgressBar.visible()
        } else {
            fragmentPlayerPlayButton.gone()
            fragmentPlayerPauseButton.visible()
            playerFragmentAudioSeekBar.visible()

            fragmentPlayerProgressBar.gone()
        }
    }
}