package com.decagonhq.clads.ui.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.decagonhq.clads.databinding.MeasurementsFragmentBinding
import com.decagonhq.clads.ui.client.adapter.AddMeasurementAdapter
import com.decagonhq.clads.ui.client.model.DressMeasurementModel
import com.decagonhq.clads.util.RecyclerClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigDecimal
import kotlin.properties.Delegates

class MeasurementsFragment : Fragment(), RecyclerClickListener {
    private var _binding: MeasurementsFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var addMeasurementFab: FloatingActionButton
    private lateinit var display:TextView
    private lateinit var myAdapter: AddMeasurementAdapter
    private var editPosition by Delegates.notNull<Int>()
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

        //Temporary list
        val currentList : MutableList<DressMeasurementModel> = mutableListOf(
            DressMeasurementModel("Arm jjklkj ", BigDecimal(23)),
            DressMeasurementModel("Leg", BigDecimal(34)),
            DressMeasurementModel("Neck", BigDecimal(15)),
            DressMeasurementModel("Ankle faajj", BigDecimal(10)),
            DressMeasurementModel("Waist", BigDecimal(24)),
            DressMeasurementModel("Sholder", BigDecimal(13)))


        /*Open dialog fragment*/
        display = binding.measurementsFragmentTestingTextView
        addMeasurementFab = binding.clientMeasurementFragmentAddMeasurementFab
        addMeasurementFab.setOnClickListener {
            AddMeasurementDialogFragment().show(childFragmentManager, "Dialog tag")
        }


        //Adding new measurement
        val args = arguments
        if (args != null) {
            val measurementFragmentArgs = MeasurementsFragmentArgs.fromBundle(args)
            // parse measurementArgs
           currentList.add(0, measurementFragmentArgs.dressMeasurement)
        }

        //Attaching the recyclerview
        val recyclerView = binding.measurementsFragmentRecyclerView
        myAdapter = AddMeasurementAdapter(currentList,this@MeasurementsFragment, this@MeasurementsFragment)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        childFragmentManager.setFragmentResultListener("keyClicked", requireActivity()) { key, bundle ->
            val editTextString = bundle.getParcelable<DressMeasurementModel>("bundleKey")
            // Do something with the string
            currentList.add(0, editTextString!!)
            myAdapter.notifyDataSetChanged()
        }

        childFragmentManager.setFragmentResultListener("keyClicked2", requireActivity()) { key, bundle ->
            val editTextString = bundle.getParcelable<DressMeasurementModel>("editedData")
            val position = bundle.getInt("position")
            // Do something with the string
            currentList.add(position, editTextString!!)
            myAdapter.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClickToEdit(position: Int, currentList: MutableList<DressMeasurementModel>) {
        val data = DressMeasurementModel(currentList[position].measurementName, currentList[position].measurement)
        val bundle = bundleOf(
            "editData" to data,
            "position" to position
        )
        EditMeasurementDialogFragment(bundle).show(childFragmentManager, "Dialog tag")
    }

    override fun onItemClickToDelete(position: Int, currentList: MutableList<DressMeasurementModel>) {
        Toast.makeText(requireContext(), "You clicked $position", Toast.LENGTH_SHORT).show()
    }

}








