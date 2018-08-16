/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PetriObj;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
//import points2DVisual_withJchartLib.points2DVisual.DoublePoint;

/**
 * This class provides constructing Petri objective model.<br>
 * List of Petri-objects contains Petri-objects with links between them.<br>
 * For creating Petri-object use class PetriSim. For linking Petri-objects use
 * combining places and passing tokens.<br>
 * Method DoT() of class PetriSim provides programming the passing tokens from
 * the transition of one Petri-object to the place of other.
 *
 * @author Инна
 */
public class PetriObjModel implements Serializable {


    private double time;


    private ArrayList<PetriSim> listObj = new ArrayList<PetriSim>();
    private double t;
    private boolean isProtokolPrint = true;
    private boolean isStatistica = true;


    private ArrayList<Integer> indexplacechoice;
    private int [][] place_count_mark;
    /**
     * Constructs model with given list of Petri objects
     *
     * @param List list of Petri objects
     */
    public PetriObjModel(ArrayList<PetriSim> List) {
        listObj = List;
    }

    /**
     * Set need in protocol
     *
     * @param b is true if protocol is needed
     */
    public void setIsProtokol(boolean b) {
        isProtokolPrint = b;
    }

    /**
     * Set need in statistics
     *
     * @param b is true if statistics is 
     */
    public void setIsStatistica(boolean b) {
        isStatistica = b;
    }

    /**
     *
     * @return the list of Petri objects of model
     */
    public ArrayList<PetriSim> getListObj() {
        return listObj;
    }

    /**
     * Set list of Petri objects
     *
     * @param List list of Petri objects
     */
    public void setListObj(ArrayList<PetriSim> List) {
        listObj = List;
    }

    /**
     * Simulating from zero time until the time equal time modeling
     *
     * @param timeModeling time modeling
     */
    public void go(double timeModeling) {
        
       PetriSim.setTimeMod(timeModeling);   //3.12.2015
        
        t = 0;
        double min;
        listObj.sort(PetriSim.getComparatorByPriority());
        for (PetriSim e :  listObj) { //виправлено 9.11.2015
            e.input();
        }
        if (isProtokolPrint == true) {
            for (PetriSim e : listObj) {
                e.printMark();
            }
        }
        ArrayList<PetriSim> conflictObj = new ArrayList<PetriSim>();
        Random r = new Random();

        while (t < timeModeling) {

            conflictObj.clear();

            min = listObj.get(0).getTimeMin();  //пошук найближчої події

            for (PetriSim e : listObj) {
                if (e.getTimeMin() < min) {
                    min = e.getTimeMin();
                }
            }
            /*  if(min_t<t){ // added 24.06.2013   !!!!Подумать...при отрицательных задержках висит!!!!
             JOptionPane.showMessageDialog(null, "Negative time delay was generated! Check parameters, please/");
             return;
            
             }*/
            if (isStatistica == true) {
                for (PetriSim e : listObj) {
                    e.doStatistica((min - t) / min,min); //статистика за час "дельта т", для спільних позицій потрібно статистику збирати тільки один раз!!!

                }
            }

            t = min; // просування часу

            PetriSim.setTimeCurr(t); // просування часу //3.12.2015
            
            if (isProtokolPrint == true) {
                System.out.println(" Time progress: time = " + t + "\n");
            }
            if (t <= timeModeling) {

                for (PetriSim e : listObj) {
                    if (t == e.getTimeMin()) // розв'язання конфлікту об'єктів рівноймовірнісним способом
                    {
                        conflictObj.add(e);                           //список конфліктних обєктів
                    }
                }
                int num;
                int max;
                if (isProtokolPrint == true) {
                    System.out.println(" List of conflicting objects  " + "\n");
                    for (int ii = 0; ii < conflictObj.size(); ii++) {
                        System.out.println(" K [ " + ii + "  ] = " + conflictObj.get(ii).getName() + "\n");
                    }
                }

                if (conflictObj.size() > 1) { //вибір обєкта, що запускається
                    max = conflictObj.size();
                    
                    conflictObj.sort(PetriSim.getComparatorByPriority());
                    
                    for (int i = 1; i < conflictObj.size(); i++) { //System.out.println("  "+conflictObj.get(i).getPriority()+"  "+conflictObj.get(i-1).getPriority());
                        if (conflictObj.get(i).getPriority() < conflictObj.get(i - 1).getPriority()) {
                            max = i - 1;
                            //System.out.println("max=  "+max);
                            break;
                        }

                    }
                    if (max == 0) {
                        num = 0;
                    } else {
                        num = r.nextInt(max);
                    }
                } else {
                    num = 0;
                }

                if (isProtokolPrint == true) {
                    System.out.println(" Selected object  " + conflictObj.get(num).getName() + "\n" + " NextEvent " + "\n");
                }

                for (PetriSim e: listObj) {
                    if (e.getNumObj() == conflictObj.get(num).getNumObj()) {
                        if (isProtokolPrint == true) {
                            System.out.println(" time =   " + t + "   Event '" + e.getEventMin().getName() + "'\n"
                                    + "                       is occuring for the object   " + e.getName() + "\n");
                        }
                        e.doT();
                        e.stepEvent();

                    }

                }
                if (isProtokolPrint == true) {
                    System.out.println("Markers leave transitions:");
                    for (PetriSim e : listObj) //ДРУК поточного маркірування
                    {
                        e.printMark();
                    }
                }
                listObj.sort(PetriSim.getComparatorByPriority());
                for (PetriSim e : listObj) {
                    //можливо змінились умови для інших обєктів
                    e.input(); //вхід маркерів в переходи Петрі-об'єкта

                }
                if (isProtokolPrint == true) {
                    System.out.println("Markers enter transitions:");
                    for (PetriSim e : listObj){ //ДРУК поточного маркірування
                    
                         e.printMark();
                    }
                }
            }
        }
    }
    
