package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.decagonhq.clads.databinding.ClientDetailsFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.media.MediaFragmentPhotoNameArgs
import com.decagonhq.clads.viewmodels.ClientViewModel
import kotlinx.coroutines.flow.forEach
import java.sql.ClientInfoStatus
import java.util.*

class ClientDetailsFragment : BaseFragment() {
    private var _binding: ClientDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: ClientDetailsFragmentArgs by navArgs()
    private val clientViewModel: ClientViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ClientDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            clientDetailsFragmentClientNameTextView.text = args.clientModel?.fullName
            clientDetailsFragmentNumberTextView.text = args.clientModel?.phoneNumber
            clientDetailsFragmentEmailTextView.text = args.clientModel?.email
            clientDetailsFragmentGenderItemTextView.text = args.clientModel?.gender
            val address = args.clientModel?.deliveryAddresses?.firstOrNull()
            address?.let {
                clientDetailsFragmentDeliveryAddressItemTextView.text =
                    "${it.street?.capitalize()} ~ ${it.city?.capitalize()} ~ ${it.state}"
            }
            val clientInitials = args.clientModel?.fullName?.split(" ")?.get(0)?.substring(0, 1)?.capitalize(Locale.ROOT) +
                    args.clientModel?.fullName?.split(" ")?.get(1)?.substring(0, 1)?.capitalize(Locale.ROOT)
            clientsDetailsInitialsTextView.text = clientInitials


        }


    }

}


