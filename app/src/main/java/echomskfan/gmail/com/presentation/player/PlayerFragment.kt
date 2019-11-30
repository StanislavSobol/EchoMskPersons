package echomskfan.gmail.com.presentation.player

import echomskfan.gmail.com.MApplication
import echomskfan.gmail.com.R
import echomskfan.gmail.com.di.player.DaggerPlayerComponent
import echomskfan.gmail.com.di.player.PlayerScope
import echomskfan.gmail.com.presentation.BaseFragment
import echomskfan.gmail.com.presentation.FragmentType
import javax.inject.Inject

class PlayerFragment : BaseFragment(FragmentType.None, R.layout.fragment_player) {

    @PlayerScope
    @Inject
    internal lateinit var viewModelFactory: PlayerViewModelFactory

    init {
        DaggerPlayerComponent.builder()
            .appComponent(MApplication.getAppComponent())
            .build()
            .inject(this)
    }

//    private val mediaPlayerServiceIntent: Intent = Intent(requireContext(), MediaPlayerService::class.java)
//    private var mediaPlayerServiceConnection: ServiceConnection? = null
//    private var mediaPlayerService: MediaPlayerService? = null


    // mediaPlayerServiceIntent = Intent(this, MediaPlayerService::class.java)
/*
    private fun initMediaPlayer() {
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