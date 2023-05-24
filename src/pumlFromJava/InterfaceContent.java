package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.ArrayList;

public class InterfaceContent extends ElementContent{
    private ArrayList<Methode> classMethods = new ArrayList<>();
    public InterfaceContent(){}
    @Override
    public void setElement(Element element){
        this.className = element.getSimpleName().toString();
        this.classType = element.getKind();
        for (Element enclosedElement : element.getEnclosedElements()){
            //Gestion des méthodes
            if (enclosedElement.getKind() == ElementKind.METHOD){
                Methode methode = new Methode(enclosedElement.getSimpleName().toString());
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
                if (methode.getVisibilite() == Visibilite.PUBLIC)
                    contenu += "+ ";
                else if (methode.getVisibilite() == Visibilite.PROTECTED)
                    contenu += "# ";
                else if (methode.getVisibilite() == Visibilite.PRIVATE)
                    contenu += "- ";
                contenu += methode.toString() + "\n";
            }
            return contenu += "}\n";
        }
    }
}
