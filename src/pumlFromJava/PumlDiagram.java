package pumlFromJava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PumlDiagram {
    List<String> classesList = new ArrayList<>();
    String classesContent;
    String name;
    String directory;
    public PumlDiagram(String name, String directory){
        this.name = name;
        this.directory = directory;
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

        //Création du fichier
        File file = new File("Path");
        try {
            if (file.createNewFile()){
                
            }
            else{

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
