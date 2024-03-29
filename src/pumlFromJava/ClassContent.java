package pumlFromJava;

import javax.lang.model.element.*;
import java.util.ArrayList;

public class ClassContent extends ElementContent{

    private ArrayList<Attribut> classAttributs = new ArrayList<>();

    private ArrayList<Methode> classMethods = new ArrayList<>();

    public ArrayList<Methode> getMethodes(){
        return this.classMethods;
    }

    public ArrayList<Attribut> getAttributs(){return this.classAttributs;}

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
            if (enclosedElement.getKind() == ElementKind.METHOD ||enclosedElement.getKind() == ElementKind.CONSTRUCTOR){
                ExecutableElement executableElement = (ExecutableElement)enclosedElement;
                Methode methode = new Methode(executableElement);
                methode.findModifier(enclosedElement);
                methode.findVisibility(enclosedElement);
                if (enclosedElement.getKind() == ElementKind.CONSTRUCTOR)
                    methode.setName(this.className);
                classMethods.add(methode);
            }
        }
    }

    @Override
    public String genererContenuElement(boolean isDca) {
        String contenu = "class " + this.className + "{\n";
        //Génération type DCA
        if (isDca){
            for (Attribut attribut : classAttributs) {
                if (!attribut.getType().toString().contains("."))
                    contenu += "\t" + attribut.getNom() + "\n";
            }
            return contenu += "}";
        }
        //Génération type DCC
        else{
            for(Attribut attribut : classAttributs){
                if (attribut.getPumlVisibility())
                    contenu += "\t" + attribut.AttributtoString(false) + "\n";
            }
            for (Methode methode : classMethods){
                contenu += "\t" + methode.MethodetoString() + "\n";
            }
            return contenu += "}\n";
        }
    }
}