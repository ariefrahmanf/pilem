package com.ariefrahmanfajri.pilem.presentation.playvideo

import android.app.Application
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import com.ariefrahmanfajri.pilem.databinding.FragmentPlayVideoBinding
import com.ariefrahmanfajri.pilem.di.ApiModule
import com.ariefrahmanfajri.pilem.di.DaggerAppComponent
import com.ariefrahmanfajri.pilem.presentation.tvshow.TvShowViewModel
import com.ariefrahmanfajri.pilem.util.Resource
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import javax.inject.Inject


class PlayVideoFragment : Fragment() {

    @Inject
    lateinit var tvShowViewModel: TvShowViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentPlayVideoBinding.inflate(layoutInflater)
    }
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private val args by navArgs<PlayVideoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerAppComponent.builder()
            .apiModule(ApiModule(application = activity?.application as Application))
            .build()
            .inject(this)
        super.onViewCreated(view, savedInstanceState)
        fetchVideo()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @androidx.media3.common.util.UnstableApi
/*    private fun initializePlayer() {
        player = ExoPlayer.Builder(binding.root.context)
            .build()
            .also { exoPlayer ->
                binding.exoPlayerView.player = exoPlayer

                val mediaItem =
                    MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")

                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
            }
    }*/


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val window = activity?.window as Window
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE


        window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            }
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
            }
            view.onApplyWindowInsets(windowInsets)
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
//            initializePlayer()
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onResume() {
        super.onResume()
//        hideSystemUi()
        if (Util.SDK_INT <= 23 || player == null) {
//            initializePlayer()
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
//            releasePlayer()
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
//            releasePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.exoPlayerView.release();
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    private fun youtubePlayer(key: String) {
        viewLifecycleOwner.lifecycle.addObserver(binding.exoPlayerView)

/*        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                // using pre-made custom ui
                val defaultPlayerUiController =
                    DefaultPlayerUiController(binding.exoPlayerView, youTubePlayer)
                binding.exoPlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)

                youTubePlayer.loadVideo(
                    key,
                    0F
                )
            }
        }

        val options = IFramePlayerOptions.Builder().controls(0).build()
        binding.exoPlayerView.initialize(listener, options)*/

        binding.exoPlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = key
                youTubePlayer.loadVideo(videoId, 0F)
            }
        })
    }

    fun fetchVideo() {
        tvShowViewModel.getVideo(args.id).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Toast.makeText(binding.root.context, "Loading", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    it.data?.let {
                        it.results?.let { videos ->
                            videos.forEach { item ->
                                if (item.type == "Trailer") {
                                    youtubePlayer(item.key as String)
                                }
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}