package com.blogspot.tsprates.kmeans;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.ChartBuilder_XY;
import org.knowm.xchart.Chart_XY;
import org.knowm.xchart.Series_XY;
import org.knowm.xchart.Styler_XY;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.internal.style.Styler;

public class Chart
{

    private final List<List<Double>> x = new ArrayList<List<Double>>();
    
    private final List<List<Double>> y = new ArrayList<List<Double>>();
    
    private final List<Double> cx = new ArrayList<Double>();
    
    private final List<Double> cy = new ArrayList<Double>();
    
    private final Chart_XY chart;

    /**
     * Constructor.
     * 
     * @param data
     * @param center
     * @param dataCluster
     * @param numCluster 
     */
    public Chart(double[][] data, double[][] center, int[] dataCluster,
            int numCluster)
    {
        chart = new ChartBuilder_XY().width(800).height(600).build();

        setupStyle();

        for (int i = 0; i < numCluster; i++)
        {
            x.add(new ArrayList<Double>());
            y.add(new ArrayList<Double>());
        }

        
        for (int i = 0; i < data.length; i++)
        {
            x.get(dataCluster[i]).add(data[i][0]);
            y.get(dataCluster[i]).add(data[i][1]);
        }
        
        for (int i = 0; i < numCluster; i++)
        {
            cx.add(center[i][0]);
            cy.add(center[i][1]);
        }
        

        for (int i = 0; i < numCluster; i++)
        {
            chart.addSeries("Cluster " + (i + 1), x.get(i), y.get(i));
        }

        chart.addSeries("Centers", cx, cy);
    }

    private void setupStyle()
    {
        Styler_XY syler = chart.getStyler();
        syler.setDefaultSeriesRenderStyle(Series_XY.ChartXYSeriesRenderStyle.Scatter);
        syler.setChartTitleVisible(false);
        syler.setLegendPosition(Styler.LegendPosition.InsideSW);
        syler.setMarkerSize(16);
    }

    public void show()
    {
        new SwingWrapper(chart).displayChart();
    }
}
