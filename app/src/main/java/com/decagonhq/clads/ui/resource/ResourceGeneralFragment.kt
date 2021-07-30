package com.decagonhq.clads.ui.resource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.resource.ResourceGeneralArticleModel
import com.decagonhq.clads.data.domain.resource.ResourceGeneralVideoModel
import com.decagonhq.clads.databinding.ResourceGeneralFragmentBinding
import com.decagonhq.clads.ui.resource.adapter.GeneralArticleRvAdapter
import com.decagonhq.clads.ui.resource.adapter.GeneralVideoRvAdapter
import com.decagonhq.clads.util.ResourceDummyData.articleItem
import com.decagonhq.clads.util.ResourceDummyData.videoItem

class ResourceGeneralFragment :
    Fragment(),
    GeneralVideoRvAdapter.Interaction,
    GeneralArticleRvAdapter.Interaction {
    private var _binding: ResourceGeneralFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var videoRecyclerView: RecyclerView
    private lateinit var videoRvAdapter: GeneralVideoRvAdapter

    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleRvAdapter: GeneralArticleRvAdapter

    private lateinit var viewAllVideos: TextView
    private lateinit var viewAllArticles: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ResourceGeneralFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Initialise View*/
        articleRecyclerView = binding.resourcesGeneralFragmentImageArticlePublicationRecyclerView
        videoRecyclerView = binding.resourcesGeneralFragmentVideosRecyclerView
        viewAllArticles = binding.resourcesGeneralViewAllArticlePublicationTextView
        viewAllVideos = binding.resourcesGeneralViewAllVideoTextView

        /*Initialise RecyclerView*/
        videoRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            videoRvAdapter = GeneralVideoRvAdapter(this@ResourceGeneralFragment)
            adapter = videoRvAdapter
            videoRvAdapter.submitList(videoItem as List<ResourceGeneralVideoModel>)
        }

        articleRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            articleRvAdapter = GeneralArticleRvAdapter(this@ResourceGeneralFragment)
            adapter = articleRvAdapter
            articleRvAdapter.submitList(articleItem as List<ResourceGeneralArticleModel>)
        }

        viewAllVideos.setOnClickListener {
            findNavController().navigate(R.id.resourceVideosFragment)
        }

        viewAllArticles.setOnClickListener {
            val action = ResourceGeneralFragmentDirections.actionResourceGeneralFragmentToResourceArticlesFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(position: Int, item: ResourceGeneralVideoModel) {
        val currentItem = videoItem[position]
    }

    override fun onItemSelected(position: Int, item: ResourceGeneralArticleModel) {
        val currentItem = articleItem[position]

        val itemBundle =
            bundleOf(getString(R.string.resource_view_individual_article_fragment_article_link_key) to item.articleTitle)

        view?.findNavController()?.navigate(
            R.id.resourceViewIndividualArticleFragment,
            itemBundle
        )
    }
}
