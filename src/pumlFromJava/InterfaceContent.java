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
        return "";
    }
}
