package com.decagonhq.clads.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.decagonhq.clads.util.CustomProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    lateinit var progressDialog: CustomProgressDialog
    @Inject
    lateinit var retrofit: Retrofit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = CustomProgressDialog(requireContext())
    }
}
