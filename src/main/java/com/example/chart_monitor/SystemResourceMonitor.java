package com.example.chart_monitor;

import com.example.chart_function.CpuMemoryChart;
import com.example.chart_function.IOBandwidthChart;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class SystemResourceMonitor {

    private final ChartData monitorChartData = new ChartData();
    private final CpuMemoryChart cpuMemoryChart = new CpuMemoryChart("CPU and Memory Chart");
    private final IOBandwidthChart ioBandwidthChart = new IOBandwidthChart("I/O and Bandwidth Chart");

    public void start() {
        
        displayChart();

        // Schedule a task to update data periodically
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateCharts();
            }
        }, 0, 500); // update every 1/2 second
    }

    private void displayChart() {
        cpuMemoryChart.pack();
        createNewScreen(cpuMemoryChart);
        cpuMemoryChart.setVisible(true);

        ioBandwidthChart.pack();
        createNewScreen(ioBandwidthChart);
        ioBandwidthChart.setVisible(true);
    }

    private void updateCharts() {
        // CPU updates
        double cpuLoad = monitorChartData.getSystemCpuLoad();
        cpuMemoryChart.updateCpuLoad(cpuLoad);

        // Memory updates
        long totalMemory = monitorChartData.getTotalMemory();
        long availableMemory = monitorChartData.getAvailableMemory();
        long usedMemory = totalMemory - availableMemory;
        double memoryUsagePercentage = ((double) usedMemory / totalMemory) * 100;
        cpuMemoryChart.updateMemoryLoad(memoryUsagePercentage);

        // I/O updates
        long[] diskIO = monitorChartData.getDiskIO();
        ioBandwidthChart.updateDiskIO(diskIO[0], diskIO[1]);

        // Bandwidth updates
        long[] bandWidthData = monitorChartData.getBandWidthIO();
        ioBandwidthChart.updateBandWidthData(bandWidthData[0], bandWidthData[1]);
    }

    private static void createNewScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }
}

