import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class ArcSystem {

    private SystemModelPanel systemModelPanel_input;
    private SystemModelPanel systemModelPanel_output;
    private int index_input;
    private int index_output;
    private int x1,x2,y1,y2;
    private Line2D.Float line;
    ArcSystemFrame arcSystemFrame;

    private double time;
    private int priority;
    private double probability;

    ArrayList<Integer>indexes_input = new ArrayList<>();
    ArrayList<Integer>indexes_out = new ArrayList<>();

    ArrayList<Integer> numarc = new ArrayList<>();
   /* ArcSystem(SystemModelPanel systemModelPanel)
    {
        systemModelPanel_output=systemModelPanel;
        arcSystemFrame = new ArcSystemFrame();
    }
*/
    ArcSystem(SystemModelPanel systemModelPanel_output ,SystemModelPanel systemModelPanel_input)
    {
        arcSystemFrame = new ArcSystemFrame(systemModelPanel_output.systemModel,systemModelPanel_input.systemModel);

        priority = 1;
        probability=1;
        time=0;
        indexes_input.add(0);
        indexes_out.add(0);
        arcSystemFrame.index_out.add(0);
        arcSystemFrame.index_inp.add(0);
        arcSystemFrame.inp.setSelectedIndex(0);
        arcSystemFrame.out.setSelectedIndex(0);
        //arcSystemFrame.indexes_out.add(0);
        //arcSystemFrame.indexes_input.add(0);
        //arcSystemFrame.update_data();
       // indexes_input = arcSystemFrame.index_inp;
       // indexes_out = arcSystemFrame.index_out;

        this.systemModelPanel_output= systemModelPanel_output;
        this.systemModelPanel_input = systemModelPanel_input;
        index_input = systemModelPanel_input.getIndex();
        index_output = systemModelPanel_output.getIndex();
        arcSystemFrame.update_data();
        for(int i=0;i<arcSystemFrame.table.getRowCount();i++)
        {
            numarc.add((int)arcSystemFrame.table.getValueAt(i,2));
        }
        openFrame();
        arcSystemFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                    priority = (int)arcSystemFrame.priority.getValue();
                    probability = (double)arcSystemFrame.probability.getValue();
                    indexes_input = arcSystemFrame.index_inp;
                    indexes_out = arcSystemFrame.index_out;
                    time=Double.valueOf(arcSystemFrame.time.getText());
                    System.out.println("time = !!!" + time );
                    for(int i=0;i<arcSystemFrame.table.getRowCount();i++)
                    {
                        numarc.add((Integer)arcSystemFrame.table.getValueAt(i,2));
                    }
                }
        });



        line = new Line2D.Float(systemModelPanel_output.getX(),systemModelPanel_output.getY(),systemModelPanel_input.getX(),systemModelPanel_input.getY());

    }

    public int getIndex_input() {
        return index_input;
    }

    public int getIndex_output() {
        return index_output;
    }

    public Line2D.Float getLine() {

        int x1 = systemModelPanel_output.getX();
        int y1 = systemModelPanel_output.getY();
        int x2 = systemModelPanel_input.getX();
        int y2 = systemModelPanel_input.getY();

        int h = systemModelPanel_input.getHeight()/4;
        int w = systemModelPanel_input.getWidth()/4;

        if(systemModelPanel_output.getX()+systemModelPanel_output.getWidth()<systemModelPanel_input.getX()) // первая панель слева
        {
            x1 += systemModelPanel_output.getWidth();

            if(Math.abs(systemModelPanel_output.getY()-systemModelPanel_input.getY())>6*h) {
                if (systemModelPanel_output.getY() > systemModelPanel_input.getY())

                //systemModelPanel_input.getY()+systemModelPanel_input.getHeight()>systemModelPanel_output.getY())
                {
                    y1 += systemModelPanel_output.getHeight() * 1/ 4;
                    y2 += systemModelPanel_input.getHeight() * 3/ 4;
                }
            else  //(systemModelPanel_output.getY() < systemModelPanel_input.getY() && systemModelPanel_output.getY() - systemModelPanel_input.getY() > 6 * h)
                {
                    y1 += systemModelPanel_output.getHeight() * 3 / 4;
                    y2 += systemModelPanel_input.getHeight() * 1/ 4;
                }
            }
            else
            {
                y1 += systemModelPanel_output.getHeight() / 2;
                y2 += systemModelPanel_input.getHeight() / 2;
            }

        }
        else if(systemModelPanel_output.getX()>systemModelPanel_input.getX()+systemModelPanel_input.getWidth()) // первая панель справа
        {
            x2+=systemModelPanel_input.getWidth();

            if(Math.abs(systemModelPanel_output.getY()-systemModelPanel_input.getY())>6*h) {
                if (systemModelPanel_output.getY() > systemModelPanel_input.getY())

                //systemModelPanel_input.getY()+systemModelPanel_input.getHeight()>systemModelPanel_output.getY())
                {
                    y1 += systemModelPanel_output.getHeight() * 1/ 4;
                    y2 += systemModelPanel_input.getHeight() * 3/ 4;
                }
                else  //(systemModelPanel_output.getY() < systemModelPanel_input.getY() && systemModelPanel_output.getY() - systemModelPanel_input.getY() > 6 * h)
                {
                    y1 += systemModelPanel_output.getHeight() * 3 / 4;
                    y2 += systemModelPanel_input.getHeight() * 1/ 4;
                }
            }
            else
            {
                y1 += systemModelPanel_output.getHeight() / 2;
                y2 += systemModelPanel_input.getHeight() / 2;
            }
        }
        else if(systemModelPanel_output.getY()+systemModelPanel_output.getHeight()<systemModelPanel_input.getY()) // первая панель сверху
        {
            y1+=systemModelPanel_output.getHeight();
            if(Math.abs(systemModelPanel_output.getX()-systemModelPanel_input.getX())<w) {
                if (systemModelPanel_output.getX() > systemModelPanel_input.getX())

                //systemModelPanel_input.getY()+systemModelPanel_input.getHeight()>systemModelPanel_output.getY())
                {
                    x1 += systemModelPanel_output.getWidth() * 1 / 4;
                    x2 += systemModelPanel_input.getWidth() * 3 / 4;
                } else  //(systemModelPanel_output.getY() < systemModelPanel_input.getY() && systemModelPanel_output.getY() - systemModelPanel_input.getY() > 6 * h)
                {
                    x1 += systemModelPanel_output.getWidth() * 3 / 4;
                    x2 += systemModelPanel_input.getWidth() * 1 / 4;
                }
            }
            else
            {
                x1 += systemModelPanel_output.getWidth() / 2;
                x2 += systemModelPanel_input.getWidth() / 2;
            }
        }
        else // первая панель снизу
        {
            y2+=systemModelPanel_input.getHeight();

            if(Math.abs(systemModelPanel_output.getX()-systemModelPanel_input.getX())<w) {
                if (systemModelPanel_output.getX() > systemModelPanel_input.getX())
                {
                    x1 += systemModelPanel_output.getWidth() * 1 / 4;
                    x2 += systemModelPanel_input.getWidth() * 3 / 4;
                }
                else
                {
                    x1 += systemModelPanel_output.getWidth() * 3 / 4;
                    x2 += systemModelPanel_input.getWidth() * 1 / 4;
                }
            }
            else
            {
                x1+=systemModelPanel_output.getWidth()/2;
                x2+=systemModelPanel_input.getWidth()/2;
            }
        }
        line = new Line2D.Float(x1,y1,x2,y2);
        return line;
    }

    public void setLine(Line2D.Float line) {
        this.line = line;
    }

    public SystemModelPanel getSystemModelPanel_input() {
        return systemModelPanel_input;
    }

    public void setSystemModelPanel_input(SystemModelPanel systemModelPanel_input) {
        this.systemModelPanel_input = systemModelPanel_input;
        this.x1 = systemModelPanel_input.getX();
        this.y1 = systemModelPanel_input.getY();
    }

    public SystemModelPanel getSystemModelPanel_output() {
        return systemModelPanel_output;
    }

    public void setSystemModelPanel_output(SystemModelPanel systemModelPanel_output) {
        this.systemModelPanel_output = systemModelPanel_output;
        this.x2 = systemModelPanel_output.getX();
        this.y2 = systemModelPanel_output.getY();
    }

    public double getTime() {
        return time;
    }

    public int getPriority() {
        return priority;
    }

    public double getProbability() {
        return probability;
    }

    public void openFrame()
    {
        Color rcolor = new Color(255,0,0);
        Color gcolor = new Color(0,255,0);
        ArrayList<String> names_out = new ArrayList<>();
        ArrayList<Integer> index_out = new ArrayList<>();
        for(int i = 0;i<systemModelPanel_output.systemModel.graphPetriNetpaint.getGraphPetriPlaceList().size();i++)
        {
            if(systemModelPanel_output.systemModel.graphPetriNetpaint.getGraphPetriPlaceList().get(i).getColor().equals(rcolor))
            {
                names_out.add(systemModelPanel_output.systemModel.graphPetriNetpaint.getGraphPetriPlaceList().get(i).getName());
                index_out.add(systemModelPanel_output.systemModel.graphPetriNetpaint.getGraphPetriPlaceList().get(i).getNumber());
            }
        }
/////////////////////////////////////////////////////////
        ArrayList<String> names_inp = new ArrayList<>();
        ArrayList<Integer> index_inp = new ArrayList<>();
        for(int i = 0;i<systemModelPanel_input.systemModel.graphPetriNetpaint.getGraphPetriPlaceList().size();i++)
        {
            if(systemModelPanel_input.systemModel.graphPetriNetpaint.getGraphPetriPlaceList().get(i).getColor().equals(gcolor))
            {
                names_inp.add(systemModelPanel_input.systemModel.graphPetriNetpaint.getGraphPetriPlaceList().get(i).getName());
                index_inp.add(systemModelPanel_input.systemModel.graphPetriNetpaint.getGraphPetriPlaceList().get(i).getNumber());
            }
        }
        arcSystemFrame.setCombobox(names_out,index_out,names_inp,index_inp);

        //arcSystemFrame.setVisible(true);
    }
/*
    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
*/

}
