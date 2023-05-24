package pumlFromJava;

import javax.lang.model.element.Element;
import java.util.ArrayList;

public class EnumContent extends ElementContent{
    private ArrayList<Attribut> attributs = new ArrayList<>();
    public EnumContent(){}
    @Override
    public void setElement(Element element){
        for (Element element1 : element.getEnclosedElements())
        {
            if (element1.getKind().isField())
            {
                Attribut attributEnum = new Attribut(element1.getSimpleName().toString());
                attributs.add(attributEnum);
            }
        }
    }
    @Override
    public String genererContenuElement(boolean isDca){
        return "";
    }
}
