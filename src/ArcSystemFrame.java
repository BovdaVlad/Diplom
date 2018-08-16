import javafx.scene.control.ChoiceBox;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashSet;

public class ArcSystemFrame extends JFrame {

    private ArrayList<String> names_out = new ArrayList<>();
     ArrayList<Integer> index_out = new ArrayList<>();

    private ArrayList<String> names_inp = new ArrayList<>();
     ArrayList<Integer> index_inp = new ArrayList<>();

    public JTextField time = new JTextField("1.0");

    private SpinnerModel modelpriority = new SpinnerNumberModel(1,1,9,1);
    JSpinner priority = new JSpinner(modelpriority);
    private SpinnerModel probabilitymodel = new SpinnerNumberModel(1,0,1,0.1);
    JSpinner probability = new JSpinner(probabilitymodel);
    private JComboBox input = new JComboBox();
    private JComboBox output = new JComboBox();

    public JList<String> inp;
    public JList<String> out;
///////////////// индексы в самом списке
    ArrayList<Integer>indexes_input = new ArrayList<>();
    ArrayList<Integer>indexes_out = new ArrayList<>();

    private Object [][]data=new Object[2][3];
    private DefaultListModel defaultListModel_inp = new DefaultListModel();
    private DefaultListModel defaultListModel_out = new DefaultListModel();

    private SystemModel systemModel_int;
    private SystemModel systemModel_out;

    JTable table = new JTable();
    ArcSystemFrame(SystemModel systemModel_int,SystemModel systemModel_out) {

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(0, 0);

        this.systemModel_int = systemModel_int;
        this.systemModel_out = systemModel_out;

        for(String s_inp: systemModel_int.output) {
            defaultListModel_inp.addElement(s_inp);
        }

        for(String s_out: systemModel_out.input) {
            defaultListModel_out.addElement(s_out);
        }


        inp = new JList<>();
        inp.setLayoutOrientation(JList.VERTICAL);
        inp.setModel(defaultListModel_inp);

        inp.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                indexes_input.clear();
                for(int i=0;i<inp.getSelectedIndices().length;i++)
                    indexes_input.add(Integer.valueOf(inp.getSelectedIndices()[i]));
                update_data();

            }
        });
        JScrollPane inp_scrole = new JScrollPane(inp);
        inp_scrole.setPreferredSize(new Dimension(50,55));
