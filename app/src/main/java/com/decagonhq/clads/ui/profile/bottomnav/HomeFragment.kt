package com.decagonhq.clads.ui.profile.bottomnav

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagonhq.clads.R
import com.decagonhq.clads.databinding.HomeFragmentBinding
import com.decagonhq.clads.util.ClientsListModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private lateinit var clientList: ArrayList<ClientsListModel>
    private lateinit var clientsAdapter: HomeFragmentClientsRecyclerAdapter
    private lateinit var clientsRecyclerView: RecyclerView

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // val chartView =binding.aaChartView
        clientsRecyclerView = binding.homeFragmentClientListRecyclerView
        populateClient()
        clientsAdapter = HomeFragmentClientsRecyclerAdapter(clientList)
        clientsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        clientsRecyclerView.adapter = clientsAdapter

        chartData()
    }

    private fun chartData() {
        val chart = binding.lineChart
        val entries = ArrayList<String>()

        // initialise data for the xAxis
        entries.add("Jan")
        entries.add("Feb")
        entries.add("Mar")
        entries.add("Apr")
        entries.add("May")
        entries.add("Jun")
        entries.add("Jul")
        entries.add("Aug")
        entries.add("Sept")
        entries.add("Oct")
        entries.add("Nov")
        entries.add("Dec")

        /*initialise data for the yAxis*/
        val lineEntry = ArrayList<Entry>()
        lineEntry.add(Entry(30f, 0))
        lineEntry.add(Entry(50f, 1))
        lineEntry.add(Entry(40f, 2))
        lineEntry.add(Entry(35f, 3))
        lineEntry.add(Entry(40f, 4))
        lineEntry.add(Entry(35f, 5))
        lineEntry.add(Entry(49f, 6))
        lineEntry.add(Entry(45f, 7))
        lineEntry.add(Entry(49f, 8))
        lineEntry.add(Entry(40f, 9))
        lineEntry.add(Entry(55f, 10))
        lineEntry.add(Entry(65f, 11))

        val mFillColor = Color.argb(100, 0, 102, 245) // chart fill color

        /*set chart attributes*/
        val lineDataSet = LineDataSet(lineEntry, "Clients")
        lineDataSet.color = resources.getColor(R.color.chart_blue)
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawFilled(true)
        lineDataSet.lineWidth = 2f
        lineDataSet.fillColor = mFillColor
        lineDataSet.setCircleColor(Color.BLUE)
        lineDataSet.color = R.color.red
        lineDataSet.setDrawFilled(true)
        lineDataSet.setCircleColorHole(R.color.chart_blue)
        lineDataSet.fillAlpha = 58

        val data = LineData(entries, lineDataSet)

        chart.data = data
        chart.setBackgroundColor(resources.getColor(R.color.white))
        chart.animateXY(3000, 3000)
        chart.setDescription(null)
        chart.setGridBackgroundColor(R.color.deep_sky_blue)
        chart.axisRight.isEnabled = false
        chart.setTouchEnabled(false)
        chart.setDrawGridBackground(false)
        chart.setNoDataText("No forex yet!")

        /*set xAxis attributes*/
        val xAxis = chart.xAxis
        val yAxis = chart.axisLeft
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 6F
        xAxis.labelRotationAngle = -90f
        xAxis.axisLineColor = R.color.deep_sky_blue

        /*set yAxis attributes*/
        yAxis.setDrawGridLines(false)
        yAxis.axisLineColor = R.color.deep_sky_blue
        yAxis.textSize = 6f
        yAxis.mAxisMaximum = 100F
    }

    // dummy data to populate the recycler view
    private fun populateClient() {
        clientList = arrayListOf(
            ClientsListModel("Ruth", "Unoka", "Lagos"),
            ClientsListModel("Ezekiel", "Olufemi", "Benin"),
            ClientsListModel("Olufemi", "Ogundipe", "Abeokuta"),
            ClientsListModel("Adebayo", "Kings", "Lagos"),
            ClientsListModel("Abdul", "Salawu", "Benin"),
            ClientsListModel("Hope", "Omoruyi", "Abeokuta")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
