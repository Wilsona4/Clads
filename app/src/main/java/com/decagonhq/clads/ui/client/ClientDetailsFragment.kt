package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.decagonhq.clads.data.domain.client.Measurement
import com.decagonhq.clads.databinding.ClientDetailsFragmentBinding
import com.decagonhq.clads.ui.BaseFragment
import com.decagonhq.clads.ui.client.adapter.AddMeasurementAdapter
import com.decagonhq.clads.ui.client.adapter.RecyclerClickListener
import com.decagonhq.clads.util.hideView
import com.decagonhq.clads.util.showView
import java.util.Locale

class ClientDetailsFragment : BaseFragment(), RecyclerClickListener {
    private var _binding: ClientDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: ClientDetailsFragmentArgs by navArgs()
    private lateinit var clientMeasurementAdapter: AddMeasurementAdapter

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
            val clientInitials = args.clientModel?.fullName?.split(" ")?.get(0)?.substring(0, 1)
                ?.capitalize(Locale.ROOT) +
                args.clientModel?.fullName?.split(" ")?.get(1)?.substring(0, 1)
                    ?.capitalize(Locale.ROOT)

            clientsDetailsInitialsTextView.text = clientInitials
            val recyclerView = args.clientModel?.measurements?.toMutableList()

            measurementsFragmentRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            /*Toggle Empty Message View*/
            if (recyclerView.isNullOrEmpty()) {
                measurementsFragmentTestingTextView.showView()
            } else {
                measurementsFragmentTestingTextView.hideView()
                clientMeasurementAdapter =
                    AddMeasurementAdapter(
                        recyclerView,
                        this@ClientDetailsFragment,
                        this@ClientDetailsFragment
                    )
                measurementsFragmentRecyclerView.adapter = clientMeasurementAdapter
            }
        }
    }

    override fun onItemClickToEdit(position: Int, currentList: MutableList<Measurement>) {
    }

    override fun onItemClickToDelete(position: Int, currentList: MutableList<Measurement>) {
    }
}
