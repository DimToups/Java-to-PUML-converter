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
        int etagePumlAttribue = 0;
        for (Element element : docletEnvironment.getIncludedElements()){
            if (element.getKind() == ElementKind.CLASS){
                ClassContent classContent = new ClassContent(element);
                classContent.setEtagePuml(etagePumlAttribue%3);
                this.elements.add(classContent);
                etagePumlAttribue++;
            }
            else if(element.getKind() == ElementKind.ENUM){
                EnumContent enumContent = new EnumContent(element);
                enumContent.setEtagePuml(etagePumlAttribue%3);
                this.elements.add(enumContent);
                etagePumlAttribue++;
            }
            else if (element.getKind() == ElementKind.INTERFACE){
                InterfaceContent interfaceContent = new InterfaceContent(element);
                interfaceContent.setEtagePuml(etagePumlAttribue%3);
                this.elements.add(interfaceContent);
                etagePumlAttribue++;
            }
            else if (element.getKind() == ElementKind.PACKAGE) {
                packageName = element.getSimpleName().toString();
            }
        }
    }
    public void chercherAssociations() {
        //Recherche des agrégations
        for (ElementContent elementContent : elements) {
            Element element = findElementFromElementContent(elementContent);

            //Recherche d'agrégation/composition
            if (elementContent.classType == ElementKind.CLASS) {
                ClassContent classContent = (ClassContent) elementContent;
                for(Attribut attribut : classContent.getAttributs()) {
                    if(!attribut.getType().toString().equals("void")){
                        for(ElementContent elementContentCompar : elements){
                            if(SubstringType(attribut.getType().toString()).equals(elementContentCompar.className)) {
                                attribut.setToInvisible();
                                Association associationAgreg = new Association(elementContent, elementContentCompar, TypeAssociation.AGREGATION);
                                associationAgreg.setAttributLié(attribut);
                                associationAgreg.setMult1("1");
                                if(attribut.getType().toString().contains("java.util"))
                                    associationAgreg.setMult2("*");
                                else
                                    associationAgreg.setMult2("1");
                                this.ajoutAssociation(associationAgreg);
                            }
                        }
                    }
                }
            }

            //Recherche d'héritage
            if (((TypeElement) element).getSuperclass() != null) {
                ElementContent superElement = findElementContentFromTypeMirror(((TypeElement) element).getSuperclass());
                if (superElement != null && superElement != elementContent) {
                    Association associationSuperElement = new Association(elementContent, superElement, TypeAssociation.HERITAGE);
                    associations.add(associationSuperElement);
                    this.ajoutAssociation(associationSuperElement);
                }
            }

            //Recherche d'interface
            for (TypeMirror typeInterface : ((TypeElement) element).getInterfaces()) {
                //Ajout de l'association
                Association associationInterface = new Association(findElementContentFromElement(element), findElementContentFromTypeMirror(typeInterface), TypeAssociation.IMPLEMENT);
                associations.add(associationInterface);
                this.ajoutAssociation(associationInterface);
            }

            //Recherche de dépendances
            if (elementContent.getType() == ElementKind.CLASS || elementContent.getType() == ElementKind.METHOD) {
                for (Methode methode : ((ClassContent) elementContent).getMethodes()) {
                    //Traitement du type de la méthode
                    if (!methode.getType().toString().equals("void")) {
                        String methodeType = methode.SubstringType(methode.getType().toString());
                        for (ElementContent elementContentCompar : elements) {
                            if (methodeType.equals(elementContentCompar.className)) {
                                //Ajout de la dépendance
                                Association associationDependance = new Association(elementContent, elementContentCompar, TypeAssociation.DEPENDANCE);
                                this.ajoutAssociation(associationDependance);
                            }
                        }
                    }

                    //Traitement de ses paramètres
                    for (Attribut attribut : methode.getParameters()) {
                        if (!attribut.getType().toString().equals("void")) {
                            String attributType = methode.SubstringType(attribut.getType().toString());
                            for (ElementContent elementContentCompar : elements) {
                                if (attributType.equals(elementContentCompar.className)) {
                                    //Ajout de la dépendance
                                    Association associationDependance = new Association(elementContent, elementContentCompar, TypeAssociation.DEPENDANCE);
                                    this.ajoutAssociation(associationDependance);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Méthode pour afficher ou non une méthode
    public void afficheMethodeHeritage(){
        for (Association association : associations){
            if (association.getTypeAssociation() == TypeAssociation.HERITAGE){
                for (Methode methods1 : ((ClassContent)association.getElement1()).getMethodes()){
                    for (Methode methods2 : ((ClassContent)association.getElement2()).getMethodes()){
                        if (methods1.getNom().equals(methods2.getNom())){
                            methods1.setToPumlInvisible();
                        }
                    }
                }
            }
        }
    }

    private ElementContent findElementContentFromElement(Element element){
        ElementContent rightElement = null;
        for (ElementContent elementContent : elements){
            if (elementContent.getNom().equals(element.getSimpleName().toString()) && elementContent.getType() == element.getKind())
                return elementContent;
        }
        return null;
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
            if (elementContent.getNom().equals(SubstringType(typeMirror.toString())))
                return elementContent;
        }
        return null;
    }
    private Element findElementFromElementContent(ElementContent elementContent){
        for(Element element : docletEnvironment.getIncludedElements()){
            if(element.getSimpleName().toString().equals(elementContent.getNom()))
                return element;
        }

        return null;
    }
    private String SubstringType(String string) {
        if (string.contains(".")){
            int index = 0;
            for(int i = 0; i< string.length(); i++){
                if(string.charAt(i) == '.'){
                    index = i;
                }
            }
            string = string.substring(index+1, string.length());
            if (string.contains(">"))
                string = string.substring(0, string.length()-1);
            return string;
        }
        else{
            return string;
        }
    }
    public void ajoutAssociation(Association associationCandidate){
        //Traitement de toutes les associations
        for (Association association : associations){
            if (association.getElement1().getNom().equals(associationCandidate.getElement1().getNom()) && association.getElement2().getNom().equals(associationCandidate.getElement2().getNom()) && associationCandidate.getTypeAssociation() == association.getTypeAssociation())
                return;
            if (association.getElement1().getNom().equals(associationCandidate.getElement2().getNom()) && association.getElement2().getNom().equals(associationCandidate.getElement1().getNom()) && associationCandidate.getTypeAssociation() == association.getTypeAssociation())
                return;
        }
        this.associations.add(associationCandidate);
    }
    public void miseAJourMultiplicite(){
        for(Association association : associations){
            for(Association associationCompar : associations){
                //Exclusion des associations similaires
                if(association != associationCompar || associationCompar.getPumlVisibilite()){
                    if(association.getElement1() == associationCompar.getElement1() && association.getElement2() == associationCompar.getElement2() || association.getTypeAssociation() == associationCompar.getTypeAssociation()){
                        associationCompar.setToInvisible();
                        association.IncrementationMult();
                    }
                }
            }
        }
    }
    public void triDépendances(){
        //Traitement de toutes les associations du diagramme
        for(Association association : associations){
            //Supression des dépendances entre les deux éléments de l'association
            if(association.getTypeAssociation() != TypeAssociation.DEPENDANCE) {
                for (Association associationCompar : associations){
                    if(association.getElement1() == associationCompar.getElement1() && association.getElement2() == associationCompar.getElement2() && associationCompar.getTypeAssociation() == TypeAssociation.DEPENDANCE)
                        associationCompar.setToInvisible();
                    else if(association.getElement2() == associationCompar.getElement1() && association.getElement1() == associationCompar.getElement2() && associationCompar.getTypeAssociation() == TypeAssociation.DEPENDANCE)
                        associationCompar.setToInvisible();
                }
            }
        }
    }
}