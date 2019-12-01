package echomskfan.gmail.com.presentation.player

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import echomskfan.gmail.com.EXTRA_CAST_ID
import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.player.DaggerPlayerComponent
import echomskfan.gmail.com.di.player.PlayerScope
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import echomskfan.gmail.com.utils.logInfo
import javax.inject.Inject

class PlayerFragment : BaseFragment(FragmentType.None, R.layout.fragment_player) {

    @PlayerScope
    @Inject
    internal lateinit var viewModelFactory: PlayerViewModelFactory

    private lateinit var viewModel: PlayerViewModel
    private val castId: String? by lazy { arguments?.getString(EXTRA_CAST_ID) }
    private var playerItem: PlayerItem? = null

    init {
        DaggerPlayerComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

//    private var mediaPlayerServiceIntent: Intent? = null
//    private var mediaPlayerServiceConnection: ServiceConnection? = null
//    private var mediaPlayerService: MediaPlayerService? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlayerViewModel::class.java)
        savedInstanceState ?: viewModel.firstAttach(castId)
        viewModel.playerItemLiveData.observe(this, Observer {
            playerItem = it
            playerItem?.run { initMediaPlayer() }
        })
    }

    private fun initMediaPlayer() {
        logInfo(playerItem)
    }


    // mediaPlayerServiceIntent = Intent(this, MediaPlayerService::class.java)
/*
    private fun initMediaPlayer() {
        mediaPlayerServiceIntent = Intent(this, MediaPlayerService::class.java)
        mediaPlayerServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                mediaPlayerService = (service as MediaPlayerService.MediaServiceBinder).service
                mediaPlayerService?.parentActivity = this@MainActivity
                if (playedItem != null) {
                    mediaPlayerService?.play(playedItem!!)
                } else {
                    playedItem = mediaPlayerService?.playingItem
                    if (playedItem != null) {
                        initAudioPanel()
                    }
                }
            }

            override fun onServiceDisconnected(name: ComponentName) {
                mediaPlayerService = null
            }
        }

        if (intent.action == null) {
            bindService(mediaPlayerServiceIntent, mediaPlayerServiceConnection, BIND_AUTO_CREATE)
        }
    }
    */
}