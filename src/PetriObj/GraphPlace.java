package PetriObj;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;
import test.ExampleChart;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GraphPlace implements ExampleChart<XYChart>,Runnable
{

    private ArrayList<String> name_place;
    private int graph [][];
    private String name;
    private ArrayList<Integer> indexplace;
    public GraphPlace(int graph[][], ArrayList<String> name_place,ArrayList<Integer> indexplace, String name)
    {
        this.name_place=name_place;
        this.graph = graph;
        this.name=name;
        this.indexplace = indexplace;

    }




    public XYChart getChart() {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(name).xAxisTitle("Time").yAxisTitle("Amount").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Centre);
        chart.getStyler().setYAxisDecimalPattern(" #,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);
        // Series
        // @formatter:off
        int time = graph[0].length;
        int[] xAges = new int[time];

        for (int i = 0; i < time; i++) {
            xAges[i] = i + 1;

        }


        XYSeries seriesLiability = chart.addSeries("Place Statistic", xAges, graph[0]);
        seriesLiability.setMarker(SeriesMarkers.PLUS);
        seriesLiability.setMarkerColor(Color.YELLOW);

        for (int j = 0; j < indexplace.size(); j++) {
            addgraph(chart,xAges,indexplace.get(j));
        }

        return chart;
    }

    private void addgraph(XYChart chart,int[] xAges,int index)
    {
        try {
            chart.addSeries(name_place.get(index), xAges, graph[index]);
        } catch (Exception ex) {
            name_place.set(index, name_place.get(index) + " ");
            addgraph(chart,xAges,index);
        }
    }

    @Override
    public void run() {
        XYChart chart = (XYChart)this.getChart();
        SwingWrapper swingWrapper = new SwingWrapper(chart);
        JFrame frame = swingWrapper.displayChart();
        //JScrollPane scrollPane = new JScrollPane();
       // scrollPane.add(frame);
        frame.setTitle("Динаміка системи");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                Thread.currentThread().interrupt();
            }
        });
    }

}