    /**
     * Prints the string in given JTextArea object
     *
     * @param info string for printing
     * @param area specifies where simulation protokol is printed
     */
    private void printInfo(String info, JTextArea area){
        if(isProtokolPrint == true)
            area.append(info);
    }
    /**
     * Prints the quantity for each position of Petri net
     *
     * @param area specifies where simulation protokol is printed
     */
    int yy=0;
    private void printMark(JTextArea area){


        if (isProtokolPrint == true) {
            for (PetriSim e : listObj) {
                for(int h=0;h<place_count_mark.length;h++) {
//                    System.out.print(e.printMark()[h] + " ");
                    if(yy<time)
                    place_count_mark[h][yy] = e.printMark()[h];
                    else
                    {
                        return;
                    }
                }
                area.append(e.printMark().toString());
                System.out.println("-------------------" + yy + "--------------------");
            }
            yy++;
        }
    }

    public void go(double timeModeling,int size_place, JTextArea area, ArrayList<String> name_systems,ArrayList<Integer> count_place,ArrayList<Integer> count_transfer,ArrayList<String> name_place) { //виведення протоколу подій та результатів моделювання у об"єкт класу JTextArea
        place_count_mark = new int [size_place][(int)timeModeling+1];
        time=timeModeling;
        area.setText(" Events protocol ");
        PetriSim.setTimeMod(timeModeling);
        t = 0;
        double min;
        listObj.sort(PetriSim.getComparatorByPriority()); //виправлено 9.11.2015, 12.10.2017
        for (PetriSim e : listObj) { 
            e.input();
        }
        this.printMark(area);
        ArrayList<PetriSim> conflictObj = new ArrayList<PetriSim>();
        Random r = new Random();

        while (t < timeModeling) {

            conflictObj.clear();

            min = Double.MAX_VALUE;  //пошук найближчої події

            for (PetriSim e : listObj) {
                if (e.getTimeMin() < min) {
                    min = e.getTimeMin();
                }
            }
            if (isStatistica == true) {
                for (PetriSim e : listObj) {
                    if (min > 0) {
                        e.doStatistica((min - t) / min,min); //статистика за час "дельта т", для спільних позицій потрібно статистику збирати тільки один раз!!!
                    }
                }
            }

            t = min; // просування часу

          PetriSim.setTimeCurr(t); // просування часу //3.12.2015
            
          
            this.printInfo(" \n Time progress: time = " + t + "\n",area);
            
            if (t <= timeModeling) {

                for (PetriSim e : listObj) {
                    if (t == e.getTimeMin()){ // розв'язання конфлікту об'єктів рівноймовірнісним способом
                    
                        conflictObj.add(e);                           //список конфліктних обєктів
                    }
                }
                int num;
                int max;
                if (isProtokolPrint == true) {
                    area.append("  List of conflicting objects  " + "\n");
                    for (int ii = 0; ii < conflictObj.size(); ii++) {
                        area.append("  K [ " + ii + "  ] = " + conflictObj.get(ii).getName() + "\n");
                    }
                }

                if (conflictObj.size() > 1) //вибір обєкта, що запускається
                {
                    max = conflictObj.size();
                    listObj.sort(PetriSim.getComparatorByPriority());
                    for (int i = 1; i < conflictObj.size(); i++) { //System.out.println("  "+conflictObj.get(i).getPriority()+"  "+conflictObj.get(i-1).getPriority());
                        if (conflictObj.get(i).getPriority() < conflictObj.get(i - 1).getPriority()) {
                            max = i - 1;
                           
                            break;
                        }

                    }
                    if (max == 0) {
                        num = 0;
                    } else {
                        num = r.nextInt(max);
                    }
                } else {
                    num = 0;
                }

               
                this.printInfo(" Selected object  " + conflictObj.get(num).getName() + "\n" + " NextEvent " + "\n",area);
                

                for (PetriSim list : listObj) {
                    if (list.getNumObj() == conflictObj.get(num).getNumObj()) {
                        this.printInfo(" time =   " + t + "   Event '" + list.getEventMin().getName() + "'\n" + "                       is occuring for the object   " + list.getName() + "\n", area);
                        list.doT();
                        list.stepEvent();
                    }
                }
                this.printInfo("Markers leave transitions:", area);
               // this.printMark(area);
                listObj.sort(PetriSim.getComparatorByPriority());
                for (PetriSim e : listObj) {
                    //можливо змінились умови для інших обєктів
                    e.input(); //вхід маркерів в переходи Петрі-об'єкта

                }
                
                this.printInfo("Markers enter transitions:", area);
                this.printMark(area);
            }
        }
        area.append("\n Modeling results: \n");

        StatisticsModelFrame statisticsModelFrame = new  StatisticsModelFrame();


        int index_place=0;
        int place_size = 0;
        int index_transfer=0;

        for (PetriSim e : listObj) {
            area.append("\n Petri-object " + e.getName());
            area.append("\n Mean values of the quantity of markers in places : ");
            for (PetriP P : e.getListPositionsForStatistica()) {
               // area.append("\n  Place '" + P.getName() + "'  " + Double.toString(P.getMean()) + " Max: " + P.getObservedMax() + " Min: "
               // + P.getObservedMin() + " - " + P.getavarege_value(timeModeling));
                area.append("\n  Place '" + P.getName() + "  " + P.getMark() + "  " +"'  " + Double.toString(P.getMean()) + " Average: " + P.getavarege_value(timeModeling));
              //  P.clear();
                statisticsModelFrame.placestatistic.add(name_systems.get(index_place),P.getName(),P.getObservedMax(),P.getObservedMin(),P.getMean());
                place_size++;
                if(count_place.get(index_place)<=place_size)
                {
                    place_size=0;
                    index_place++;
                }
            }
            area.append("\n Mean values of the quantity of active transition channels : ");

            place_size=0;

            for (PetriT T : e.getNet().getListT()) {
                area.append("\n Transition '" + T.getName() + "'  " + Double.toString(T.getMean()) + " Average:  " + T.getavarege_value(timeModeling));
                System.out.println(T.getName());
                if(index_transfer>name_systems.size()-1)
                    break;
                else {
                    statisticsModelFrame.transferstatistic.add(name_systems.get(index_transfer), T.getName(), T.getObservedMax(), T.getObservedMin(), T.getMean());
                    place_size++;

                    if (count_transfer.get(index_transfer) <= place_size) {
                        place_size = 0;
                        index_transfer++;
                    }
                }
            }
        }

        statisticsModelFrame.pack();

        statisticsModelFrame.setVisible(true);
        ChoicePlace choicePlace = new ChoicePlace(name_place);

        choicePlace.addWindowListener(new WindowAdapter() {

           @Override
            public void windowClosed(WindowEvent e) {
                indexplacechoice = choicePlace.indexes_input;
                new Thread(new GraphPlace(place_count_mark,name_place,indexplacechoice,"Place")).start(); //AreaLineChart03 areaLineChart03 = new AreaLineChart03(place_count_mark);
            }

        });
     }

