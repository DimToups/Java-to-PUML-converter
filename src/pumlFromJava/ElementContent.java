package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public abstract class ElementContent {
    protected String className;
    protected ElementKind classType;
    private int etagePuml;
    public ElementContent(Element element){
        this.className = element.getSimpleName().toString();
        this.classType = element.getKind();
    }
    public abstract String genererContenuElement(boolean isDca);
    public String getNom(){
        return this.className;
    }
    public ElementKind getType(){
        return this.classType;
    }
    public void setEtagePuml(int etage){this.etagePuml = etage;}
    public int getPumlEtage(){return this.etagePuml;}
}
