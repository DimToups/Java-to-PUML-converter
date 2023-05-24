package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;

public class EnumContent extends ElementContent{
    private ArrayList<Attribut> classAttributs = new ArrayList<>();
    public EnumContent(){}
    @Override
    public void setElement(Element element){
        this.className = element.getSimpleName().toString();
        this.classType = element.getKind();
        for (Element enclosedElement : element.getEnclosedElements()){
            if (enclosedElement.getKind().isField()){
                VariableElement variableElement = (VariableElement) enclosedElement;
                Attribut attribut = new Attribut(variableElement);
                classAttributs.add(attribut);
            }
        }
    }
    @Override
    public String genererContenuElement(boolean isDca){
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
            return contenu += "}\n";
        }
    }
}
