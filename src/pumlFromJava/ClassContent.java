package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;

public class ClassContent{
    private String className;
    private ElementKind classType;
    private ArrayList<Attribut> classAttributs = new ArrayList<>();
    private ArrayList<Methode> classMethods = new ArrayList<>();

    public ClassContent() {}
    public void setClass(Element element){
        this.className = element.getSimpleName().toString();
        this.classType = element.getKind();
        for (Element enclosedElement : element.getEnclosedElements()){
            //Gestion des attributs
            if (enclosedElement.getKind().isField() && !enclosedElement.asType().toString().contains(".")){
                Attribut attribut = new Attribut(enclosedElement.getSimpleName().toString());
                classAttributs.add(attribut);
            }
            //Gestion des méthodes
            if (false){
                Methode methode = new Methode(enclosedElement.getSimpleName().toString());
                classMethods.add(methode);
            }
        }
    }
    public String genererContenuClasse(){
        String classContent = "class " + className;
        //Affichage du stéréotype
        if (classType == ElementKind.ENUM){
            classContent += " <<Interface>>";
        }
        else if (classType == ElementKind.INTERFACE) {
            classContent += " <<Enum>>";
        }
        classContent += "{\n";
        //Affichage des attributs
        for(Attribut attribut : classAttributs){
            classContent += "\t" + attribut.getNom() + "\n";
        }
        //Affichage des méthodes
        for(Methode methode : classMethods){
            classContent += "\t" + methode.getNom() + "() : " + methode.getType() + "\n";
        }
        classContent += "}\n";
        return classContent;
    }
    public String getNom(){ return this.className; }
    public ElementKind getType(){ return this.classType; }
}