import graphnet.GraphPetriNet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PetriNetsPanelInactive extends JPanel{

    private GraphPetriNet graphNet;

    PetriNetsPanelInactive()
    {
        this.setBackground(Color.WHITE);
        setFocusable(true);
        //setBackground(new Color(229, 229, 229));
        setPreferredSize(new java.awt.Dimension(20000, 20000));
    }
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        if (graphNet == null) {
            graphNet = new GraphPetriNet();
        }
        //тобто на початку роботи встановлюється графічна мережа з порожніми списками графічних елементів та порожньою мережею Петрі!!!
/*        if (currentPlacementPoint != null) {
            paintCurrentPlacementPoint(g2);
        }*/
        graphNet.paintGraphPetriNet(g2, g);
        // промальовуємо всі мережі
        /*for (GraphPetriNet pnet : graphNetList) {
            if (pnet != graphNet) {
                pnet.paintGraphPetriNet(g2, g);
            }
        }*/
    }

    public void setGraphNet(GraphPetriNet net) { //коректно працює тільки якщо потім не змінювати граф
        //рекомендується використовувати addGraphNet
        graphNet = net;
        repaint();
    }
}
