package com.decagonhq.clads.ui.resource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.resource.ResourceGeneralArticleModel
import com.decagonhq.clads.databinding.ResourceArticlesFragmentBinding
import com.decagonhq.clads.ui.resource.adapter.ViewAllArticleRvAdapter
import com.decagonhq.clads.util.ResourceDummyData.articleItem

class ResourceArticlesFragment :
    Fragment(),
    ViewAllArticleRvAdapter.Interaction {
    private var _binding: ResourceArticlesFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleRvAdapter: ViewAllArticleRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ResourceArticlesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleRecyclerView = binding.resourcesArticleFragmentArticlesRecyclerView

        articleRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            articleRvAdapter = ViewAllArticleRvAdapter(this@ResourceArticlesFragment)
            adapter = articleRvAdapter
            articleRvAdapter.submitList(articleItem as List<ResourceGeneralArticleModel>)
        }
    }

    override fun onItemSelected(position: Int, item: ResourceGeneralArticleModel) {
        val itemBundle =
            bundleOf(getString(R.string.resource_view_individual_article_fragment_article_link_key) to item.articleTitle)

        view?.findNavController()?.navigate(
            R.id.action_resourceArticlesFragment_to_resourceViewIndividualArticleFragment,
            itemBundle
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
