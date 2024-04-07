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

public class IOBandwidthChart extends JFrame {
    private final TimeSeries diskIOTimeSeries = new TimeSeries("Disk I/O (KB/s)");
    private final TimeSeries bandWidthTimeSeries = new TimeSeries("Network Bandwidth (KB/s)");

    public IOBandwidthChart(String title) {
        super(title);
        TimeSeriesCollection dataPeriod = new TimeSeriesCollection();
        dataPeriod.addSeries(diskIOTimeSeries);
        dataPeriod.addSeries(bandWidthTimeSeries);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title, "Time (s)", "Kilobyte (KB)", dataPeriod, true, true, false);

        XYPlot coordinate = chart.getXYPlot();
        NumberAxis chartRange = (NumberAxis) coordinate.getRangeAxis();
        chartRange.setRange(0.0, 5000.0); 

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(chartPanel);
    }

    public void updateDiskIO(long readPerSecond, long writePerSecond) {
        double readWrite = (readPerSecond + writePerSecond) / 1024.0; 
        this.diskIOTimeSeries.addOrUpdate(new Second(), readWrite);
    }

    public void updateBandWidthData(long sendPerSecond, long getPerSecond) {
        double readWrite = (sendPerSecond + getPerSecond) / 1024.0; 
        this.bandWidthTimeSeries.addOrUpdate(new Second(), readWrite);
    }
}
