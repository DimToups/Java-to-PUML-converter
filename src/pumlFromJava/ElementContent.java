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

    public String getNom(){
        return this.className;
    }

    public ElementKind getType(){
        return this.classType;
    }

    public Integer getPumlEtage(){return this.etagePuml;}

    public void setEtagePuml(Integer etage){this.etagePuml = etage;}

    // Qu'on va override selon si c'est une classe, une énum ou une interfance
    public abstract String genererContenuElement(boolean isDca);
}
