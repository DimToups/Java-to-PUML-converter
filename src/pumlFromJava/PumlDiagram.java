package pumlFromJava;

import jdk.javadoc.doclet.DocletEnvironment;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PumlDiagram {
    private ArrayList<ClassContent> classes = new ArrayList<>();
    private String packageName;
    private String name;
    private String directory;
    private DocletEnvironment docletEnvironment;
    private ArrayList<Liaison> liaisons = new ArrayList<>();
    public PumlDiagram(String name, String directory, DocletEnvironment docletEnvironment){
        this.name = name;
        this.directory = directory;
        this.docletEnvironment = docletEnvironment;
    }
    /*public void makeDiagram(){
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
    }*/
    //private void endFile(){classesContent += "\n@enduml";}
    public void chercherClasses(){
        for (Element element : docletEnvironment.getIncludedElements()){
            if (element.getKind() == ElementKind.CLASS || element.getKind() == ElementKind.ENUM || element.getKind() == ElementKind.INTERFACE){
                ClassContent classContent = new ClassContent();
                classContent.setClass(element);
            }
        }
    }
    public void chercherLiaisons(){
        for (Element element : docletEnvironment.getIncludedElements()){
            TypeElement typeElement = (TypeElement) element;
            //Lien simple
            for (Element enclosedElement : element.getEnclosedElements()){
                if (enclosedElement.getKind().isField()){
                    for (Element elementCompar : docletEnvironment.getIncludedElements()) {
                        //Lien simple
                        if (elementCompar.asType() == enclosedElement.asType() && elementCompar != element) {
                            Liaison newLiaison = new Liaison(element.getSimpleName().toString(), elementCompar.getSimpleName().toString(), TypeLiaison.SIMPLE);
                            liaisons.add(newLiaison);
                        }
                    }
                }
            }
            //Heritage
            for (Element elementCompar : docletEnvironment.getIncludedElements()){
                if (elementCompar.toString().equals(typeElement.getSuperclass().toString())) {
                    Liaison newLiaison = new Liaison(element.getSimpleName().toString(), elementCompar.getSimpleName().toString(), TypeLiaison.HERITAGE);
                    liaisons.add(newLiaison);
                }
            }
            //Implémentation
            for (TypeMirror interfaceElement : typeElement.getInterfaces()){
                for (Element elementCompar : docletEnvironment.getIncludedElements()){
                    if (elementCompar.toString().equals(interfaceElement.toString())){
                        Liaison newLiaison = new Liaison(element.getSimpleName().toString(), elementCompar.getSimpleName().toString(), TypeLiaison.IMPLEMENT);
                        liaisons.add(newLiaison);
                    }
                }
            }
        }
    }
    public void genererDiagramme(){
        createFile();
        initFile();
        //Génération de toutes les classes
        for (ClassContent classContent : classes){
            classContent.genererContenuClasse();
        }
        //Génération de tous les liens
        for (Liaison liaison : liaisons){
            liaison.genererLiaison();
        }
        endFile();
    }
    private void createFile(){
        File file = new File(directory + "/" + name);
        try {
            if (!file.createNewFile()){
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(directory + "/" + name));
                writer.write("");
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void initFile(){
        try {
            String initFile = "@startuml\n" +
                    "'https://plantuml.com/class-diagram\n" +
                    "skinparam style strictuml\n" +
                    "skinparam classAttributeIconSize 0\n" +
                    "skinparam classFontStyle Bold\n" +
                    "\n" +
                    "hide empty members\n" +
                    "\n";
            FileOutputStream fos = null;
            fos = new FileOutputStream(directory + "/" + name, true);
            byte[] b = initFile.getBytes();
            fos.write(b);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void endFile(){
        try {
            String endFile = "\n@enduml";
            FileOutputStream fos = null;
            fos = new FileOutputStream(directory + "/" + name, true);
            byte[] b = endFile.getBytes();
            fos.write(b);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
