package com.eto.binding_usage_project.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eto.binding_usage_project.databinding.FragmentHorizontalBarChartBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class HorizontalBarChart : Fragment() {


    private val profitValue = ArrayList<BarEntry>()

    private lateinit var binding: FragmentHorizontalBarChartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHorizontalBarChartBinding.inflate(inflater, container, false)
        dataListing()
        return binding.root
    }

    private fun dataListing() {
        profitValue.add(BarEntry(1f, 10f))
        profitValue.add(BarEntry(2f, 20f))
        profitValue.add(BarEntry(3f, 33.5f))
        profitValue.add(BarEntry(4f, 5f))
        profitValue.add(BarEntry(5f, 29.5f))
        setChart()
    }


    private fun setChart() {
        val barChart = binding.horizontalBarChart
        barChart.description.isEnabled = false
        barChart.setPinchZoom(true)
        barChart.setDrawGridBackground(false)

        barChart.axisLeft.setDrawLabels(true)
        val xAxis = binding.horizontalBarChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = false

        xAxis.valueFormatter = IndexAxisValueFormatter(
            arrayListOf("Q1", "Sales", "Profit", "Revenue", "Growth", "Returns")
        )
        val rightAxis = barChart.axisLeft
        rightAxis.setDrawLabels(false)
        rightAxis.setDrawAxisLine(false)


        val data = barChart.data
        val barDataSetter: BarDataSet

        if (data != null && data.dataSetCount > 0) {
            binding.xAxisTitle.visibility = View.GONE
            binding.yAxisTitle.visibility = View.GONE
            barDataSetter = binding.horizontalBarChart.data.getDataSetByIndex(0) as BarDataSet
            barDataSetter.values = profitValue
            barChart.data.notifyDataChanged()
            barChart.notifyDataSetChanged()
        } else {
            barDataSetter = BarDataSet(profitValue, "Data Set")
            barDataSetter.isHighlightEnabled = true
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
            legend.isEnabled = false
            legend.verticalAlignment =
                com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment =
                com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
            legend.orientation =
                com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(false)
            legend.yOffset = 2f
            barChart.invalidate()

        }
    }

}