import PetriObj.PetriT;
import graphnet.GraphPetriNet;
import graphnet.GraphPetriTransition;
import graphpresentation.PetriNetsPanel;
import sun.swing.ImageIconUIResource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SystemModelPanel extends JPanel implements Cloneable {
    private int sizeimage = 100;
    private BufferedImage image = new BufferedImage(sizeimage, sizeimage, BufferedImage.TYPE_INT_ARGB);
    GraphPetriNet netpaint;

    private int index;
    private int countplace;
    private int x_,y_;
    Image imagee;
    MainFrame mainFrame;
    SystemModel systemModel;

    public int getX_() {
        return x_;
    }

    public void setX_(int x_) {
        this.x_ = x_;
    }

    public int getY_() {
        return y_;
    }

    public void setY_(int y_) {
        this.y_ = y_;
    }

    @Override
    protected SystemModelPanel clone() throws CloneNotSupportedException { // 30.11.2015

        SystemModelPanel systemModelPanel_clone = new SystemModelPanel();
        systemModelPanel_clone.setMainFrame(mainFrame);
        systemModelPanel_clone.setIndex(index);

        systemModelPanel_clone.sizeimage=sizeimage;
        systemModelPanel_clone.image=image;
        systemModelPanel_clone.netpaint=netpaint;
        systemModelPanel_clone.countplace=countplace;

        systemModelPanel_clone.systemModel = systemModel.clone();

        return  systemModelPanel_clone;
    }

    SystemModelPanel()
    {

    }

    public void setMainFrame(MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }



    SystemModelPanel(SystemModel systemModel,MainFrame mainFrame,int index)
    {
        setToolTipText(systemModel.getName());

        this.index = index;
        netpaint=systemModel.graphPetriNetpaint;
        countplace = netpaint.getGraphPetriPlaceList().size();
        this.setBackground(Color.WHITE);


        this.setSize(sizeimage+20,sizeimage+20);
        this.mainFrame = mainFrame;

        this.setPreferredSize(new Dimension(sizeimage,sizeimage+20));

        this.systemModel = systemModel;

        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(sizeimage,sizeimage));
        label.setSize(sizeimage,sizeimage);
       // ImageIcon a = new ImageIcon("");
        int fontsize=1;
        if (systemModel.getName().length()>25)
        {
            fontsize = 6;
        }
        else if (systemModel.getName().length()>20)
        {
            fontsize = 7;
        }
        else if (systemModel.getName().length()>15)
        {
            fontsize = 8;
        }
        else if (systemModel.getName().length()>15)
        {
            fontsize = 9;
        }
        else if (systemModel.getName().length()>10)
        {
            fontsize = 10;
        }
        else
        {
            fontsize = 11;
        }
        Font f = new Font("Serif",Font.TYPE1_FONT,fontsize);
        JLabel label1 = new JLabel(systemModel.getName());
        label1.setFont(f);
        add(label1);
        add(label);
        System.out.println("0000");
        try {
            BufferedImage image = getImage(new File("D:\\Diplom\\src\\file_image\\" +systemModel.getGraphPetriNet().getImage().getName()));
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(label.getWidth(), label.getHeight(), BufferedImage.SCALE_DEFAULT));
            label.setIcon(imageIcon);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        //label.setIcon(new ImageIcon("C:\\Users\\Александр\\Desktop\\pr.png"));

        label.setPreferredSize(new Dimension(100,100));


        /*
        try {
            imagee = ImageIO.read(new File("C:\\Users\\Александр\\Desktop\\pr.png"));
            image =ImageIO.read(new File("C:\\Users\\Александр\\Desktop\\pr.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        //getGraphics().drawImage(getImage(new File("C:\\Users\\Александр\\Desktop\\pr.png")), 0, 0, null);


        //repaint();


        //mainFrame.panelModelSystems.remove(SystemModelPanel.this);
        //repaint();
        //mainFrame.panelModelSystems.repaint();
        //repaint();

        /*
        setLayout(new GridBagLayout());
        repaint();
        int k=0;
        add(new JLabel(systemModel.name), new GridBagConstraints(0, k, 2, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        k++;
        // ресурси
        if(systemModel.resource.size()>0) {
            add(new JLabel("Ресурси"), new GridBagConstraints(0, k, 2, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 0, 0));

            SpinnerModel []values = new SpinnerNumberModel[systemModel.resource.size()];
            JSpinner []spinners=new JSpinner[systemModel.resource.size()];
            k++;

            for(int i=0;i<systemModel.resource.size();i++)
            {
                int v = systemModel.numresource.get(i);
                values[i] = new SpinnerNumberModel(v,1,100,1);
                spinners[i]=new JSpinner(values[i]);
                add(new JLabel(systemModel.resource.get(i)),new GridBagConstraints(0, k, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
                add(spinners[i],new GridBagConstraints(1, k, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
                k++;
            }
        }
// на вхід
        if(systemModel.input.size()>0) {
            add(new JLabel("Вхід"), new GridBagConstraints(0, k, 2, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 0, 0));


            k++;
            for(int i=0;i<systemModel.input.size();i++)
            {

                add(new JLabel(systemModel.input.get(i)),new GridBagConstraints(0, k, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));

                /*
                JLabel label = new JLabel();
                label.setIcon(new ImageIcon(getImage(systemModel.fileinput.get(i))));
                add(label,new GridBagConstraints(1, k, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
                        */
/*                k++;
            }
        }


        // на вихід
        if(systemModel.output.size()>0) {
            add(new JLabel("Вихід"), new GridBagConstraints(0, k, 2, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 0, 0));
            k++;
            for(int i=0;i<systemModel.output.size();i++)
            {

                add(new JLabel(systemModel.output.get(i)),new GridBagConstraints(0, k, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));

                /*
                JLabel label = new JLabel();
                label.setIcon(new ImageIcon(getImage(systemModel.fileoutput.get(i))));
                add(label,new GridBagConstraints(1, k, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
                        */
/*                k++;
            }
        }*/
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCountplace() {
        return countplace;
    }

    public void setCountplace(int countplace) {
        this.countplace = countplace;
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        //g.drawImage(getImage(new File("C:\\Users\\Александр\\Desktop\\pr.png")), 0, 0, null);

        setMinimumSize(new Dimension(100,100));
        //mainFrame.panelModelSystems.repaint();
    }

    private BufferedImage getImage(File file)
    {
        try {
            image = ImageIO.read(file);
           // System.out.println(image.getWidth() + " - " + image.getHeight());
        } catch (IOException ex) {
            System.out.println("dssd");
        }
       // AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(0.5, 0.5 ), null);

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(1 / ((double) image.getWidth() / sizeimage), 1 / ((double) image.getHeight() / sizeimage)), null);
        image = op.filter(image, null);
       // System.out.println("новый размер " + image.getHeight() + " " + image.getWidth() + "  - ");
        return image;
    }
    public void setL()
    {

    }
}
