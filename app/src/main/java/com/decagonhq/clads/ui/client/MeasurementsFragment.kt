package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.decagonhq.clads.databinding.MeasurementsFragmentBinding
import com.decagonhq.clads.ui.client.adapter.AddMeasurementAdapter
import com.decagonhq.clads.util.RecyclerClickListener
import com.decagonhq.clads.ui.client.model.DressMeasurementModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigDecimal

class MeasurementsFragment : androidx.fragment.app.Fragment(), RecyclerClickListener {
    private var _binding: MeasurementsFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var addMeasurementFab: FloatingActionButton
    private lateinit var display:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = MeasurementsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentList : MutableList<DressMeasurementModel> = mutableListOf(
            DressMeasurementModel("Arm jjklkj ", BigDecimal(23)),
            DressMeasurementModel("Leg", BigDecimal(34)),
            DressMeasurementModel("Neck", BigDecimal(15)),
            DressMeasurementModel("Ankle faajj bjvj", BigDecimal(10)),
            DressMeasurementModel("Waist", BigDecimal(24)),
            DressMeasurementModel("Sholder", BigDecimal(13)))


        /*Initialize Views*/
        display = binding.measurementsFragmentTestingTextView
        addMeasurementFab = binding.clientMeasurementFragmentAddMeasurementFab
        addMeasurementFab.setOnClickListener {
//            AddMeasurementDialogFragment().show(childFragmentManager, "Dialog tag")
            setFragmentResultListener("request_key") { requestKey: String, bundle: Bundle ->
                val result = bundle.getString("your_data_key")
                // do something with the result

            }


        }


        val args = arguments
        if (args != null) {
            val measurementFragmentArgs = MeasurementsFragmentArgs.fromBundle(args)
            // parse measurementArgs
           currentList.add(0,measurementFragmentArgs.dressMeasurement)
        }

        val recyclerView = binding.measurementsFragmentRecyclerView
        recyclerView.adapter = AddMeasurementAdapter(currentList,this@MeasurementsFragment, this@MeasurementsFragment)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked1(dressMeasurementModel: DressMeasurementModel) {
        EditMeasurementDialogFragment().show(childFragmentManager, "Dialog tag")
    }

    override fun onItemClicked2(dressMeasurementModel: DressMeasurementModel) {
        Toast.makeText(requireContext(), "Position 2 was clicked", Toast.LENGTH_SHORT).show()
    }

}
