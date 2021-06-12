package com.decagonhq.clads.ui.client

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.MeasurementsFragmentBinding
import com.decagonhq.clads.ui.client.adapter.AddMeasurementAdapter
import com.decagonhq.clads.ui.client.model.DressMeasurementModel
import com.decagonhq.clads.util.ClientMeasurementData.currentList
import com.decagonhq.clads.util.RecyclerClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MeasurementsFragment : Fragment(), RecyclerClickListener {
    private var _binding: MeasurementsFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var addMeasurementFab: FloatingActionButton
    private lateinit var listMessageDisplay: TextView
    private lateinit var myAdapter: AddMeasurementAdapter
    private lateinit var recyclerView: RecyclerView
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

        /*Adding client measurement*/
        addClientMeasurement()

        /*Edit client measurement*/
        editClientMeasurement()

        /*Open dialog fragment*/
        listMessageDisplay = binding.measurementsFragmentTestingTextView
        addMeasurementFab = binding.clientMeasurementFragmentAddMeasurementFab
        recyclerView = binding.measurementsFragmentRecyclerView

        addMeasurementFab.setOnClickListener {
            AddMeasurementDialogFragment().show(childFragmentManager, getString(R.string.tag))
        }

        myAdapter = AddMeasurementAdapter(currentList, this@MeasurementsFragment, this@MeasurementsFragment)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun editClientMeasurement() {
        childFragmentManager.setFragmentResultListener(getString(R.string.request_key_keyClicked2), requireActivity()) { key, bundle ->
            myAdapter.notifyDataSetChanged()
        }
    }

    /*Adding client measurement*/
    private fun addClientMeasurement() {
        childFragmentManager.setFragmentResultListener(getString(R.string.request_key_keyClicked), requireActivity()) { key, bundle ->
            val editTextString = bundle.getParcelable<DressMeasurementModel>(getString(R.string.key_bundleKey))
            // Do something with the string
            currentList.add(0, editTextString!!)
            myAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClickToEdit(position: Int, currentList: MutableList<DressMeasurementModel>) {
        val data = DressMeasurementModel(currentList[position].measurementName, currentList[position].measurement)
        val bundle = bundleOf(getString(R.string.key_editedData) to data, getString(R.string.key_position) to position)
        EditMeasurementDialogFragment(bundle).show(childFragmentManager, getString(R.string.tag))
    }

    override fun onItemClickToDelete(position: Int, currentList: MutableList<DressMeasurementModel>) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.delete_measurement)) // for set Title
        alertDialog.setMessage(getString(R.string.are_you_sure) + " \n " + getString(R.string.do_you_want_to_delete_measurement)) // for Message
        alertDialog.setIcon(R.drawable.ic_baseline_delete_forever_24) // for alert icon
        alertDialog.setPositiveButton(getText(R.string.dialog_alert_confirmation_yes)) { dialog, id ->
            // set your desired action here.
            currentList.remove(DressMeasurementModel(currentList[position].measurementName, currentList[position].measurement))
            myAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        alertDialog.setNegativeButton(getText(R.string.dialog_alert_confirmation_cancle)) { dialog, id ->
            // set your desired action here.
            dialog.cancel()
        }
        val alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        val negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
        negativeButton.setTextColor(Color.RED)

        val positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton.setTextColor(Color.BLUE)
    }
}
