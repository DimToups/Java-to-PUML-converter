package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;

public class ClassContent extends ElementContent{
    private ArrayList<Attribut> classAttributs = new ArrayList<>();
    private ArrayList<Methode> classMethods = new ArrayList<>();
    public ClassContent() {
        super();
    }
    @Override
    public void setElement(Element element){
        this.className = element.getSimpleName().toString();
        this.classType = element.getKind();
        for (Element enclosedElement : element.getEnclosedElements()){
            //Gestion des attributs
            if (enclosedElement.getKind().isField()){
                Attribut attribut = new Attribut(enclosedElement.getSimpleName().toString());
                classAttributs.add(attribut);
            }
            //Gestion des méthodes
            if (enclosedElement.getKind() == ElementKind.METHOD){
                Methode methode = new Methode(enclosedElement.getSimpleName().toString());
                classMethods.add(methode);
            }
        }
    }
    @Override
    public String genererContenuElement(boolean isDCA){
        String classContent = "class " + className;
        //Affichage du stéréotype
        if (classType == ElementKind.INTERFACE){
            classContent += " <<Interface>>";
        }
        else if (classType == ElementKind.ENUM) {
            classContent += " <<Enum>>";
        }
        classContent += "{\n";
        //Affichage des attributs
        for(Attribut attribut : classAttributs){
            // Gestion de la visibilité
            if (attribut.getVisibilite().equals(Visibilite.PRIVATE))
            {
                classContent += "\t" + "-" + attribut.getNom() + " : " + attribut.getType() +"\n";
            }
            else if (attribut.getVisibilite().equals(Visibilite.PUBLIC))
            {
                classContent += "\t" + "+" + attribut.getNom() + " : " + attribut.getType() + "\n";
            } else if (attribut.getVisibilite().equals(Visibilite.PROTECTED))
            {
                classContent += "\t" + "#" + attribut.getNom() + " : " + attribut.getType() + "\n";
            }
        }
        //Affichage des méthodes
        for(Methode methode : classMethods){
            // Gestion de la visibilité
            if (methode.getVisibilite().equals(Visibilite.PRIVATE))
            {
                classContent += "\t" + "-" + methode.getNom() + "(" + methode.getParameters() + ") : " + methode.getType() + "\n";
            } else if (methode.getVisibilite().equals(Visibilite.PUBLIC)) {
                classContent += "\t" + "+" + methode.getNom() + "(" + methode.getParameters() + ") : " + methode.getType() + "\n";
            } else if (methode.getVisibilite().equals(Visibilite.PROTECTED)) {
                classContent += "\t" + "#" + methode.getNom() + "(" + methode.getParameters() + ") : " + methode.getType() + "\n";
            }
        }
        classContent += "}\n";
        return classContent;
    }
    public String getNom(){ return this.className; }
    public ElementKind getType(){ return this.classType; }
}