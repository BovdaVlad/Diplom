import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.chartpart.Chart;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class MySwingWrapper extends SwingWrapper <XYChart> {

    public MySwingWrapper(XYChart chart) {
        super(chart);
    }


    public JFrame displayChart() {

        // Create and set up the window.
        final JFrame frame = new JFrame("dds");

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run() {

                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                   // XChartPanel<XYChart> chartPanel = new XChartPanel<XYChart>(charts.get(0));
                    //() chartPanels.add(chartPanel);
                  //  frame.add(chartPanel);

                    // Display the window.
                    frame.setName("Динаміка системи");
                    frame.pack();
                    frame.setVisible(true);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return frame;
    }

}
