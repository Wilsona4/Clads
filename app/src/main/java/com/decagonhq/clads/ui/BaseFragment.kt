package com.decagonhq.clads.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.decagonhq.clads.util.Constants.IMAGE_RETROFIT
import com.decagonhq.clads.util.Constants.MAIN_RETROFIT
import com.decagonhq.clads.util.CustomProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    lateinit var progressDialog: CustomProgressDialog
    @Inject
    @Named(MAIN_RETROFIT)
    lateinit var mainRetrofit: Retrofit

    @Inject
    @Named(IMAGE_RETROFIT)
    lateinit var imageRetrofit: Retrofit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = CustomProgressDialog(requireContext())
    }
}
