package com.example.chart_function;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import javax.swing.*;
import java.awt.*;

public class CpuMemoryChart extends JFrame {
    private final TimeSeries cpuLoadTimeSeries = new TimeSeries("CPU Load");
    private final TimeSeries memoryLoadTimeSeries = new TimeSeries("Memory Usage");

    public CpuMemoryChart(String title) {
        super(title);
        TimeSeriesCollection dataPeriod = new TimeSeriesCollection();
        dataPeriod.addSeries(cpuLoadTimeSeries);
        dataPeriod.addSeries(memoryLoadTimeSeries);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title, "Time (s)", "Percentage (%)", dataPeriod, true, true, false);

        XYPlot coordinate = chart.getXYPlot();
        NumberAxis chartRange = (NumberAxis) coordinate.getRangeAxis();
        chartRange.setRange(0.0, 100.0); 

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(chartPanel);
    }

    public void updateCpuLoad(double load) {
        this.cpuLoadTimeSeries.addOrUpdate(new Second(), load);
    }

    public void updateMemoryLoad(double usage) {
        this.memoryLoadTimeSeries.addOrUpdate(new Second(), usage);
    }
}
