package com.decagonhq.clads.ui.client

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.decagonhq.clads.R
import com.decagonhq.clads.data.domain.DressMeasurementModel
import com.decagonhq.clads.databinding.MeasurementsFragmentBinding
import com.decagonhq.clads.ui.client.adapter.AddMeasurementAdapter
import com.decagonhq.clads.ui.client.adapter.RecyclerClickListener
import com.decagonhq.clads.ui.client.dialogfragment.ClientManagementDialogFragments.Companion.createClientDialogFragment
import com.decagonhq.clads.util.ClientMeasurementData
import com.decagonhq.clads.util.ClientMeasurementData.currentList
import com.decagonhq.clads.viewmodels.ClientsRegisterViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MeasurementsFragment : Fragment(), RecyclerClickListener {
    private var _binding: MeasurementsFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val clientsRegisterViewModel: ClientsRegisterViewModel by activityViewModels()

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
        init()
        setEventListeners()
        setObserver()

//        /*Adding client measurement*/
//        addClientMeasurement()
//
//        /*Edit client measurement*/
//        editClientMeasurement()
//
//        /*Open dialog fragment*/



    }

//    private fun editClientMeasurement() {
//        childFragmentManager.setFragmentResultListener(
//            EDITED_MEASUREMENT_REQUEST_KEY,
//            requireActivity()
//        ) { key, bundle ->
//            myAdapter.notifyDataSetChanged()
//        }
//    }
//
////    /*Adding client measurement*/
//    private fun addClientMeasurement() {
//        childFragmentManager.setFragmentResultListener(
//            ADD_MEASUREMENT_REQUEST_KEY,
//            requireActivity()
//        ) { key, bundle ->
//            val editTextString =
//                bundle.getParcelable<DressMeasurementModel>(ADD_MEASUREMENT_BUNDLE_KEY)
//            // Do something with the string
//            currentList.add(0, editTextString!!)
//            listMessageDisplay.visibility = View.GONE
//            myAdapter.notifyDataSetChanged()
//        }
//    }

    override fun onItemClickToEdit(position: Int, currentList: MutableList<DressMeasurementModel>) {
        val data = DressMeasurementModel(
//            currentList[position].measurementName,
//            currentList[position].measurement
            currentList[position].title,
            currentList[position].value
        )
        val bundle = bundleOf(
            EDIT_MEASUREMENT_BUNDLE_KEY to data,
            EDIT_MEASUREMENT_BUNDLE_POSITION to position
        )

        createClientDialogFragment(R.layout.edit_measurement_dialog_fragment, bundle)
            .show(childFragmentManager, MeasurementsFragment::class.simpleName)
    }

    override fun onItemClickToDelete(
        position: Int,
        currentList: MutableList<DressMeasurementModel>
    ) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.delete_measurement)) // for set Title
        alertDialog.setMessage(getString(R.string.do_you_want_to_delete_measurement)) // for Message
        alertDialog.setIcon(R.drawable.ic_baseline_delete_forever_24) // for alert icon

        alertDialog.setPositiveButton(getText(R.string.dialog_alert_confirmation_yes)) { dialog, id ->
            // set your desired action here.
//            currentList.remove(
//                DressMeasurementModel(
////                    currentList[position].measurementName,
////                    currentList[position].measurement
//                    currentList[position].title,
//                    currentList[position].value
//                )
//            )
            myAdapter.deleteMeasurement(position)
            myAdapter.notifyDataSetChanged()

            if (ClientMeasurementData.currentList.isEmpty()) {
                listMessageDisplay.visibility = View.VISIBLE
            }

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
        negativeButton.setTextColor(resources.getColor(R.color.navy_blue))

        val positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton.setTextColor(resources.getColor(R.color.navy_blue))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
//        const val ADD_MEASUREMENT_REQUEST_KEY = "ADD CLIENT MEASUREMENT REQUEST KEY"
//        const val ADD_MEASUREMENT_BUNDLE_KEY = "ADD CLIENT MEASUREMENT BUNDLE KEY"

        const val EDIT_MEASUREMENT_BUNDLE_KEY = "EDIT CLIENT MEASUREMENT BUNDLE KEY"
        const val EDIT_MEASUREMENT_BUNDLE_POSITION = "EDIT CLIENT MEASUREMENT BUNDLE POSITION"

        const val EDITED_MEASUREMENT_REQUEST_KEY = "EDITED CLIENT MEASUREMENT REQUEST KEY"
        const val EDITED_MEASUREMENT_BUNDLE_KEY = "EDITED CLIENT MEASUREMENT BUNDLE KEY"
    }


    private fun setObserver(){

        clientsRegisterViewModel.measurementData.observe(viewLifecycleOwner) {
            myAdapter.updateList(it.toMutableList())
            myAdapter.notifyDataSetChanged()
        }
    }


    private fun init(){
        listMessageDisplay = binding.measurementsFragmentTestingTextView
        addMeasurementFab = binding.clientMeasurementFragmentAddMeasurementFab
        recyclerView = binding.measurementsFragmentRecyclerView
        listMessageDisplay.visibility = View.VISIBLE
        myAdapter = AddMeasurementAdapter(currentList, this@MeasurementsFragment, this@MeasurementsFragment)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    }


    private fun setEventListeners(){
        addMeasurementFab.setOnClickListener {
            createClientDialogFragment(R.layout.add_measurement_dialog_fragment)
                .show(childFragmentManager, MeasurementsFragment::class.simpleName)
        }
    }
}
