import PetriObj.PetriP;
import PetriObj.PetriT;
import graphnet.GraphPetriNet;
import graphnet.GraphPetriPlace;
import graphnet.GraphPetriTransition;
import graphpresentation.PetriNetsPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class PanelModelSystems extends PetriNetsPanel {

    private int index = 0;
   // GraphPetriNet graphPetriNet = new GraphPetriNet();
    ArcSystem current;
    ArrayList<SystemModelPanel> systemModelPanels = new ArrayList<>();
    ArrayList<ArcSystem> arcSystems = new ArrayList<>();
    GraphPetriNet graphPetriNet;
    MoveListener ml = new MoveListener();

    MainFrame mainFrame;
    GraphPetriNet dd;
    boolean arc=false;

    PanelModelSystems(MainFrame mainFrame) {
        super(new JTextField());
        graphPetriNet=new GraphPetriNet();
        dd = new GraphPetriNet();
        setSize(800, 800);
        setBackground(Color.WHITE);
        this.mainFrame=mainFrame;

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                /*current = findarc(e.getPoint());
                if (current != null) {
                    //arcSystemFrame.setVisible(true);
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    current = null;
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                }*/
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                current = findarc(e.getPoint());
                if (current != null) {
                    //arcSystemFrame.setVisible(true);
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    current = null;
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                }
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                current = findarc(e.getPoint());
                if(current!=null) {

                    current.openFrame();
                    current.arcSystemFrame.setVisible(true);
                    current=null;

                }
                else
                    current = null;
            }

        });

    }


    public ArcSystem findarc(Point2D p)
    {
        if(arcSystems.size()!=0) {
            for (int i = 0; i < arcSystems.size(); i++) {

                Line2D.Float el = arcSystems.get(i).getLine();
                double yy = (arcSystems.get(i).getLine().y2 - arcSystems.get(i).getLine().y1);
                double xx = (arcSystems.get(i).getLine().x2 - arcSystems.get(i).getLine().x1);
                double mainy;
                if (xx == 0) {
                    mainy = arcSystems.get(i).getLine().x1;
                } else if (yy == 0) {
                    mainy = arcSystems.get(i).getLine().y1;
                } else {
                    mainy = (int)p.getX() * yy / xx - (int)arcSystems.get(i).getLine().x1 * yy / xx + (int)arcSystems.get(i).getLine().y1;
                }
                if (mainy<= (p.getY()+5) && mainy>=(p.getY()-5))
                    return arcSystems.get(i);

            }
        }
        return null;
    }


    public void addmodel(SystemModel systemModel)
    {
        SystemModelPanel systemModelPanel = new SystemModelPanel(systemModel,mainFrame,index);
        systemModelPanel.setX_(systemModelPanel.getX());
        systemModelPanel.setY_(systemModelPanel.getY());
            systemModelPanel.systemModel.graphPetriNetpaint = new GraphPetriNet();
            systemModelPanel.systemModel.graphPetriNetpaint.getGraphPetriPlaceList().addAll(systemModelPanel.systemModel.getGraphPetriNet().getGraphPetriPlaceList());
            systemModelPanel.systemModel.graphPetriNetpaint.getGraphPetriTransitionList().addAll(systemModelPanel.systemModel.getGraphPetriNet().getGraphPetriTransitionList());
            systemModelPanel.systemModel.graphPetriNetpaint.getGraphArcOutList().addAll(systemModelPanel.systemModel.getGraphPetriNet().getGraphArcOutList());
            systemModelPanel.systemModel.graphPetriNetpaint.getGraphArcInList().addAll(systemModelPanel.systemModel.getGraphPetriNet().getGraphArcInList());

        systemModelPanel.addMouseListener(ml);
        systemModelPanel.addMouseMotionListener(ml);


        systemModelPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
/*
                mainFrame.modelFrame.setGraphNet(systemModelPanel.systemModel.graphPetriNetpaint);
                mainFrame.modelFrame.repaint();
*/
                mainFrame.indicatorsPanel.setGraphNet(systemModelPanel.getIndex());
                //mainFrame.indicatorsPanel.update();
            }

        });
        index++;
        systemModelPanels.add(systemModelPanel);



        this.add(systemModelPanel);
    }

    public void newPlaceButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_newPlaceButtonActionPerformed
        // GraphPetriPlace pp = new GraphPetriPlace(new PetriP(PetriNetsPanel.getPetriPName(), 0), PetriNetsPanel.getIdPosition());
        GraphPetriPlace pp = new GraphPetriPlace(
                new PetriP(GraphPetriPlace.setSimpleName(), 0), PetriNetsPanel.getIdPosition()); //  by Inna 18.01.2013
        pp.setColor(Color.BLACK);
        getGraphNet().getGraphPetriPlaceList().add(pp);
        setCurrent(pp);

    }//GEN-LAST:event_newPlaceButtonActionPerformed

    public void newPlaceButtonActionPerformedOrange(ActionEvent evt) {//GEN-FIRST:event_newPlaceButtonActionPerformed
        // GraphPetriPlace pp = new GraphPetriPlace(new PetriP(PetriNetsPanel.getPetriPName(), 0), PetriNetsPanel.getIdPosition());
        GraphPetriPlace pp = new GraphPetriPlace(
                new PetriP(GraphPetriPlace.setSimpleName(), 0), PetriNetsPanel.getIdPosition()); //  by Inna 18.01.2013
        pp.setColor(Color.ORANGE);
        getGraphNet().getGraphPetriPlaceList().add(pp);
        setCurrent(pp);
    }//GEN-LAST:event_newPlaceButtonActionPerformed


    public void newTransitionButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_newTransitionButtonActionPerformed
        //  GraphPetriTransition pt = new GraphPetriTransition(new PetriT(PetriNetsPanel.getPetriTName(), 0.0, Double.MAX_VALUE), PetriNetsPanel.getIdTransition()); //corrected by Inna 28.11.2012
        GraphPetriTransition pt = new GraphPetriTransition(
                new PetriT(GraphPetriTransition.setSimpleName(), 0.0), PetriNetsPanel.getIdTransition());//  by Inna 18.01.2013
        getGraphNet().getGraphPetriTransitionList().add(pt);
        setCurrent(pt);
        // pt.getPetriTransition().printParameters();
    }//GEN-LAST:event_newTransitionButtonActionPerformed

    public void newArcButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_newArcButtonActionPerformed
        //setIsSettingArc(true);
        arc = true;
    }//GEN-LAST:event_newArcButtonActionPerformed



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        for(SystemModelPanel systemModelPanel: systemModelPanels)
        {
            systemModelPanel.setLocation(systemModelPanel.getX_(),systemModelPanel.getY_());
        }
        BasicStroke pen = new BasicStroke(3);
        g2.setStroke(pen);
        //g2.drawString("ss",10,10);
        for(int i=0;i<arcSystems.size();i++)
        {
            g2.setColor(Color.lightGray);
            g2.draw(arcSystems.get(i).getLine());
            double x1=  arcSystems.get(i).getLine().getX1();
            double y1 = arcSystems.get(i).getLine().getY1();
            double x2 = arcSystems.get(i).getLine().getX2();
            double y2 = arcSystems.get(i).getLine().getY2();
            double lenght = Math.sqrt(Math.pow(x2-x1,2) +Math.pow(y2-y1,2));

            AffineTransform fontAT = new AffineTransform();

            //fontAT.rotate((y1-y2),(x2-x1),0,0);

            fontAT.rotate(Math.abs(x1-x2),Math.abs(y1-y2),0,0);
            fontAT.translate(0,2);
            System.out.println((((y2-y1+4)/(x2-x1))));
            System.out.println("-----------------");
            Font f = new Font("Calibri",Font.BOLD,12).deriveFont(fontAT);

            g2.setFont(f);
            //g2.drawString("ss",50,50);
            //g2.drawString("saa",(int)(x1+lenght/3),(int)(y2-y1)*2);
            //g2.drawString("ddd",(int)x1,(int)y1);
            g2.setColor(Color.BLUE);
            g2.drawString("t=" + arcSystems.get(i).getTime(),(int)((x2-x1)/7*2+x1),(int)((y2-y1)/7*2+y1+2));
            g2.setColor(Color.RED);
            g2.drawString("pri="+arcSystems.get(i).getPriority(),(int)((x2-x1)/7*3+x1),(int)((y2-y1)/7*3+y1+2));
            g2.setColor(Color.GREEN);
            g2.drawString("pro="+arcSystems.get(i).getProbability(),(int)((x2-x1)/7*4+x1),(int)((y2-y1)/7*4+y1+2));
            g2.setColor(Color.lightGray);
            double x3,y3;

            double beta = Math.atan2((y1-y2),(x2-x1));
            double alfa = Math.PI/18;
            int r1 = 20;

            Polygon polygon = new Polygon();


            x3=(int)Math.round(x2-r1*Math.cos(beta+alfa));
            y3=(int)Math.round(y2+r1*Math.sin(beta+alfa));
            //g2.draw(new Line2D.Double(x2,y2,x3,y3));
            polygon.addPoint((int)x3,(int)y3);

            x3=(int)Math.round(x2-r1*Math.cos(beta-alfa));
            y3=(int)Math.round(y2+r1*Math.sin(beta-alfa));
           // g2.draw(new Line2D.Double(x2,y2,x3,y3));
            polygon.addPoint((int)x3,(int)y3);
            polygon.addPoint((int)x2,(int)y2);
            g2.draw(polygon);
            g2.fill(polygon);

        }



    }

    SystemModelPanel systemModelPanel_out = null;
    SystemModelPanel systemModelPanel_inp = null;
    ArcSystem arcSystem = null;

    class MoveListener extends MouseAdapter {

        private Point old;

        @Override
        public void mousePressed(MouseEvent e) {
            //super.mousePressed(e);
            old = e.getPoint();
            SystemModelPanel qs = (SystemModelPanel)e.getSource();
            mainFrame.indicatorsPanel.setGraphNet(qs.getIndex());
            //mainFrame.indicatorsPanel.update();
            System.out.println(qs.getIndex());
            System.out.println(qs.systemModel.graphPetriNet.getGraphPetriTransitionList().get(0).getPetriTransition().getProbability());
            System.out.println("----------------");
            if(e.getSource().getClass().getName().equals("SystemModelPanel"))
            {
                SystemModelPanel s = (SystemModelPanel)e.getSource();
                mainFrame.modelFrame.setGraphNet(s.netpaint);
                //mainFrame.modelFrame.repaint();
            }


            if(arc && (e.getSource().getClass().getName().equals("SystemModelPanel")))
            {
                if(((SystemModelPanel) e.getSource()).systemModel.output.size()>0) {
                    systemModelPanel_out = (SystemModelPanel) e.getSource();
                    arc = false;
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Виходу немає");
                }
            }
            else if (systemModelPanel_out !=null)
            {
                if(((SystemModelPanel) e.getSource()).systemModel.input.size()>0) {
                    systemModelPanel_inp = (SystemModelPanel) e.getSource();

                    arcSystem = new ArcSystem(systemModelPanel_out, systemModelPanel_inp);

                    arcSystems.add(arcSystem);
                    systemModelPanel_inp = null;
                    systemModelPanel_out = null;
                    arc = false;
                    arcSystem = null;
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Входу немає");
                }
            }
            else
            {
                arc=false;
                arcSystem = null;
                systemModelPanel_inp=null;
                systemModelPanel_out=null;
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            ((SystemModelPanel)e.getSource()).setLocation(((SystemModelPanel)e.getSource()).getX() + e.getX() - (int)old.getX(), ((SystemModelPanel)e.getSource()).getY() + e.getY() - (int)old.getY());
            ((SystemModelPanel)e.getSource()).setX_(((SystemModelPanel)e.getSource()).getX() + e.getX() - (int)old.getX());
            ((SystemModelPanel)e.getSource()).setY_(((SystemModelPanel)e.getSource()).getY() + e.getY() - (int)old.getY());
            repaint();
        }
    }
}
