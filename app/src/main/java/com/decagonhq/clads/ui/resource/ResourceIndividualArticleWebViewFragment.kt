package com.decagonhq.clads.ui.resource

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.FragmentResourceViewIndividualArticleWebviewBinding

class ResourceIndividualArticleWebViewFragment : Fragment() {

    private var _binding: FragmentResourceViewIndividualArticleWebviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResourceViewIndividualArticleWebviewBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleLink = arguments?.getString(getString(R.string.resource_view_individual_article_fragment_article_link_key))
        webView = binding.resourceViewIndividualArticleFragmentIndividualArticleWebview
        webView.webViewClient = object :
            WebViewClient() {}
        if (articleLink != null) {
            webView.loadUrl("https://www.google.com")
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                isEnabled = false
                view.findNavController().navigateUp()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
