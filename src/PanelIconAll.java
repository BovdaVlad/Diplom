import graphnet.GraphPetriNet;
import graphnet.GraphPetriPlace;
import graphpresentation.FileUse;
import graphpresentation.PetriNetsFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PanelIconAll extends JPanel {
    private BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
    private int sizeimage = 30;

    private int xposition=0;
    private int yposition=0;

    private ArrayList<JLabel> iconsystems = new ArrayList<>();
    private ArrayList<SystemModel> systemModels = new ArrayList<>();

    private ArrayList<String> paths = new ArrayList<>();

    private MainFrame mainFrame;

    private int []indexes;
    private ArrayList<Integer> contain;
    PanelIconAll(MainFrame mainFrame)
    {
        contain=new ArrayList<>();
        this.mainFrame=mainFrame;

        addMouseListener(new MouseHandler());

        setLayout(new FlowLayout());


       // paths.add("C:\\Users\\Александр\\Desktop\\img\\a.pns");
       // paths.add("C:\\Users\\Александр\\Desktop\\img\\bb.pns");
/*
        paths.add("src\\systems\\Борошно мала компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Борошно велика компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Дріжжі.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Цукор середня компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Цукор велика компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Сіль мала компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Сіль середня компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Олія середня компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Олія мала компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Вода велика компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Полуниця велика компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Абрикос середня компанія.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\склад.pns");

        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Заміс та розділення тіста.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Випічка.pns");
        paths.add("C:\\Users\\Александр\\Desktop\\системы\\Упаковка.pns");*/

        File folder = new File("src\\systems");
        File []folderEnries = folder.listFiles();
        for(File entry : folderEnries)
        {
            if(!entry.isDirectory())
            {
               paths.add(entry.getPath());
            }
        }



        indexes = new int[paths.size()];


        this.setBackground(Color.pink);

        JLabel []labels = new JLabel[paths.size()];

        for(int i =0;i<paths.size();i++)
        {
            labels[i] = downloadPetriNet(i);
            add(labels[i]);
        }


    }

    private JLabel downloadPetriNet(int index) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        GraphPetriNet net = new GraphPetriNet();
        try {
            fis = new FileInputStream(paths.get(index));
            ois = new ObjectInputStream(fis);
            net = ((GraphPetriNet) ois.readObject()).clone();  //створюємо копію, щоб відновити стан усіх класів з усіма номерами...
            //String name = net.getPetriNet().getName();
            ois.close();


        } catch (FileNotFoundException e) {
            System.out.println("Such file was not found");
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(PetriNetsFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(FileUse.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(PetriNetsFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {
                return null;
            }

            try {
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(PetriNetsFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException e) {
                return null;
            }

        }
        try {
            System.out.println((net.getImage()));
            image = ImageIO.read(new File("src\\file_image\\" +(net.getImage())));
//            System.out.println(image.getWidth() + " - " + image.getHeight());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(1 / ((double) image.getWidth() / sizeimage), 1 / ((double) image.getHeight() / sizeimage)), null);
        image = op.filter(image, null);
/*
        try
        {
            image = ImageIO.read(new File("C:\\Users\\Александр\\Desktop\\4.png"));
            //System.out.println(image.getWidth() + " - " + image.getHeight());
        }
        catch (IOException ex)
        {
            System.out.println("dssd");
        }
        */
        //image = new Image(new File(""));
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));

        GraphPetriNet finalNet = net;
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                String name = finalNet.getPetriNet().getName();
                ArrayList<String> r = new ArrayList<>();
                ArrayList<String> inp = new ArrayList<>();
                ArrayList<String> out = new ArrayList<>();
                ArrayList<File> fileinp = new ArrayList<>();
                ArrayList<File> fileout = new ArrayList<>();
                ArrayList<Integer> numr = new ArrayList<>();

                ArrayList<Integer> index_inp = new ArrayList<>();
                ArrayList<Integer> index_out = new ArrayList<>();

                ArrayList<GraphPetriPlace> place = finalNet.getGraphPetriPlaceList();
                Color rcolor = new Color(255,0,0);
                Color bcolor = new Color(0,0,255);
                Color gcolor = new Color(0,255,0);
                for (GraphPetriPlace petriPlace : place)
                {

                   // System.out.println(petriPlace.getColor());
                    if(petriPlace.getColor().equals(rcolor))
                    {
                        out.add(petriPlace.getName());
                        index_out.add(petriPlace.getNumber());
                    }
                    else if(petriPlace.getColor().equals(gcolor))
                    {
                        inp.add(petriPlace.getName());
                        index_inp.add(petriPlace.getNumber());
                    }
                    else if(petriPlace.getColor().equals(bcolor))
                    {
                        r.add(petriPlace.getName());
                       // System.out.println(petriPlace.getNumber());
                        numr.add(petriPlace.getPetriPlace().getMark());
                    }
                    else
                    {

                    }
                }

                //fileout.add(new File("C:\\Users\\Александр\\Desktop\\3.png"));
                //fileout.add(new File("C:\\Users\\Александр\\Desktop\\4.png"));

                SystemModel systemModel = new SystemModel(name, r, inp,index_inp, out,index_out, numr, fileinp, fileout);
                systemModel.setGraphPetriNet(finalNet);
                if(contain.contains(index))
                {
                    indexes[index]++;
                    systemModel.setName(systemModel.getName()+indexes[index]);
                }
                contain.add(index);

                mainFrame.addModel(systemModel);
            }
        });

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.modelFrame.setGraphNet(finalNet);
                mainFrame.modelFrame.repaint();

                mainFrame.indicatorsPanel.setGraphNet(contain.size()-1);
                mainFrame.indicatorsPanel.update();
            }
        });
        return label;
    }




    private class MouseHandler extends MouseAdapter
    {

        @Override
        public	void mouseClicked(MouseEvent event) {

        }
    }
}