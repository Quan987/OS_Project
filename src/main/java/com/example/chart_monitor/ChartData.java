package com.example.chart_monitor;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;
import java.util.Arrays;

public class ChartData {
    private final SystemInfo systemInfo;
    private long[] processorPeriod;
    private long[] ioPeriod;
    private long[] bandwidthPeriod;

    // Constructor
    public ChartData() {
        this.systemInfo = new SystemInfo();
        this.processorPeriod = systemInfo.getHardware().getProcessor().getSystemCpuLoadTicks();
        this.ioPeriod = new long[]{0, 0}; 
        this.bandwidthPeriod = new long[]{0, 0}; 
        updateIOGraph();
        updateBandWithGraph();
    }

    private void updateIOGraph() {
        long readBytes = 0;
        long writeBytes = 0;
        for (HWDiskStore disk : systemInfo.getHardware().getDiskStores()) {
            readBytes += disk.getReadBytes();
            writeBytes += disk.getWriteBytes();
        }
        ioPeriod = new long[]{readBytes, writeBytes};
    }

    private void updateBandWithGraph() {
        long bytesSent = 0;
        long bytesGet = 0;
        for (NetworkIF bandWidth : systemInfo.getHardware().getNetworkIFs()) {
            bytesSent += bandWidth.getBytesSent();
            bytesGet += bandWidth.getBytesRecv();
        }
        bandwidthPeriod = new long[]{bytesSent, bytesGet};
    }

    public double getSystemCpuLoad() {
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] ticks = processor.getSystemCpuLoadTicks();
        double cpuTime = processor.getSystemCpuLoadBetweenTicks(processorPeriod);
        processorPeriod = Arrays.copyOf(ticks, ticks.length);
        return cpuTime * 100; 
    }

    public long getAvailableMemory() {
        GlobalMemory getMemory = systemInfo.getHardware().getMemory();
        return getMemory.getAvailable();
    }

    public long getTotalMemory() {
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        return memory.getTotal();
    }

    public long[] getDiskIO() {
        long[] current = new long[2]; 
        for (HWDiskStore disk : systemInfo.getHardware().getDiskStores()) {
            current[0] += disk.getReadBytes();
            current[1] += disk.getWriteBytes();
        }
        long[] ioBytes = new long[]{current[0] - ioPeriod[0], current[1] - ioPeriod[1]};
        updateIOGraph(); 
        return ioBytes; 
    }

    public long[] getBandWidthIO() {
        long[] current = new long[2]; 
        for (NetworkIF bandWidth : systemInfo.getHardware().getNetworkIFs()) {
            current[0] += bandWidth.getBytesSent();
            current[1] += bandWidth.getBytesRecv();
        }
        long[] bandWidthGetByte = new long[]{current[0] - bandwidthPeriod[0], current[1] - bandwidthPeriod[1]};
        updateBandWithGraph(); 
        return bandWidthGetByte; 
    }

    
}

