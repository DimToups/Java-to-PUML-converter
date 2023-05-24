package pumlFromJava;

import javax.lang.model.element.Element;
import java.util.ArrayList;

public class EnumContent extends ElementContent{
    private ArrayList<Attribut> attributs = new ArrayList<>();
    public EnumContent(){}
    @Override
    public void setElement(Element element){

    }
    @Override
    public String genererContenuElement(boolean isDca){
        return "";
    }
}
