import graphnet.GraphPetriNet;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Document;
import java.awt.*;

public class IndicatorsPanel extends JPanel
{

    GraphPetriNet graphPetriNet;
    MainFrame mainFrame;

    private int index;
    IndicatorsPanel(MainFrame mainFrame)
    {
        setMinimumSize(new Dimension(500,350));
        this.mainFrame=mainFrame;
        setLayout(new GridBagLayout());
    }

    public void setGraphNet(int index)
    {
        this.index=index;
        update();
    }

    public void update()
    {
        this.removeAll();

        double value=0;

        int d =  mainFrame.jSplitPane.getDividerLocation();
        mainFrame.jSplitPane.setDividerLocation(700);
        mainFrame.jSplitPane.setDividerLocation(d);

        int k=0;

        JLabel jlabel = new JLabel("Час роботи");
        add(jlabel,new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        k++;

        SpinnerModel []spinnerModels = new SpinnerModel[mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getPetriTList().size()];

        JSpinner []spinners = new JSpinner[mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().size()];

        for(int i =0;i<mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().size();i++)
        {
            value = mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().get(i).getPetriTransition().getParametr();
            spinnerModels[i]=new SpinnerNumberModel(value,0,10000,0.1);
            spinners[i] = new JSpinner(spinnerModels[i]);
            JLabel label = new JLabel(mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getPetriTList().get(i).getName());
            label.setMaximumSize(new Dimension(300,10));
            spinners[i].setMaximumSize(new Dimension(200,10));
            add(label,new GridBagConstraints(0, k, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            add(spinners[i],new GridBagConstraints(1, k, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            k++;
            int finalI = i;
            spinners[i].addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().get(finalI).getPetriTransition().setTimeServ((double)spinners[finalI].getValue());
                    System.out.println(mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().get(finalI).getName() + "   " +
                            mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().get(finalI).getPetriTransition().getTimeServ());
                }
            });

        }

        JLabel jLabel2 = new JLabel("Вірогідність переходів");
        add(jLabel2,new GridBagConstraints(0, k, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        k++;


        SpinnerModel []spinnerModels1 = new SpinnerModel[mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getPetriTList().size()];

        double dd = 0;
        JSpinner []spinners1 = new JSpinner[mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getPetriTList().size()];
        for(int j=0;j<mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().size();j++)
        {
            dd = mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().get(j).getPetriTransition().getProbability();
            /*
            System.out.println(graphPetriNet.getPetriTList().get(j).getNumber());
            System.out.println(graphPetriNet.getPetriTList().get(j).getNum());
            System.out.println(graphPetriNet.getPetriTList().get(j).getPriority());
            System.out.println(graphPetriNet.getPetriTList().get(j).getBuffer());
            System.out.println(graphPetriNet.getPetriTList().get(j).getProbability());
            System.out.println(graphPetriNet.getPetriTList().get(j).getDistribution());
            System.out.println(graphPetriNet.getPetriTList().get(j).getParametr());
            System.out.println(graphPetriNet.getPetriTList().get(j).getMinTime());
            System.out.println(graphPetriNet.getPetriTList().get(j).getDistributionParamName());
            System.out.println(graphPetriNet.getPetriTList().get(j).getParamDeviation());
            */
            spinnerModels1[j]=new SpinnerNumberModel(dd,0.0,1.0,0.1);
            spinners1[j] = new JSpinner(spinnerModels1[j]);
            JLabel label = new JLabel(mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getPetriTList().get(j).getName());


            add(label,new GridBagConstraints(0, k, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            add(spinners1[j],new GridBagConstraints(1, k, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            k++;
            int finalI = j;
            spinners1[j].addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    SystemModelPanel ss = null;
                    try
                    {
                        ss = mainFrame.panelModelSystems.systemModelPanels.get(index).clone();
                    } catch (CloneNotSupportedException e1)
                    {
                        e1.printStackTrace();
                    }
                    ss.systemModel.graphPetriNet.getGraphPetriTransitionList().get(finalI).getPetriTransition().setProbability((double)spinners1[finalI].getValue());
                    mainFrame.panelModelSystems.systemModelPanels.set(index,ss);  //systemModel.graphPetriNet.getGraphPetriTransitionList().get(finalI).getPetriTransition().setProbability((double)spinners1[finalI].getValue());
                }
            });
        }
        add(new JLabel("Пріорітет"),new GridBagConstraints(0, k, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        k++;
        SpinnerModel []spinnerModels2 = new SpinnerModel[mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().size()];
        JSpinner []spinners2 = new JSpinner[mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().size()];
        for(int j=0;j<mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().size();j++) {

            dd = mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().get(j).getPetriTransition().getPriority();

            spinnerModels2[j]=new SpinnerNumberModel(dd,0.0,9.0,1.0);
            spinners2[j] = new JSpinner(spinnerModels2[j]);
            JLabel label = new JLabel(mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getPetriTList().get(j).getName());
            add(label,new GridBagConstraints(0, k, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            add(spinners2[j],new GridBagConstraints(1, k, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            k++;
            int finalI = j;
            spinners2[j].addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    try {
                        mainFrame.panelModelSystems.systemModelPanels.set(index,mainFrame.panelModelSystems.systemModelPanels.get(index).clone());
                    } catch (CloneNotSupportedException e1) {
                        e1.printStackTrace();
                    }
                    mainFrame.panelModelSystems.systemModelPanels.get(index).systemModel.graphPetriNet.getGraphPetriTransitionList().get(finalI).getPetriTransition().setPriority((int)((double)spinners2[finalI].getValue()));

                }
            });
        }
        this.repaint();
    }
}