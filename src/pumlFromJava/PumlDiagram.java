package pumlFromJava;

import java.util.ArrayList;
import java.util.List;

public class PumlDiagram {
    List<String> classes = new ArrayList<>();
    public PumlDiagram(){

    }
    public void setClasses(ArrayList<String> classes){
        this.classes = classes;
    }
    public void makeDiagram(){
        System.out.println("Enter makeDiagram");
    }
}
