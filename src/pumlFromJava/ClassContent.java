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
                attribut.findModifier(enclosedElement);
                attribut.findVisibility(enclosedElement);
                classAttributs.add(attribut);
            }
            //Gestion des méthodes
            if (enclosedElement.getKind() == ElementKind.METHOD){
                Methode methode = new Methode(enclosedElement.getSimpleName().toString());
                methode.findModifier(enclosedElement);
                methode.findVisibility(enclosedElement);
                classMethods.add(methode);
            }
        }
    }
    @Override
    public String genererContenuElement(boolean isDca) {
        String contenu = "class " + this.className + "{\n";
        if (isDca){
            for (Attribut attribut : classAttributs) {
                if (attribut.getType().toString().contains("."))
                    contenu += attribut.toString() + "\n";
            }
            return contenu += "}";
        }
        else{
            for(Attribut attribut : classAttributs){
                if (attribut.getVisibilite() == Visibilite.PUBLIC)
                    contenu += "+ ";
                else if (attribut.getVisibilite() == Visibilite.PROTECTED)
                    contenu += "# ";
                else if (attribut.getVisibilite() == Visibilite.PRIVATE)
                    contenu += "- ";
                contenu += attribut.toString() + "\n";
            }
            for (Methode methode : classMethods){
                contenu += methode.MethodetoString() + "\n";
            }
            return contenu += "}\n";
        }
    }
}