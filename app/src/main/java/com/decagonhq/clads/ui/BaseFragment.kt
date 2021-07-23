package com.decagonhq.clads.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.util.Constants.IMAGE_RETROFIT
import com.decagonhq.clads.util.Constants.MAIN_RETROFIT
import com.decagonhq.clads.util.CustomProgressDialog
import com.decagonhq.clads.util.SessionManager
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

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var database: CladsDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = CustomProgressDialog(requireContext())
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
