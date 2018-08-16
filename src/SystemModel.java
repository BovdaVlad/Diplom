import graphnet.GraphPetriNet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SystemModel implements Cloneable  {

    ArrayList <String> resource = new ArrayList<>();
    ArrayList<String> input = new ArrayList<>();
    ArrayList<String> output = new ArrayList<>();
    ArrayList<Integer> numresource = new ArrayList<>();
    ArrayList<File> fileinput = new ArrayList<>();
    ArrayList<File> fileoutput = new ArrayList<>();


    ArrayList<Integer> index_inp = new ArrayList<>();
    ArrayList<Integer> index_out = new ArrayList<>();

    GraphPetriNet graphPetriNet;
    GraphPetriNet graphPetriNetpaint;

    private String name;


    @Override
    protected SystemModel clone() throws CloneNotSupportedException
    {
        SystemModel systemModel = new SystemModel(name,this.resource,input,index_inp,output,index_out,numresource,fileinput,fileoutput);
        systemModel.setGraphPetriNet(this.graphPetriNet);
        systemModel.graphPetriNetpaint = this.graphPetriNetpaint;
        return systemModel;
    }

    SystemModel(String name, ArrayList<String> resource, ArrayList<String> input, ArrayList<Integer> index_inp, ArrayList<String> output, ArrayList<Integer> index_out, ArrayList<Integer> numresource, ArrayList<File> fileinput, ArrayList<File> fileoutput)
    {

        this.index_inp = index_inp;
        this.index_out = index_out;

        this.resource=resource;
        this.input=input;
        this.output=output;
        this.numresource=numresource;
        this.fileinput=fileinput;
        this.fileoutput=fileoutput;
        this.name = name;
    }

    public GraphPetriNet getGraphPetriNet() {
        return graphPetriNet;
    }

    public void setGraphPetriNet(GraphPetriNet graphPetriNet) {
        this.graphPetriNet = graphPetriNet;
        try {
            graphPetriNetpaint = graphPetriNet.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
