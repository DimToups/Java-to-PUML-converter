package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public abstract class ElementContent {
    protected String className;
    protected ElementKind classType;
    private Integer etagePuml;
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
    public void setEtagePuml(Integer etage){this.etagePuml = etage;}
    public Integer getPumlEtage(){return this.etagePuml;}
}
