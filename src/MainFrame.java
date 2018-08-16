import PetriObj.*;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import graphnet.*;
import graphpresentation.CreateModelFrame;
import graphpresentation.PetriNetsFrame;
import graphpresentation.PetriNetsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame {

    PanelModelSystems panelModelSystems;
    PetriNetsPanelInactive modelFrame;
    IndicatorsPanel indicatorsPanel;
    JSplitPane jSplitPane;
    JTextArea protokolTextArea = new JTextArea();
    public MainFrame() {
        super("Головне вікно");

        this.setSize(1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(0, 0);
        //protokolTextArea.setSize(500,500);
        //add(protokolTextArea);
        JPanel panelModel = new JPanel();
        panelModel.setSize(700,700);
        panelModel.setLayout(new BorderLayout());


        // робоча панель
        panelModelSystems = new PanelModelSystems(MainFrame.this);
        panelModel.add(panelModelSystems,BorderLayout.CENTER);



        JPanel panelall = new PanelIconAll(MainFrame.this);
        JPanel panelcustomers = new JPanel();
        JPanel paneldevice = new JPanel();
        JPanel panelfactories = new JPanel();
        JPanel panelother = new JPanel();
        JPanel panelworkers = new JPanel();

        JScrollPane jScrollPaneall = new JScrollPane(panelall);
        JScrollPane jScrollPanecustomers = new JScrollPane(panelcustomers);
        JScrollPane jScrollPanedevice = new JScrollPane(paneldevice);
        JScrollPane jScrollPanefactories = new JScrollPane(panelfactories);
        JScrollPane jScrollPaneother = new JScrollPane(panelother);
        JScrollPane jScrollPaneworkers = new JScrollPane(panelworkers);

        JTabbedPane jTabbedPane = new JTabbedPane();

        jTabbedPane.add("Все",jScrollPaneall);
        jTabbedPane.add("Клієнти",jScrollPanecustomers);
        jTabbedPane.add("Працівники",jScrollPaneworkers);
        jTabbedPane.add("Прилади",jScrollPanedevice );
        jTabbedPane.add("Заводи",jScrollPanefactories);
        jTabbedPane.add("Інше",jScrollPaneother);
       // jTabbedPane.setMinimumSize(new Dimension(200,500));
        panelModel.add(jTabbedPane,BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu menufile = new JMenu("Файл");
        JMenuItem createsystem = new JMenuItem("Створити систему");

        createsystem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateModelFrame createModelFrame = new CreateModelFrame();
                createModelFrame.setVisible(true);
            }
        });
        JMenuItem opensystem = new JMenuItem("Відкрити систему");
        menufile.add(createsystem);

        JMenu menuelements = new JMenu("З'єднання підсистем");
        JMenuItem transitions = new JMenuItem("Переходи");

        transitions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelModelSystems.newTransitionButtonActionPerformed(e);
            }
        });
        JMenuItem targets = new JMenuItem("Цільові показники");
        targets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelModelSystems.newPlaceButtonActionPerformedOrange(e);
            }
        });

        JMenuItem place = new JMenuItem("Звичайні мітки");
        place.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelModelSystems.newPlaceButtonActionPerformed(e);
            }
        });

        JMenuItem compound = new JMenuItem("З'єднання");

        compound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelModelSystems.newArcButtonActionPerformed(e);
            }
        });
       // menuelements.add(transitions);
      //  menuelements.add(targets);
      //  menuelements.add(place);
        menuelements.add(compound);

        JMenu menumodel = new JMenu("Моделювання");

        JMenuItem runmodel = new JMenuItem("Запуск моделі");
        runmodel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemRunNetActionPerformed(e);
            }
        });

        JMenuItem generatemodel = new JMenuItem("Генерація моделі");
        menumodel.add(runmodel);
        //menumodel.add(generatemodel);
        //JMenu menugeneticalgoritm = new JMenu("Генетичний алгоритм");

        menuBar.add(menufile);
        menuBar.add(menuelements);
        menuBar.add(menumodel);
       // menuBar.add(menugeneticalgoritm);
        this.setJMenuBar(menuBar);

        JPanel panel2 = new JPanel();
        panel2.setSize(500,700);
        panel2.setMinimumSize(new Dimension(500,700));

        indicatorsPanel = new IndicatorsPanel(MainFrame.this);
        indicatorsPanel.setSize(500,350);
        //indicatorsFrame.setLayout(new BorderLayout());
        //**********************************************
        //
        //
        //
        //
        //
        // **********
        JScrollPane jScrollPane1 = new JScrollPane(indicatorsPanel);
        jScrollPane1.setSize(500,350);