///////////////////////////////////////////////////////////////
        out = new JList<>();
        out.setLayoutOrientation(JList.VERTICAL);
        out.setModel(defaultListModel_out);

        out.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                indexes_out.clear();
                for(int i=0;i<out.getSelectedIndices().length;i++) {
                    indexes_out.add(Integer.valueOf(out.getSelectedIndices()[i]));
                    System.out.print(Integer.valueOf(out.getSelectedIndices()[i]) + " ");
                }
                //System.out.println();
                update_data();
            }
        });

        JScrollPane out_scrole = new JScrollPane(out);
        out_scrole.setPreferredSize(new Dimension(50,55));

        setLayout(new GridBagLayout());
        JLabel[] labels = new JLabel[5];
        labels[0] = new JLabel("Час ");
        labels[1] = new JLabel("Пріорітет ");
        labels[2] = new JLabel("Вірогідність");
        labels[3] = new JLabel("Вихід");
        labels[4] = new JLabel("Вхід");


        JComboBox<String> combo = new JComboBox<String>(new String[]{"Менеджер", "Программист", "Водитель"});
        combo.setSelectedIndex(0);

        add(labels[0], new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        add(time, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        add(labels[1], new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        add(priority, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        add(labels[2], new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        add(probability, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        add(labels[3], new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        add(labels[4], new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        add(inp_scrole, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        add(out_scrole, new GridBagConstraints(1, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        String[] strings = {"Name", "Color", "NumArc"};

        update_data();

        //table.setModel(new DefaultTableModel());
        table = new JTable(data, strings);

        //Устаналиваем размеры прокручиваемой области
        //table.setPreferredScrollableViewportSize(new Dimension(600, 200));
        table.setAutoCreateRowSorter(true);

        //arcTableModel.fireTableDataChanged();
       // table.getCellEditor(0,0).s
        /*
        DefaultTableModel model = new DefaultTableModel(data, strings) {
            // Функция получения типа столбца
            public Class<?> getColumnClass(int column) {
                return data[0][column].getClass();
            }
        };
*/


// Редактор ячейки с раскрывающимся списком
//        JSpinner spinner = new JSpinner(model1);


        //SpinnerEditor editor = new SpinnerEditor();
// Определение редактора ячеек для колонки
        TableColumnModel tcm = table.getColumnModel();
        TableColumn tc = tcm.getColumn(2);


        tc.setCellEditor(new SpinnerEditor());
        //table.getColumnModel().getColumn(1).setCellEditor(editor);
        table.getColumnModel().getColumn(2).setCellEditor(new SpinnerEditor());
        //table.setCellEditor(new SpinnerEditor());
        //JScrollPane jscrlp = new JScrollPane(table);
        add(table, new GridBagConstraints(0, 5, 3, 4, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));



        pack();
    }

    public void update_data()
    {

        String[] strings = {"Name", "Color", "NumArc"};

        int num = indexes_input.size() + indexes_out.size();

        data = new Object[num][3];

        int i=0;
        for(i =0; i<indexes_input.size();i++)
        {

            data[i][0]=systemModel_int.output.get((indexes_input.get(i)));
            data[i][1]="Вихід";
            data[i][2]=1;

            //hashSet.add(new DataInputOutput(systemModel_int.output.get((indexes_input.get(i))),"Вихід",5));
        }
        for(int j = i; j<num;j++)
        {

            data[j][0]=systemModel_out.input.get((indexes_out.get(j-i)));
            data[j][1]="Вхід";
            data[j][2]=1;

            //hashSet.add(new DataInputOutput(systemModel_out.input.get((indexes_out.get(j-i))),"Вхід",5));
        }
/*
        Object [][]f = new Object[num][3];
        int t=0;
        if(num!=0)
        {
            for (Object d : hashSet) {
                f[t][0] = ((DataInputOutput) hashSet.iterator().next()).getName();
                f[t][1] = ((DataInputOutput) hashSet.iterator().next()).getType();
                f[t][2] = ((DataInputOutput) hashSet.iterator().next()).getCount();
                t++;
            }
        }
        */
            //System.out.println("size data: " + data.length);
            remove(table);
            table = new JTable(data, strings);

            this.add(table, new GridBagConstraints(0, 5, 3, 4, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));

            pack();
        table.getColumnModel().getColumn(2).setCellEditor(new SpinnerEditor());
/*
        DefaultTableModel defaultTableModel = (DefaultTableModel)table.getModel();
        defaultTableModel.fireTableDataChanged();*/
    }
    public void setCombobox(ArrayList<String> names_out,ArrayList<Integer> index_out, ArrayList<String>names_inp ,ArrayList<Integer> index_inp)
    {
        this.names_out = names_out;
        this.names_inp=names_inp;
        this.index_inp=index_inp;
        this.index_out=index_out;
        //input.removeAll();
        output.removeAllItems();
        input.removeAllItems();
        for(String s: names_out)
        {
            input.addItem(s);
        }
        for(String s: names_inp)
        {
            output.addItem(s);
        }

    }


    public static void main( String[] args )
        {
           // ArcSystemFrame a = new ArcSystemFrame();
           // a.setVisible(true);
       /* JFrame frame = new JFrame();
        JTable table = new JTable(data,columnNames);
        //table.setSurrendersFocusOnKeystroke(true);
        TableColumnModel tcm = table.getColumnModel();
        TableColumn tc = tcm.getColumn(1);


        tc.setCellEditor(new SpinnerEditor());

        TableCellEditor tableCellEditor = table.getCellEditor(0,0);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(table,BorderLayout.SOUTH);
        JButton button = new JButton("dd");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    System.out.println(table.getModel().getValueAt(0,1).toString());
                    System.out.println(table.getModel().getValueAt(1,1).toString());
                }
            });
        frame.add(button,BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
        */
    }

    static class SpinnerEditor extends DefaultCellEditor
    {
            JSpinner spinner;
            JSpinner.DefaultEditor editor;
            JTextField textField;
            boolean valueSet;
            SpinnerModel model;
            // Initializes the spinner.
            public SpinnerEditor()
            {
                super(new JTextField());
                model = new SpinnerNumberModel(1,1,9,1);
                spinner = new JSpinner(model);
                editor = ((JSpinner.DefaultEditor)spinner.getEditor());
                textField = editor.getTextField();
                textField.addFocusListener( new FocusListener() {
                    public void focusGained( FocusEvent fe ) {
                        System.err.println("Got focus");
                        //textField.setSelectionStart(0);
                        //textField.setSelectionEnd(1);
                        SwingUtilities.invokeLater( new Runnable() {
                            public void run() {
                                if ( valueSet ) {
                                    textField.setCaretPosition(1);
                                }
                            }
                        });
                    }
                    public void focusLost( FocusEvent fe ) {
                    }
                });
                textField.addActionListener( new ActionListener() {
                    public void actionPerformed( ActionEvent ae ) {
                        stopCellEditing();
                    }
                });
            }

            // Prepares the spinner component and returns it.
            public Component getTableCellEditorComponent(
                    JTable table, Object value, boolean isSelected, int row, int column
            ) {
                if ( !valueSet ) {
                    spinner.setValue(value);
                }
                SwingUtilities.invokeLater( new Runnable() {
                    public void run() {
                        textField.requestFocus();
                    }
                });
                return spinner;
            }

            public boolean isCellEditable( EventObject eo ) {
                System.err.println("isCellEditable");
                if ( eo instanceof KeyEvent) {
                    KeyEvent ke = (KeyEvent)eo;
                    System.err.println("key event: "+ke.getKeyChar());
                    textField.setText(String.valueOf(ke.getKeyChar()));
                    //textField.select(1,1);
                    //textField.setCaretPosition(1);
                    //textField.moveCaretPosition(1);
                    valueSet = true;
                } else {
                    valueSet = false;
                }
                return true;
            }

            // Returns the spinners current value.
            public Object getCellEditorValue() {
                return spinner.getValue();
            }

            public boolean stopCellEditing() {
                System.err.println("Stopping edit");
                try {
                    editor.commitEdit();
                    spinner.commitEdit();
                } catch ( java.text.ParseException e ) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid value, discarding.");
                }
                return super.stopCellEditing();
            }
        }
    }



