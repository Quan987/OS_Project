package com.example;
import com.example.chart_monitor.SystemResourceMonitor;

public class Main {
    public static void main(String[] args) {
        SystemResourceMonitor monitor = new SystemResourceMonitor();
        monitor.start();
        System.out.println("App Running");
    }
}
