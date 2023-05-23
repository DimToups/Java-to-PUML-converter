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
    private boolean isDca;
    private ArrayList<Association> associations = new ArrayList<>();
    public PumlDiagram(String name, String directory, DocletEnvironment docletEnvironment, boolean isDCA){
        this.name = name;
        this.directory = directory;
        this.docletEnvironment = docletEnvironment;
        this.isDca = isDCA;
        System.out.println("isDca = " + isDCA);
    }
    public void chercherClasses(){
        for (Element element : docletEnvironment.getIncludedElements()){
            if (element.getKind() == ElementKind.CLASS || element.getKind() == ElementKind.ENUM || element.getKind() == ElementKind.INTERFACE){
                ClassContent classContent = new ClassContent();
                classContent.setClass(element);
                this.classes.add(classContent);
            }
            else if (element.getKind() == ElementKind.PACKAGE) {
                packageName = element.getSimpleName().toString();
            }
        }
    }
    public void chercherAssociations(){
        for (Element element : docletEnvironment.getIncludedElements()){
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                //Lien simple
                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.getKind().isField()) {
                        for (Element elementCompar : docletEnvironment.getIncludedElements()) {
                            //Lien simple
                            if (elementCompar.asType() == enclosedElement.asType() && elementCompar != element) {
                                Association newAssociation = new Association(findClass(element), findClass(elementCompar), TypeAssociation.SIMPLE);
                                associations.add(newAssociation);
                            }
                        }
                    }
                }
                //Heritage
                for (Element elementCompar : docletEnvironment.getIncludedElements()) {
                    if (elementCompar.toString().equals(typeElement.getSuperclass().toString())) {
                        Association newAssociation = new Association(findClass(element), findClass(elementCompar), TypeAssociation.HERITAGE);
                        associations.add(newAssociation);
                    }
                }
                //Implémentation
                for (TypeMirror interfaceElement : typeElement.getInterfaces()) {
                    for (Element elementCompar : docletEnvironment.getIncludedElements()) {
                        if (elementCompar.toString().equals(interfaceElement.toString())) {
                            Association newAssociation = new Association(findClass(element), findClass(elementCompar), TypeAssociation.IMPLEMENT);
                            associations.add(newAssociation);
                        }
                    }
                }
            }
        }
    }
    private ClassContent findClass(Element element){
        ClassContent rightClass = new ClassContent();
        boolean found = false;
        for (ClassContent classContent : classes){
            if (classContent.getNom().equals(element.getSimpleName().toString()) && classContent.getType() == element.getKind()){
                rightClass = classContent;
                found = true;
            }
        }
        if (found) {return rightClass;}
        else{return null;}
    }
    public void genererDiagramme(){
        createFile();
        initFile();
        //Génération de toutes les classes
        for (ClassContent classContent : classes){
            try {
                String classString = classContent.genererContenuClasse();
                FileOutputStream fos = null;
                fos = new FileOutputStream(directory + "/" + name, true);
                byte[] b = classString.getBytes();
                fos.write(b);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //Génération de tous les liens
        for (Association Association : associations){
            try {
                String AssociationString = Association.genererAssociation();
                FileOutputStream fos = null;
                fos = new FileOutputStream(directory + "/" + name, true);
                byte[] b = AssociationString.getBytes();
                fos.write(b);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
            initFile += "package " + packageName + "{\n";
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
            String endFile = "\n}\n@enduml";
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