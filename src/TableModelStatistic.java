import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

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
