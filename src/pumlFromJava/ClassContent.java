package pumlFromJava;

import javax.lang.model.element.*;
import java.util.ArrayList;

public class ClassContent extends ElementContent{
    private ArrayList<Attribut> classAttributs = new ArrayList<>();
    private ArrayList<Methode> classMethods = new ArrayList<>();
    public ClassContent(Element element) {
        super(element);
        for (Element enclosedElement : element.getEnclosedElements()){
            //Gestion des attributs
            if (enclosedElement.getKind().isField()){
                VariableElement variableElement = (VariableElement) enclosedElement;
                Attribut attribut = new Attribut(variableElement);
                classAttributs.add(attribut);
            }
            //Gestion des méthodes
            if (enclosedElement.getKind() == ElementKind.METHOD){
                ExecutableElement executableElement = (ExecutableElement) enclosedElement;
                Methode methode = new Methode(executableElement);
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
                if (attribut.getType().toString().contains("."))
                    contenu += attribut.getNom() + "\n";
            }
            for (Methode methode : classMethods){
                contenu += methode.MethodetoString() + "\n";
            }
            return contenu += "}\n";
        }
    }
}