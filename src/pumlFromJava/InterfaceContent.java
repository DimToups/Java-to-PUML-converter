package pumlFromJava;

import javax.lang.model.element.Element;
import java.util.ArrayList;

public class InterfaceContent extends ElementContent{
    private ArrayList<Methode> methodes = new ArrayList<>();
    public InterfaceContent(){}
    @Override
    public void setElement(Element element){

    }
    @Override
    public String genererContenuElement(boolean isDca){
        return "";
    }
}
