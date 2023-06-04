package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import java.util.ArrayList;

public class InterfaceContent extends ElementContent{
    private ArrayList<Methode> classMethods = new ArrayList<>();
    public InterfaceContent(Element element){
        super(element);
        for (Element enclosedElement : element.getEnclosedElements()){
            //Gestion des méthodes
            if (enclosedElement.getKind() == ElementKind.METHOD ||enclosedElement.getKind() == ElementKind.CONSTRUCTOR){
                ExecutableElement executableElement = (ExecutableElement)enclosedElement;
                Methode methode = new Methode(executableElement);
                methode.setParameters(enclosedElement);
                methode.findModifier(enclosedElement);
                methode.findVisibility(enclosedElement);
                classMethods.add(methode);
            }
        }
    }
    @Override
    public String genererContenuElement(boolean isDca){
        String contenu = "interface " + this.className + "<<interface>>{\n";
        if (isDca)
            return contenu += "}";
        else{
            for (Methode methode : classMethods){
                contenu += "\t" + methode.MethodetoString() + "\n";
            }
            return contenu += "}\n";
        }
    }
    public ArrayList<Methode> getMethodes(){
        return this.classMethods;
    }
}
