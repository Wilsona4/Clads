package com.decagonhq.clads.util

import android.graphics.Color
import android.view.View
import com.decagonhq.clads.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

object ChartData {

    fun chartData(view: View) {
        val chart = view.findViewById<LineChart>(R.id.home_fragment_clients_line_chart)
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
        lineDataSet.color = R.color.chart_blue
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
        // chart.setBackgroundColor(resources.getColor(R.color.white))
        chart.animateXY(3000, 3000)
        chart.setDescription(null)
        chart.setGridBackgroundColor(R.color.deep_sky_blue)
        chart.axisRight.isEnabled = false
        chart.setTouchEnabled(false)
        chart.setDrawGridBackground(false)
        chart.setNoDataText("No data yet!")

        /*set xAxis attributes*/
        val xAxis = chart.xAxis
        val yAxis = chart.axisLeft
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 6f
        xAxis.labelRotationAngle = -90f
        xAxis.axisLineColor = R.color.deep_sky_blue

        /*set yAxis attributes*/
        yAxis.setDrawGridLines(false)
        yAxis.axisLineColor = R.color.deep_sky_blue
        yAxis.textSize = 6f
        yAxis.mAxisMaximum = 100F
    }
}
