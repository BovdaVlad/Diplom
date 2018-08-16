package graphpresentation;

import PetriObj.*;
import graphnet.GraphPetriPlace;
import graphnet.GraphPetriTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CreateModelFrame extends JFrame {


    private JTextField netNameTextField = new JTextField("");

    private JTextField timeStartField= new JTextField("0");
    private JTextField timeModelingTextField = new JTextField("1000");
    private FileUse fileUse = new FileUse();

    public PetriNetsPanel petriNetsPanel;
   // public PetriNetsPanel petriNetsPanel;

    public CreateModelFrame() {

        JMenuBar menuBar = new JMenuBar();

        JButton menusave = new JButton("Зберегти");
        menusave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jMenuItem2ActionPerformed(e);
            }
        });

        JButton menuopen = new JButton("Відкрити");
        menuopen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMenuItemActionPerformed(e);
            }
        });
        menuopen.setIcon(new ImageIcon(getImage("src\\img\\open.png")));
        menusave.setIcon(new ImageIcon(getImage("src\\img\\save.png")));
        menuBar.add(menuopen);
        menuBar.add(menusave);

        /////////////////////////////////////////////////////
        JButton menuplace = new JButton("Мітки");
        menuplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                newPlaceButtonActionPerformed(evt);
            }
        });
        JButton menutrans = new JButton("Переходи");
        menutrans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                newTransitionButtonActionPerformed(evt);
            }
        });
        JButton menuarc = new JButton("Дуги");
        menuarc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                newArcButtonActionPerformed(evt);
            }
        });
        JButton menugreenplace = new JButton("Вхідні мітки");
        JButton menuredplace = new JButton("Вихідні мітки");
        JButton menublueplace = new JButton("Ресурси");

        JButton menurun = new JButton("Запуск");

        menurun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemRunNetActionPerformed(e);
            }
        });

        menuplace.setIcon(new ImageIcon(getImage("src\\img\\p.png")));
        menutrans.setIcon(new ImageIcon(getImage("src\\img\\t.png")));
        menuarc.setIcon(new ImageIcon(getImage("src\\img\\a.png")));
        menugreenplace.setIcon(new ImageIcon(getImage("src\\img\\pg.png")));
        menuredplace.setIcon(new ImageIcon(getImage("src\\img\\pr.png")));
        menublueplace.setIcon(new ImageIcon(getImage("src\\img\\pb.png")));
        menurun.setIcon(new ImageIcon(getImage("src\\img\\run.png")));

        menuBar.add(menuplace);
        menuBar.add(menutrans);
        menuBar.add(menuarc);

        menuBar.add(menugreenplace);
        menuBar.add(menuredplace);
        menuBar.add(menublueplace);
        menuBar.add(menurun);


        menugreenplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                newPlaceButtonActionPerformedGreen(evt);
            }
        });
        menublueplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                newPlaceButtonActionPerformedBlue(evt);
            }
        });
        menuredplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                newPlaceButtonActionPerformedRed(evt);
            }
        });

       // menuBar.add(new JLabel("Початок моделювання"));
       // menuBar.add(timeStartField);
        menuBar.add(new JLabel("Час моделювання"));
        menuBar.add(timeModelingTextField);

        this.setJMenuBar(menuBar);


        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(0, 0);
        petriNetsPanel = new PetriNetsPanel(new JTextField("sss"));
        petriNetsPanel.setSize(1000, 600);

        add(petriNetsPanel);




        pack();

    }

    private void newArcButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_newArcButtonActionPerformed

        petriNetsPanel.setIsSettingArc(true);
    }//GEN-LAST:event_newArcButtonActionPerformed

    private void newTransitionButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_newTransitionButtonActionPerformed
        //  GraphPetriTransition pt = new GraphPetriTransition(new PetriT(PetriNetsPanel.getPetriTName(), 0.0, Double.MAX_VALUE), PetriNetsPanel.getIdTransition()); //corrected by Inna 28.11.2012
        GraphPetriTransition pt = new GraphPetriTransition(
                new PetriT(GraphPetriTransition.setSimpleName(), 0.0), PetriNetsPanel.getIdTransition());//  by Inna 18.01.2013
        petriNetsPanel.getGraphNet().getGraphPetriTransitionList().add(pt);
        petriNetsPanel.setCurrent(pt);
        // pt.getPetriTransition().printParameters();
    }//GEN-LAST:event_newTransitionButtonActionPerformed

    private void newPlaceButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_newPlaceButtonActionPerformed
        // GraphPetriPlace pp = new GraphPetriPlace(new PetriP(PetriNetsPanel.getPetriPName(), 0), PetriNetsPanel.getIdPosition());
        GraphPetriPlace pp = new GraphPetriPlace(
                new PetriP(GraphPetriPlace.setSimpleName(), 0), PetriNetsPanel.getIdPosition()); //  by Inna 18.01.2013
        pp.setColor(Color.BLACK);
        petriNetsPanel.getGraphNet().getGraphPetriPlaceList().add(pp);
        petriNetsPanel.setCurrent(pp);
        // pp.getPetriPlace().printParameters();
        // System.out.println("after added place we have such graph net:");
        //  petriNetsPanel.getGraphNet().print();
    }//GEN-LAST:event_newPlaceButtonActionPerformed

    private void newPlaceButtonActionPerformedGreen(ActionEvent evt) {//GEN-FIRST:event_newPlaceButtonActionPerformed
        // GraphPetriPlace pp = new GraphPetriPlace(new PetriP(PetriNetsPanel.getPetriPName(), 0), PetriNetsPanel.getIdPosition());
        GraphPetriPlace pp = new GraphPetriPlace(
                new PetriP(GraphPetriPlace.setSimpleName(), 0), PetriNetsPanel.getIdPosition()); //  by Inna 18.01.2013
        pp.setColor(Color.GREEN);
        petriNetsPanel.getGraphNet().getGraphPetriPlaceList().add(pp);
        petriNetsPanel.setCurrent(pp);
        // pp.getPetriPlace().printParameters();
        // System.out.println("after added place we have such graph net:");
        //  petriNetsPanel.getGraphNet().print();
    }//GEN-LAST:event_newPlaceButtonActionPerformed

    private void newPlaceButtonActionPerformedBlue(ActionEvent evt) {//GEN-FIRST:event_newPlaceButtonActionPerformed
        // GraphPetriPlace pp = new GraphPetriPlace(new PetriP(PetriNetsPanel.getPetriPName(), 0), PetriNetsPanel.getIdPosition());
        GraphPetriPlace pp = new GraphPetriPlace(
                new PetriP(GraphPetriPlace.setSimpleName(), 0), PetriNetsPanel.getIdPosition()); //  by Inna 18.01.2013
        pp.setColor(Color.BLUE);
        petriNetsPanel.getGraphNet().getGraphPetriPlaceList().add(pp);
        petriNetsPanel.setCurrent(pp);
        // pp.getPetriPlace().printParameters();
        // System.out.println("after added place we have such graph net:");
        //  petriNetsPanel.getGraphNet().print();
    }//GEN-LAST:event_newPlaceButtonActionPerformed

    private void newPlaceButtonActionPerformedRed(ActionEvent evt) {//GEN-FIRST:event_newPlaceButtonActionPerformed
        // GraphPetriPlace pp = new GraphPetriPlace(new PetriP(PetriNetsPanel.getPetriPName(), 0), PetriNetsPanel.getIdPosition());
        GraphPetriPlace pp = new GraphPetriPlace(
                new PetriP(GraphPetriPlace.setSimpleName(), 0), PetriNetsPanel.getIdPosition()); //  by Inna 18.01.2013
        pp.setColor(Color.RED);
        petriNetsPanel.getGraphNet().getGraphPetriPlaceList().add(pp);
        petriNetsPanel.setCurrent(pp);
        // pp.getPetriPlace().printParameters();
        // System.out.println("after added place we have such graph net:");
        //  petriNetsPanel.getGraphNet().print();
    }//GEN-LAST:event_newPlaceButtonActionPerformed
    private BufferedImage getImage(String path) {
        int sizeimage = 20;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
            System.out.println(image.getWidth() + " - " + image.getHeight());
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(1 / ((double) image.getWidth() / sizeimage), 1 / ((double) image.getHeight() / sizeimage)), null);
            image = op.filter(image, null);
            return image;
        } catch (IOException ex) {
            System.out.println("dssd");
            return null;
        }
    }

    private void itemRunNetActionPerformed(ActionEvent evt) {//GEN-FIRST:event_itemRunNetActionPerformed


        JTextField netNameTextField = new JTextField("ssw");
        JTextArea protokolTextArea = new JTextArea();
        //protokolTextArea.setText("---------Events protocol----------");
        //protokolTextArea.setText("---------STATISTICS---------");
        if (petriNetsPanel.getGraphNet() == null) {
            //   errorFrame.setErrorMessage(" Petri Net does not exist yet. Paint it or read it from file.");
            // errorFrame.setVisible(true);
        } else {
            try {
                petriNetsPanel.getGraphNet().createPetriNet(netNameTextField.getText()); // modified by Katya 08.12.2016
                if (petriNetsPanel.getGraphNet().hasParameters() == true) { // added by Katya 08.12.2016
                    //errorFrame.setErrorMessage(" Petri Net has parameters. Provide specific values for them first.");
                    //errorFrame.setVisible(true);
                    return;
                }
                if (petriNetsPanel.getGraphNet().isCorrectInArcs() != true) {
                    //errorFrame.setErrorMessage(" Transition has no input places.");
                    //errorFrame.setVisible(true);
                } else {
                    if (petriNetsPanel.getGraphNet().isCorrectOutArcs() != true) {
                        //errorFrame.setErrorMessage(" Transition has no output places.");
                        //errorFrame.setVisible(true);
                    } else {
                        if (petriNetsPanel.getGraphNet().getPetriNet() == null) {
                            //errorFrame.setErrorMessage(" Petri Net does not exist yet. Paint it or read it from file. ");
                            //errorFrame.setVisible(true);
                        } else {
                            PetriSim.setTimeMod(Double.parseDouble(timeModelingTextField.getText()));
                            PetriSim petriSim = new PetriSim(petriNetsPanel.getGraphNet().getPetriNet());
                            PetriSim.setTimeCurr(Double.valueOf(timeStartField.getText()));
                            // petriSim.changeTimeCurr(); // added by Inna 18.01.2013

                            ArrayList<PetriSim> list = new ArrayList<PetriSim>();
                            list.add(petriSim);
                            PetriObjModel m = new PetriObjModel(list); //Петрі-об"єктна модель, що складається з одного Петріз-об"єкта
                           // m.go(Double.valueOf(timeModelingTextField.getText()),10, protokolTextArea,null,null,null,null);
                            m.go(Double.valueOf(timeModelingTextField.getText()));
                            //petriNetsPanel.getGraphNet().printStatistics(statisticsTextArea);
                            //перетворення у потрібний формат ...
                            Double d = new Double(petriSim.getTimeCurr()); //added by Inna 3.06.2013
                            Double dd = new Double(100.0*(petriSim.getTimeCurr()-d.intValue()));  //десяткова частина
                            //timeStartField.setText(String.valueOf(
                            //        d.intValue()+"."+dd.intValue()
                            //)); //added by Inna 3.06.2013
                            timeStartField.setText("0");
                            //timeModelingTextField.setText("");
                            petriNetsPanel.repaint(); //додано 19.11.2012, можливо не потрібно?
                        }
                    }
                }
            } catch (ExceptionInvalidNetStructure ex) {
                Logger.getLogger(PetriNetsFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_itemRunNetActionPerformed

    private void jMenuItem2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            fileUse.saveGraphNetAs(petriNetsPanel, this);
        } catch (ExceptionInvalidNetStructure ex) {
            Logger.getLogger(PetriNetsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed


    private void openMenuItemActionPerformed(ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        try {
            fileUse.newWorksheet(petriNetsPanel);
            timeStartField.setText(String.valueOf(0));

            netNameTextField.setText("Untitled");
            //protokolTextArea.setText("---------Events protocol----------");
            //statisticsTextArea.setText("---------STATISTICS---------");
            String pnetName = fileUse.openFile (petriNetsPanel, this);
            if (pnetName != null) {
                netNameTextField.setText(pnetName);
            }
        } catch (ExceptionInvalidNetStructure ex) {
            Logger.getLogger(PetriNetsFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_openMenuItemActionPerformed



}
