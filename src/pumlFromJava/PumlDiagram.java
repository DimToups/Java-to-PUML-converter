package pumlFromJava;

import java.util.ArrayList;
import java.util.List;

public class PumlDiagram {
    List<String> classesList = new ArrayList<>();
    String classesContent;
    public PumlDiagram(){

    }
    public void setClasses(ArrayList<String> classes){
        this.classesList = classes;
    }
    public void makeDiagram(){
        initFile();
        //Traitement de chaque classe
        for(int i = 0; i < classesList.size(); i++){
            //Création de la chaîne de caractères à placer dans le fichier
            String classe = "class " + classesList.get(i) + "{\n\n}";
            //Ajout du String dans classContent
            classesContent += "\n" + classe;
        }
        endFile();

        System.out.println(classesContent);
    }
    private void initFile(){
        classesContent = "@startuml\n" +
                "'https://plantuml.com/class-diagram\n" +
                "skinparam style strictuml\n" +
                "skinparam classAttributeIconSize 0\n" +
                "skinparam classFontStyle Bold\n" +
                "\n" +
                "hide empty members\n" +
                "\n";
    }
    private void endFile(){
        classesContent += "\n@enduml";
    }
}
