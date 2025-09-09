package com.eto.binding_usage_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eto.binding_usage_project.databinding.FragmentBarChartBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class BarChart : Fragment() {

    private lateinit var binding: FragmentBarChartBinding

    val profitValue = ArrayList<BarEntry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBarChartBinding.inflate(layoutInflater)

        dataListing()
        return binding.root
    }

    private fun dataListing() {
        profitValue.add(BarEntry(1f, 10f))
        profitValue.add(BarEntry(2f, 20f))
        profitValue.add(BarEntry(3f, 33.5f))
        profitValue.add(BarEntry(4f, 5f))
        profitValue.add(BarEntry(5f, 45.8f))
        setChart()
    }


    private fun setChart() {
        val barChart = binding.barChart
        barChart.description.isEnabled = false
        barChart.axisRight.isEnabled = true
        barChart.setMaxVisibleValueCount(50)
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(false)
        barChart.setDrawBarShadow(false)
        barChart.axisLeft.setDrawLabels(true)
        val xAxis = binding.barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        xAxis.valueFormatter = IndexAxisValueFormatter(
            arrayListOf("Q1", "Sales", "Profit", "Revenue", "Growth", "")
        )

        val rightAxis = barChart.axisRight
        rightAxis.setDrawLabels(false)
        rightAxis.setDrawAxisLine(false)


        val data = barChart.data
        val barDataSetter: BarDataSet

        if (data != null && data.dataSetCount > 0) {
            barDataSetter = binding.barChart.data.getDataSetByIndex(0) as BarDataSet
            barDataSetter.values = profitValue
            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()
        } else {
            barDataSetter = BarDataSet(profitValue, "Data Set")
            barDataSetter.isHighlightEnabled = false
            barDataSetter.setColors(*ColorTemplate.MATERIAL_COLORS)
            barDataSetter.setDrawValues(true)
            val dataSet = ArrayList<IBarDataSet>()
            dataSet.add(barDataSetter)
            val barData = BarData(dataSet)
            barData.setValueTextColor(ColorTemplate.COLORFUL_COLORS[0])
            barData.setValueTextSize(10f)
            barChart.data = barData
            barChart.setFitBars(true)

            val legend = barChart.legend
            legend.isEnabled = true
            legend.verticalAlignment =
                com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment =
                com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
            legend.orientation =
                com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(false) // make sure it’s outside the chart area
            legend.yOffset = 10f
            barChart.invalidate()

        }
    }

}