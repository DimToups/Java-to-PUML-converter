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
        for(ElementContent elementContent : elements){
            Element element = findElementFromElementContent(elementContent);
            //Recherche d'agrégation/composition
            if (true){

            }
            //Recherche d'héritage


            //Recherche d'interface
            for(TypeMirror typeInterface : ((TypeElement)element).getInterfaces()){
                Association assoInterface = new Association(elementContent, findElementContentFromTypeMirror(typeInterface), TypeAssociation.IMPLEMENT);
                associations.add(assoInterface);
            }
        }
        for(Association association : associations){
            System.out.println(association.getElement1().getNom() + " -> " + association.getElement2().getNom() + " : " + association.getTypeAssociation().toString());
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
    private ElementContent findElementContentFromTypeMirror(TypeMirror typeMirror){
        for(ElementContent elementContent : elements){
            if (elementContent.getNom().equals(typeMirror.toString()));
                return elementContent;
        }
        return null;
    }
    private Element findElementFromElementContent(ElementContent elementContent){
        for(Element element : docletEnvironment.getIncludedElements()){
            System.out.println(elementContent.getNom() + " : " + element.getSimpleName().toString());
            if(element.getSimpleName().toString().equals(elementContent.getNom()))
                return element;
        }

        return null;
    }
}