    public class StatisticsModelFrame extends JFrame {

        TableModelStatistic placestatistic = new TableModelStatistic();
        TableModelStatistic transferstatistic = new TableModelStatistic();
        StatisticsModelFrame()
        {

            super("Статистична інформація");

            this.setSize(1200, 700);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setLocation(0, 0);
            setLayout(new GridBagLayout());

            JTable table_place = new JTable(placestatistic);
            JTable table_transfer = new JTable(transferstatistic);

            JScrollPane jscrlp_place = new JScrollPane(table_place);
            JScrollPane jscrlp_transfer = new JScrollPane(table_transfer);

            table_place.setAutoCreateRowSorter(true);
            table_transfer.setAutoCreateRowSorter(true);
            table_place.setPreferredScrollableViewportSize(new Dimension(500, 250));
            table_transfer.setPreferredScrollableViewportSize(new Dimension(500, 250));
            JLabel label1 = new JLabel("Мітки");
            JLabel label2 = new JLabel("Переходи");

            add(label1, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 1, 0));
            add(jscrlp_place, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));

            add(label2, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            add(jscrlp_transfer, new GridBagConstraints(0, 3, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));

            pack();
        }
    }


    public class TableModelStatistic extends AbstractTableModel {

        ArrayList<String> systems = new ArrayList<>();
        ArrayList<String> positions = new ArrayList<>();
        ArrayList<Double> max = new ArrayList<>();
        ArrayList<Double> min = new ArrayList<>();
        ArrayList<Double> averages = new ArrayList<>();

        TableModelStatistic() {
            super();
        }
        TableModelStatistic(ArrayList<String> systems,ArrayList<String> positions,ArrayList<Double> max,ArrayList<Double> min,ArrayList<Double> averages )
        {
            super();
            this.systems=systems;
            this.positions=positions;
            this.max = max;
            this.min=min;
            this.averages = averages;
        }

        public void add(String system_name,String position,double max, double min, double average)
        {
            systems.add(system_name);
            positions.add(position);
            this.max.add(max);
            this.min.add(min);
            this.averages.add(average);
        }

        @Override
        public int getRowCount() {
            return systems.size();
        }
        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int r, int c) {
            switch (c) {
                case 0:
                    return systems.get(r);
                case 1:
                    return positions.get(r);
                case 2: return max.get(r);
                case 3: return min.get(r);
                case 4: return averages.get(r);
                default:
                    return "";
            }

        }
        @Override
        public String getColumnName(int c) {
            String result = "";
            switch (c) {
                case 0:
                    result = "Система";
                    break;
                case 1:
                    result = "Позиція";
                    break;
                case 2:
                    result = "Максимум";
                    break;
                case 3:
                    result = "Мінімум";
                    break;
                case 4:
                    result = "Середнє";
                    break;
            }
            return result;
        }
        public void clear()
        {
            systems.clear();
            averages.clear();
            min.clear();
            max.clear();

        }
    }


}
