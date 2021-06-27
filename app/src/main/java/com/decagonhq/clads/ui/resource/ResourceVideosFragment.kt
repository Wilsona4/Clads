package com.decagonhq.clads.ui.resource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.data.domain.resource.ResourceDetailVideoModel
import com.decagonhq.clads.databinding.ResourceVideosFragmentBinding
import com.decagonhq.clads.ui.resource.adapter.ViewAllVideoRvAdapter
import com.decagonhq.clads.util.ResourceDummyData.videoViewAllItem

class ResourceVideosFragment : Fragment(), ViewAllVideoRvAdapter.Interaction {
    private var _binding: ResourceVideosFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var videoRecyclerView: RecyclerView
    private lateinit var videoRvAdapter: ViewAllVideoRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ResourceVideosFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Initialise View*/
        videoRecyclerView = binding.resourcesVideoFragmentVideosRecyclerView

        /*Initialise RecyclerView*/
        videoRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            videoRvAdapter = ViewAllVideoRvAdapter(this@ResourceVideosFragment)
            adapter = videoRvAdapter
            videoRvAdapter.submitList(videoViewAllItem as List<ResourceDetailVideoModel>)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(position: Int, item: ResourceDetailVideoModel) {
        val currentItem = videoViewAllItem[position]
    }
}
