package com.eto.binding_usage_project.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eto.binding_usage_project.databinding.FragmentLineChartBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class LineChart : Fragment() {
    private lateinit var binding: FragmentLineChartBinding

    private val profitValue = ArrayList<Entry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLineChartBinding.inflate(inflater, container, false)
        dataListing()
        return binding.root
    }

    private fun dataListing() {
        profitValue.clear()
        profitValue.add(Entry(0f, 10f))
        profitValue.add(Entry(1f, 20f))
        profitValue.add(Entry(2f, 33.5f))
        profitValue.add(Entry(3f, 5f))
        profitValue.add(Entry(4f, 29.5f))
        setChart()
    }

    private fun setChart() {
        val lineChart = binding.lineChart
        lineChart.description.isEnabled = false
        lineChart.setPinchZoom(true)
        lineChart.setDrawGridBackground(false)
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.valueFormatter = IndexAxisValueFormatter(
            arrayListOf("Turnover", "Sales", "Profit", "Revenue", "Growth")
        )
        val leftAxis = lineChart.axisLeft
        leftAxis.setDrawLabels(true)
        val rightAxis = lineChart.axisRight
        rightAxis.isEnabled = false
        val dataSet = LineDataSet(profitValue, "Performance")
        dataSet.setColors(Color.RED)
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 4f
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawValues(true)
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()
    }
}
