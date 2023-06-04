package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;

public class EnumContent extends ElementContent{
    private ArrayList<Attribut> classAttributs = new ArrayList<>();
    public EnumContent(Element element){
        super(element);
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

        for(Attribut attribut : classAttributs){
            contenu += "\t" + attribut.AttributtoString() + "\n";
        }

        return contenu += "}\n";
    }
}
