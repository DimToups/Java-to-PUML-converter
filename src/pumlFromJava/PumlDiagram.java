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
    private ArrayList<ElementContent> elements = new ArrayList<>();
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
            if (element.getKind() == ElementKind.CLASS){
                ClassContent classContent = new ClassContent(element);
                this.elements.add(classContent);
            }
            else if(element.getKind() == ElementKind.ENUM){
                EnumContent enumContent = new EnumContent(element);
                this.elements.add(enumContent);
            }
            else if (element.getKind() == ElementKind.INTERFACE){
                InterfaceContent interfaceContent = new InterfaceContent(element);
                this.elements.add(interfaceContent);
            }
            else if (element.getKind() == ElementKind.PACKAGE) {
                packageName = element.getSimpleName().toString();
            }
        }
    }
    public void chercherAssociations(){
        //Recherche des agrégations
        for(ElementContent element : elements){
            //Si element est une énumération ou une classe
            if (true){

            }
        }
    }
    private ElementContent findClass(Element element){
        ElementContent rightElement = null;
        boolean found = false;
        for (ElementContent elementContent : elements){
            if (elementContent.getNom().equals(element.getSimpleName().toString()) && elementContent.getType() == element.getKind()){
                rightElement = elementContent;
                found = true;
            }
        }
        if (found) {return rightElement;}
        else{return null;}
    }
    public void genererDiagramme(){
        GenerateurDiagramme generateurDiagramme = new GenerateurDiagramme(name, directory, packageName, isDca);
        generateurDiagramme.createFile();
        generateurDiagramme.initFile();
        generateurDiagramme.generateElementsForPuml(elements);
        generateurDiagramme.generateLinksForPuml(associations);
        generateurDiagramme.endFile();
    }
}