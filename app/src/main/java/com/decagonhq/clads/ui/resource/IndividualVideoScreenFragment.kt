package com.decagonhq.clads.ui.resource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.data.domain.resource.ResourceDetailVideoModel
import com.decagonhq.clads.databinding.IndividualVideoScreenFragmentBinding
import com.decagonhq.clads.ui.resource.adapter.ViewAllVideoRvAdapter
import com.decagonhq.clads.util.ResourceDummyData
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util

class IndividualVideoScreenFragment : Fragment(), ViewAllVideoRvAdapter.Interaction {
    private var _binding: IndividualVideoScreenFragmentBinding? = null
    private lateinit var playerView: PlayerView
    private lateinit var mediaItem: MediaItem
    private lateinit var trackSelector: DefaultTrackSelector
    private var player: SimpleExoPlayer? = null
    private var playerWhenReady: Boolean = true
    private var currentWindow: Int = 0
    private var playBackPosition: Long = 0
    private lateinit var videoRecyclerView: RecyclerView

    //    private lateinit var videoRvAdapter: ViewAllVideoRvAdapter
    private val adapter by lazy { ViewAllVideoRvAdapter(this) }
    private lateinit var playbackStateListener: PlaybackStateListener
    private var url: String = "https://assets.mixkit.co/videos/preview/mixkit-hands-of-a-tailor-working-12528-small.mp4"

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = IndividualVideoScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        /*Initialise Recycler View*/
        videoRecyclerView = binding.individualVideoScreenFragmentRecyclerView
        adapter.submitList(ResourceDummyData.videoViewAllItem as List<ResourceDetailVideoModel>)
        videoRecyclerView.adapter = adapter
        videoRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        playbackStateListener = PlaybackStateListener()
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    // interface override from ViewAllVideoRvAdapter.Interaction. This set a new url in the MediaItem exoplayer
    override fun onItemSelected(position: Int, item: ResourceDetailVideoModel) {
        mediaItem = MediaItem.fromUri(item.resourceImage)
        player?.setMediaItem(mediaItem)
        player?.play()
    }

    private fun initializePlayer() {
        if (player == null) {
            trackSelector = DefaultTrackSelector(requireContext())
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd())
            player =
                SimpleExoPlayer.Builder(requireContext()).setTrackSelector(trackSelector).build()
        }
        playerView = binding.individualVideoScreenFragmentPlayerView
        playerView.player = player
        // use media Item builder to set the video

        mediaItem = MediaItem.Builder()
            .setUri(url)
            .build()
        player!!.setMediaItem(mediaItem)
        // if there exist a saved state collect them and continue, if not, use the default innitialize at top
        player!!.playWhenReady = playerWhenReady
        player!!.seekTo(currentWindow, playBackPosition)
        player!!.addListener(playbackStateListener)
        player!!.prepare()
    }

    private fun releasePlayer() {

        player?.let { playerNotNull ->
            playerWhenReady = playerNotNull.playWhenReady
            playBackPosition = playerNotNull.currentPosition
            currentWindow = playerNotNull.currentWindowIndex
            // release the player
            playerNotNull.removeListener(playbackStateListener)
            playerNotNull.release()
            player = null
        }
    }

    inner class PlaybackStateListener : Player.EventListener {
        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
        override fun onRepeatModeChanged(repeatMode: Int) {}
        override fun onPositionDiscontinuity(reason: Int) {}
        override fun onLoadingChanged(isLoading: Boolean) {}
        override fun onSeekProcessed() {}
        override fun onPlaybackStateChanged(playbackState: Int) {
            // this is the state of the player at different instance. You can do something with it latter in future e.g as the toast below
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
