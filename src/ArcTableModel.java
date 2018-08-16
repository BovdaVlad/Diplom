
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ArcTableModel extends AbstractTableModel {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<Color> colors = new ArrayList<>();
    ArrayList<Integer> numarcs = new ArrayList<>();

    ArcTableModel() {
        super();
    }

    ArcTableModel(ArrayList<String> names,ArrayList<Color> colors,ArrayList<Integer> numarcs) {
        super();
        this.names = names;
        this.colors = colors;
        this.numarcs=numarcs;

    }

    public void addArc(String name,Color color,Integer num)
    {
        this.names.add(name);
        this.colors.add(color);
        this.numarcs.add(num);
    }

    @Override
    public int getRowCount() {
        return names.size();
    }
    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:

                return names.get(r);
            case 1:
                            JTextField textField = new JTextField();
                            textField.setBackground(colors.get(r));
                            return textField;


            case 2: {
                SpinnerModel spinnerModel = new SpinnerNumberModel((int)numarcs.get(r),1,9,1);
                JSpinner spinner = new JSpinner(spinnerModel);
                return spinner;//numarcs.get(r);
            }


            default:
                return "";
        }

    }

    @Override
    public String getColumnName(int c) {
        String result = "";
        switch (c) {
            case 0:
                result = "Name";
                break;
            case 1:
                result = "Color";
                break;
            case 2:
                result = "NumArc";
                break;
        }
        return result;
    }
    public void clear()
    {
        numarcs.clear();
        colors.clear();
        names.clear();
    }
}
