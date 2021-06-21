package com.decagonhq.clads.ui.resource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.data.domain.resource.ResourceGeneralArticleModel
import com.decagonhq.clads.databinding.ResourceArticlesFragmentBinding
import com.decagonhq.clads.ui.resource.adapter.GeneralArticleRvAdapter
import com.decagonhq.clads.util.ResourceDummyData.articleItem

class ResourceArticlesFragment : Fragment(), GeneralArticleRvAdapter.Interaction {
    private var _binding: ResourceArticlesFragmentBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleRvAdapter: GeneralArticleRvAdapter

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
            layoutManager = GridLayoutManager(requireContext(), 3)
            articleRvAdapter = GeneralArticleRvAdapter(this@ResourceArticlesFragment)
            adapter = articleRvAdapter
            articleRvAdapter.submitList(articleItem as List<ResourceGeneralArticleModel>)
        }
    }

    override fun onItemSelected(position: Int, item: ResourceGeneralArticleModel) {
        val currentItem = articleItem[position]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