////////////////////////////////////////////////////////////////////////////////////////////////////
        //ModelFrame modelFrame = new ModelFrame();
        modelFrame = new PetriNetsPanelInactive();

        modelFrame.setSize(500,350);

        JScrollPane jScrollPane2 = new JScrollPane(modelFrame);
        jScrollPane2.setSize(500,350);


        JSplitPane jSplitPanepanel2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,jScrollPane1,jScrollPane2);
        jSplitPanepanel2.setSize(500,700);
        jSplitPanepanel2.setDividerLocation(550);
        jSplitPanepanel2.setDividerLocation(0.5);
        jSplitPanepanel2.setMinimumSize(new Dimension(500,700));
        jSplitPanepanel2.setOneTouchExpandable(true);



        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panelModel,jSplitPanepanel2);

        jSplitPane.setDividerLocation(700);
        jSplitPane.setOneTouchExpandable(true);
        this.add(jSplitPane);

    }


    private void itemRunNetActionPerformed(ActionEvent evt) {//GEN-FIRST:event_itemRunNetActionPerformed


        JTextField netNameTextField = new JTextField("ssw");

        JTextField timeStartField = new JTextField("0");
        String timemod = JOptionPane.showInputDialog("Введіть час моделювання","10000");
        JTextField timeModelingTextField = new JTextField("10000");
        timeModelingTextField.setText(timemod);
        int nump = 0;
        int numt = 0;
        int numarcin=0;
        int numarcout=0;
        for (int i = 0; i < panelModelSystems.systemModelPanels.size(); i++)
        {
            for(int g =0;g<panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcOutList().size();g++)
            {
                /*
                if(i>0) {
                    //for (int h = 0; h < panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcOutList().size(); h++) {
                    panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcOutList().get(g).getArcOut().setNumT(panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcOutList().get(g).getArcOut().getNumT() + numt);
                    panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcOutList().get(g).getArcOut().setNumP(panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcOutList().get(g).getArcOut().getNumP() + nump);
                    //}
                }
                */
                ArcOut arcOut = null;
                try {
                    arcOut = panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcOutList().get(g).getArcOut().clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

                arcOut.setNumP(arcOut.getNumP()+nump);
                arcOut.setNumT(arcOut.getNumT()+numt);
                arcOut.setNameP(panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcOutList().get(g).getArcOut().getNameP());
                arcOut.setNameT(panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcOutList().get(g).getArcOut().getNameT());
                GraphArcOut graphArcOut = new GraphArcOut(arcOut);

                // panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcOutList().get(g).getArcOut().setNumber(numarcout);
                panelModelSystems.graphPetriNet.getGraphArcOutList().add(graphArcOut);
                // numarcout++;

            }
            for(int y =0;y<panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcInList().size();y++)
            {
                /*
                if(i>0) {
                    //for (int h = 0; h < panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcInList().size(); h++) {
                    panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcInList().get(y).getArcIn().setNumT(panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcInList().get(y).getArcIn().getNumT()+numt);//panelModelSystems.getGraphNet().getGraphPetriTransitionList().size()-1);
                    panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcInList().get(y).getArcIn().setNumP(panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcInList().get(y).getArcIn().getNumP()+nump);//panelModelSystems.getGraphNet().getGraphPetriPlaceList().size()-1);
                    //}
                }
                */
                ArcIn arcIn = null;
                try {
                    arcIn = panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcInList().get(y).getArcIn().clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                arcIn.setNumP(arcIn.getNumP()+nump);
                arcIn.setNumT(arcIn.getNumT()+numt);
                arcIn.setNameP(panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcInList().get(y).getArcIn().getNameP());
                arcIn.setNameT(panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcInList().get(y).getArcIn().getNameT());
                GraphArcIn graphArcIn = new GraphArcIn(arcIn);
                // panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphArcInList().get(y).getArcIn().setNumber(numarcin);
                panelModelSystems.graphPetriNet.getGraphArcInList().add(graphArcIn);
                // numarcin++;

            }


            for(int k=0;k<panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphPetriTransitionList().size();k++) {


                PetriT petriTT = null;
                try {
                    petriTT = panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphPetriTransitionList().get(k).getPetriTransition().clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                petriTT.setNumber(numt);
                petriTT.getInP().add(0);
                petriTT.addInP(0);
                petriTT.setTimeServ(panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphPetriTransitionList().get(k).getPetriTransition().getTimeServ());

                System.out.println(petriTT.getName() + "  " + petriTT.getTimeServ());
                GraphPetriTransition graphPetriTransition = new GraphPetriTransition(petriTT);
               // panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphPetriTransitionList().get(k).getPetriTransition().getInP().add(0);
                for (int h = 0; h < petriTT.getInP().size(); h++)
                    petriTT.getInP().set(h, (petriTT.getInP().get(h) + nump));
                numt++;
                panelModelSystems.graphPetriNet.getGraphPetriTransitionList().add(graphPetriTransition);//panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphPetriTransitionList().get(k));

            }
            for(int j=0;j<panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphPetriPlaceList().size();j++)
            {
                panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphPetriPlaceList().get(j).getPetriPlace().setNumber(nump);
                nump++;
                panelModelSystems.graphPetriNet.getGraphPetriPlaceList().add(panelModelSystems.systemModelPanels.get(i).systemModel.getGraphPetriNet().getGraphPetriPlaceList().get(j));
            }
        }

        for(int y=0;y<panelModelSystems.arcSystems.size();y++)
        {
            PetriT petriT = new PetriT("TT" + y, panelModelSystems.arcSystems.get(y).getTime());
            petriT.setNumber(numt);
            //petriT.changeMean(222);
            petriT.setPriority((int)panelModelSystems.arcSystems.get(y).getPriority());
            petriT.setProbability(panelModelSystems.arcSystems.get(y).getProbability());

            GraphPetriTransition graphPetriTransition = new GraphPetriTransition(petriT);
            panelModelSystems.graphPetriNet.getGraphPetriTransitionList().add(graphPetriTransition);

            int out = panelModelSystems.arcSystems.get(y).getIndex_output();
            int inp = panelModelSystems.arcSystems.get(y).getIndex_input();

            int current_number = 0;
            int h=0;
            for(h=0;h<out;h++)
            {
                current_number+= panelModelSystems.systemModelPanels.get(h).getCountplace();
            }
            int gg=0;
            System.out.println(panelModelSystems.arcSystems.get(y).getIndex_output() + " - " + panelModelSystems.arcSystems.get(y).getIndex_input());

            ArrayList<Integer> index_in = new ArrayList<>();
            for(int f=0;f<panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_input.size();f++)
            {
                index_in.add(panelModelSystems.arcSystems.get(y).arcSystemFrame.index_inp.get(panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_out.get(f)));
            }
            ArrayList<Integer> index_out = new ArrayList<>();
            for(int f=0;f<panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_out.size();f++)
            {
                index_out.add(panelModelSystems.arcSystems.get(y).arcSystemFrame.index_out.get(panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_input.get(f)));
            }

            for(Integer num : index_out)
            {
                petriT.addInP(num+current_number);
                GraphPetriPlace p = panelModelSystems.graphPetriNet.getGraphPetriPlaceList().get(num+current_number);  //systemModel.getGraphPetriNet().getGraphPetriPlaceList().get(num);

//                System.out.println("KKKK In  " + panelModelSystems.arcSystems.get(y).numarc.get(panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_input.get(gg)));


                System.out.println(panelModelSystems.arcSystems.get(y).numarc.size() + " - size   " + panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_out.get(gg) + " - index");
                ArcIn arcIn = new ArcIn(p.getNumber(), petriT.getNumber(), panelModelSystems.arcSystems.get(y).numarc.get(panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_out.get(gg)));
                arcIn.setNameP(p.getName());
                arcIn.setNameT(petriT.getName());
                GraphArcIn graphArcIn = new GraphArcIn(arcIn);
                graphArcIn.getArcIn().setNumT(numt);
                graphArcIn.getArcIn().setNumP(num+current_number);
                panelModelSystems.graphPetriNet.getGraphArcInList().add(graphArcIn);

                gg++;
            }
            gg=0;
            current_number=0;
            for(h=0;h<inp;h++)
            {
                current_number+= panelModelSystems.systemModelPanels.get(h).getCountplace();
            }



            for(Integer num : index_in)//panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_out)
            {
                petriT.addOutP(num+current_number);
                GraphPetriPlace p = panelModelSystems.graphPetriNet.getGraphPetriPlaceList().get(num+current_number);

                try {
                    System.out.println("KKKK Out  " + panelModelSystems.arcSystems.get(y).numarc.get(panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_out.size() + gg));

                }
                catch (Exception ex)
                {
                    System.out.println((panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_out.size() + gg));
                    System.out.println(ex.getLocalizedMessage());
                }
                //GraphPetriPlace p = panelModelSystems.systemModelPanels.get(inp).systemModel.getGraphPetriNet().getGraphPetriPlaceList().get(num);
                ArcOut arcOut = new ArcOut(p.getNumber(), petriT.getNumber(),  panelModelSystems.arcSystems.get(y).numarc.get(panelModelSystems.arcSystems.get(y).arcSystemFrame.indexes_out.size()+gg));
                arcOut.setNameP(p.getName());
                arcOut.setNameT(petriT.getName());
                GraphArcOut graphArcOut = new GraphArcOut(arcOut);
                graphArcOut.getArcOut().setNumT(numt);
                graphArcOut.getArcOut().setNumP(num+current_number);
                panelModelSystems.graphPetriNet.getGraphArcOutList().add(graphArcOut);
                gg++;

            }

            numt++;


        }
        /*
        System.out.println();
        System.out.println("ArcIn");
        for(int r=0;r<panelModelSystems.graphPetriNet.getGraphArcInList().size();r++)
        {
            System.out.println(panelModelSystems.graphPetriNet.getGraphArcInList().get(r).getArcIn().getNameP() + "  -  "
            + panelModelSystems.graphPetriNet.getGraphArcInList().get(r).getArcIn().getNameT());

            System.out.println(panelModelSystems.graphPetriNet.getGraphArcInList().get(r).getArcIn().getNumP() + "  -  "
                    + panelModelSystems.graphPetriNet.getGraphArcInList().get(r).getArcIn().getNumT());
        }
        */
/*
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("ArcOun");
        for(int r=0;r<panelModelSystems.graphPetriNet.getGraphArcOutList().size();r++)
        {
            System.out.println(panelModelSystems.graphPetriNet.getGraphArcOutList().get(r).getArcOut().getNameP() + "  -  "
                    + panelModelSystems.graphPetriNet.getGraphArcOutList().get(r).getArcOut().getNameT());
            System.out.println(panelModelSystems.graphPetriNet.getGraphArcOutList().get(r).getArcOut().getNumP() + "  -  "
                    + panelModelSystems.graphPetriNet.getGraphArcOutList().get(r).getArcOut().getNumT());
        }
        */
        /*
        PetriT petriT = new PetriT("T", 1.0);
        petriT.setNumber(numt);

        GraphPetriTransition graphPetriTransition = new GraphPetriTransition(petriT);

        panelModelSystems.graphPetriNet.getGraphPetriTransitionList().add(graphPetriTransition);
        for (GraphPetriPlace p : panelModelSystems.systemModelPanels.get(0).systemModel.getGraphPetriNet().getGraphPetriPlaceList()) {
            if (p.getColor().equals(Color.RED)) {
                petriT.addInP(2);
                ArcIn arcIn = new ArcIn(p.getNumber(), petriT.getNumber(), 1);
                GraphArcIn graphArcIn = new GraphArcIn(arcIn);
                graphArcIn.getArcIn().setNumT(4);
                graphArcIn.getArcIn().setNumP(2);
                panelModelSystems.graphPetriNet.getGraphArcInList().add(graphArcIn);
            }
        }
        for (GraphPetriPlace p : panelModelSystems.systemModelPanels.get(1).systemModel.getGraphPetriNet().getGraphPetriPlaceList()) {
            if (p.getColor().equals(Color.GREEN)) {
                petriT.addOutP(3);
                ArcOut arcOut = new ArcOut(p.getNumber(), petriT.getNumber(), 1);
                GraphArcOut graphArcOut = new GraphArcOut(arcOut);
                graphArcOut.getArcOut().setNumT(4);
                graphArcOut.getArcOut().setNumP(3);
                panelModelSystems.graphPetriNet.getGraphArcOutList().add(graphArcOut);
            }
        }

        */
        //System.out.println(print());

        //protokolTextArea.setText("---------Events protocol----------");
        //protokolTextArea.setText("---------STATISTICS---------");


        try {
            try {
                panelModelSystems.graphPetriNet.createPetriNet("a");
            } catch (ExceptionInvalidNetStructure exceptionInvalidNetStructure) {
                exceptionInvalidNetStructure.printStackTrace();
            }
            panelModelSystems.dd = (GraphPetriNet)panelModelSystems.graphPetriNet.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        if (panelModelSystems.dd == null) {
            //   errorFrame.setErrorMessage(" Petri Net does not exist yet. Paint it or read it from file.");
            // errorFrame.setVisible(true);
        } else {
           // try {

            //    panelModelSystems.dd.createPetriNet(netNameTextField.getText()); // modified by Katya 08.12.2016


                if (panelModelSystems.dd.hasParameters() == true) { // added by Katya 08.12.2016
                    //errorFrame.setErrorMessage(" Petri Net has parameters. Provide specific values for them first.");
                    //errorFrame.setVisible(true);
                    return;
                }
                if (panelModelSystems.dd.isCorrectInArcs() != true) {
                    //errorFrame.setErrorMessage(" Transition has no input places.");
                    //errorFrame.setVisible(true);
                } else {
                    if (panelModelSystems.dd.isCorrectOutArcs() != true) {
                        //errorFrame.setErrorMessage(" Transition has no output places.");
                        //errorFrame.setVisible(true);
                    } else {
                        if (panelModelSystems.dd.getPetriNet() == null) {
                            //errorFrame.setErrorMessage(" Petri Net does not exist yet. Paint it or read it from file. ");
                            //errorFrame.setVisible(true);
                        } else {
                            PetriSim.setTimeMod(Double.parseDouble(timeModelingTextField.getText()));
                            PetriSim petriSim = new PetriSim(panelModelSystems.dd.getPetriNet());
                            PetriSim.setTimeCurr(Double.valueOf(timeStartField.getText()));
                            // petriSim.changeTimeCurr(); // added by Inna 18.01.2013

                            ArrayList<PetriSim> list = new ArrayList<PetriSim>();
                            list.add(petriSim);
                            PetriObjModel m = new PetriObjModel(list); //Петрі-об"єктна модель, що складається з одного Петріз-об"єкта

                            ArrayList<String> name_systems = new ArrayList<>();
                            ArrayList<Integer> count_place = new ArrayList<>();
                            ArrayList<Integer> count_transfer = new ArrayList<>();
                            ArrayList<String> name_place = new ArrayList<>();
                            for(int k=0;k<panelModelSystems.systemModelPanels.size();k++)
                            {
                                for(int s=0;s<panelModelSystems.systemModelPanels.get(k).systemModel.getGraphPetriNet().getGraphPetriPlaceList().size();s++)
                                    name_place.add(panelModelSystems.systemModelPanels.get(k).systemModel.getGraphPetriNet().getGraphPetriPlaceList().get(s).getName());
                                name_systems.add(panelModelSystems.systemModelPanels.get(k).systemModel.getName());
                                count_place.add(panelModelSystems.systemModelPanels.get(k).getCountplace());
                                count_transfer.add(panelModelSystems.systemModelPanels.get(k).systemModel.getGraphPetriNet().getGraphPetriTransitionList().size());
                            }
                            m.go(Double.valueOf(timeModelingTextField.getText()),panelModelSystems.graphPetriNet.getGraphPetriPlaceList().size(), protokolTextArea,name_systems,count_place,count_transfer,name_place);
                            //System.out.println(protokolTextArea.getText());

                            //System.out.println(protokolTextArea.getText());
                            //System.out.println("7777777777777777777777777");
                            //System.out.println(print());

                            //petriNetsPanel.getGraphNet().printStatistics(statisticsTextArea);
                            //перетворення у потрібний формат ...
                            Double d = new Double(petriSim.getTimeCurr()); //added by Inna 3.06.2013
                            Double dd = new Double(100.0 * (petriSim.getTimeCurr() - d.intValue()));  //десяткова частина
                            timeStartField.setText(String.valueOf(
                                    d.intValue() + "." + dd.intValue()
                            )); //added by Inna 3.06.2013
                            panelModelSystems.graphPetriNet=new GraphPetriNet();
                            //panelModelSystems.repaint(); //додано 19.11.2012, можливо не потрібно?
                        }
                    }
                }


        }
    }//GEN-LAST:event_itemRunNetActionPerformed

    private String print()  {
        String s = "";
        System.out.println("********************************************");
        for(int i=0;i<panelModelSystems.graphPetriNet.getGraphPetriPlaceList().size();i++)
        {
            s+=i +" ";
           s+=panelModelSystems.graphPetriNet.getGraphPetriPlaceList().get(i).getPetriPlace().getMark();
           s+="\n";
        }
        return s;
    }

    public void addModel(SystemModel systemModel)
    {
        panelModelSystems.addmodel(systemModel);
    }

    public static void main(String[] args) {
        //GeneticAlgorithmResultFrame p = new GeneticAlgorithmResultFrame();

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        //ArcSystemFrame arcSystemFrame = new ArcSystemFrame();
        //arcSystemFrame.setVisible(true);
        //StatisticsModelFrame s = new StatisticsModelFrame();
        //s.setVisible(true);
       // GeneticAlgorithmAdjustmentFrame pp = new GeneticAlgorithmAdjustmentFrame();

    }




}
