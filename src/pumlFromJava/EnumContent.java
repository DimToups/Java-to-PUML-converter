package pumlFromJava;

import javax.lang.model.element.Element;
import java.util.ArrayList;

public class EnumContent extends ElementContent{
    private ArrayList<Attribut> classAttributs = new ArrayList<>();
    public EnumContent(){}
    @Override
    public void setElement(Element element){
        this.className = element.getSimpleName().toString();
        this.classType = element.getKind();
        for (Element element1 : element.getEnclosedElements()){
            if (element1.getKind().isField()){
                Attribut attributEnum = new Attribut(element1.getSimpleName().toString(), element1.asType());
                classAttributs.add(attributEnum);
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
