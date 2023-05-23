package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public abstract class ElementContent {
    protected String className;
    protected ElementKind classType;
    public ElementContent(){}
    public abstract void setElement(Element element);
    public abstract String genererContenuElement(boolean isDca);
    public String getNom(){
        return this.className;
    }
    public ElementKind getType(){
        return this.classType;
    }
}
