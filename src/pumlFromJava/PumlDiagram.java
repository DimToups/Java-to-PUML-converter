package pumlFromJava;

import javax.lang.model.element.ElementKind;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PumlDiagram {
    private ArrayList<ClassContent> classes = new ArrayList<>();
    private String packageName;
    private String classesContent = "";
    private String name;
    private String directory;
    private ArrayList<Liaison> liaisons = new ArrayList<>();
    public PumlDiagram(String name, String directory){
        this.name = name;
        this.directory = directory;
    }
    public void setClasses(ArrayList<ClassContent> classes, ArrayList<Liaison> liaisons, String packageName){
        this.classes = classes;
        this.liaisons = liaisons;
        this.packageName = packageName;

        for (Liaison liaison : liaisons){
            System.out.println(liaison.element1 + " -> " + liaison.element2 + " : " + liaison.typeLiaison);
        }
    }
    public void makeDiagram(){
        initFile();
        //Ajout du package dans classContent
        classesContent += "\npackage " + packageName + "{\n";
        //Traitement de chaque classe
        for(int i = 0; i < classes.size(); i++){
            //Création de la chaîne de caractères à placer dans le fichier
            String classe;
            if (classes.get(i).classType == ElementKind.INTERFACE) {
                classe = "class " + classes.get(i).className + " <<interface>>{\n";
            }
            else if (classes.get(i).classType == ElementKind.ENUM) {
                classe = "class " + classes.get(i).className + " <<enum>>{\n";
            }
            else {
                classe = "class " + classes.get(i).className + "{\n";
            }
            //Ajout des attributs de la classe
            for (Attribut attribut : classes.get(i).classAttributs){
                classe += "\t" + attribut.nom;
                if (attribut.type != null)
                    classe += " : " + attribut.type.toString();
                classe += "\n";
            }
            //Ajout des méthodes de la classe
            for (Methode methode : classes.get(i).classMethods){
                classe += "\t" + methode.nom;
                if (methode.type != null)
                    classe += " : " + methode.type.toString();
                classe += "\n";
            }
            //Ajout du String dans classContent
            classesContent += "\n" + classe + "\n}";
        }
        //Ajout des liaisons
        int etage = 1;
        for(Liaison liaison : liaisons){
            if(liaison.typeLiaison == TypeLiaison.SIMPLE){
                String stringEtage = "";
                for (int i = 0; i < etage; i++){
                    if (i % 4 == 0)
                        stringEtage += "-";
                }
                classesContent += "\n" + liaison.element1 + stringEtage + liaison.element2;
                etage++;
            }
            else if (liaison.typeLiaison == TypeLiaison.HERITAGE){
                String stringEtage = "";
                for (int i = 0; i < etage; i++){
                    if (i % 2 == 0)
                        stringEtage += "-";
                }
                classesContent += "\n" + liaison.element1 + stringEtage + "|>" + liaison.element2;
                etage++;
            }
            else if (liaison.typeLiaison == TypeLiaison.IMPLEMENT){
                String stringEtage = "";
                for (int i = 0; i < etage; i++){
                    if (i % 2 == 0)
                        stringEtage += ".";
                }
                classesContent += "\n" + liaison.element1 + stringEtage + "|>" + liaison.element2;
                etage++;
            }
        }

        endFile();

        //Création du fichier
        File file = new File(directory + "/" + name);
        try {
            if (file.createNewFile()){
                FileOutputStream fos = new FileOutputStream(directory + "/" + name, true);
                byte[] b = classesContent.getBytes();
                fos.write(b);
            }
            else{
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(directory + "/" + name));
                writer.write("");
                writer.flush();

                FileOutputStream fos = new FileOutputStream(directory + "/" + name, true);
                byte[] b = classesContent.getBytes();
                fos.write(b);
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
