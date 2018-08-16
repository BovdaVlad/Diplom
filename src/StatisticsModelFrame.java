import javax.swing.*;
import java.awt.*;

public class StatisticsModelFrame extends JFrame {

    TableModelStatistic placestatistic = new TableModelStatistic();
    TableModelStatistic transferstatistic = new TableModelStatistic();
    StatisticsModelFrame()
    {
       // super("Статистична інформація");
        this.setSize(1200, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(0, 0);
        setLayout(new GridBagLayout());
        setTitle("Статистична інформація");
        JTable table_place = new JTable(placestatistic);

        JTable table_transfer = new JTable(transferstatistic);

        JLabel label1 = new JLabel("Мітки");
        JLabel label2 = new JLabel("Переходи");

        add(label1, new GridBagConstraints(1, 0, 3, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 1, 0));
        add(table_place, new GridBagConstraints(0, 1, 3, 3, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        add(label2, new GridBagConstraints(1, 4, 3, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        add(table_transfer, new GridBagConstraints(0, 5, 3, 3, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        placestatistic.add("","",2,3,4);
        placestatistic.add("","",2,3,4);
        placestatistic.add("","",2,3,4);
        placestatistic.add("","",2,3,4);
        placestatistic.add("","",2,3,4);

        transferstatistic.add("","",2,3,4);
        transferstatistic.add("","",2,3,4);
        transferstatistic.add("","",2,3,4);
        transferstatistic.add("","",2,3,4);
        pack();
    }


}
