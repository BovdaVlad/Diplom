package PetriObj;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChoicePlace extends JFrame {
    private JList<String> inp;
    private DefaultListModel defaultListModel_inp = new DefaultListModel();
    ArrayList<Integer> indexes_input = new ArrayList<>();

    ChoicePlace(ArrayList<String> name) {

        setLayout(new GridBagLayout());
        inp = new JList<>();
        for (String s_inp : name) {
            defaultListModel_inp.addElement(s_inp);
        }
        inp.setLayoutOrientation(JList.VERTICAL);
        inp.setModel(defaultListModel_inp);

        inp.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                indexes_input.clear();
                for (int i = 0; i < inp.getSelectedIndices().length; i++)
                    indexes_input.add(Integer.valueOf(inp.getSelectedIndices()[i]));
            }
        });

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(0, 0);
        this.setTitle("Вибір позицій для детального перегляду");
        JScrollPane inp_scrole = new JScrollPane(inp);
        inp_scrole.setPreferredSize(new Dimension(400, 400));

        add(inp_scrole,new GridBagConstraints(0, 0, 1, 4, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        JButton close = new JButton("Закрити");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(close,new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setVisible(true);
    }
}

