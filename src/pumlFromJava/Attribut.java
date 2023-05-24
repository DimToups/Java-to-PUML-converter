package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;

public class Attribut {
    private String nom;
    private TypeMirror type;
    private Visibilite visibilite;
    private Modificateur modificateur;
    public Attribut(String nom){
        this.nom = nom;
    }
    public Attribut(String nom, TypeMirror type){
        this.nom = nom;
        this.type = type;
    }
    public Attribut(String nom, TypeMirror type, Visibilite visibilite){
        this.nom = nom;
        this.type = type;
        this.visibilite = visibilite;
    }
    public Attribut(String nom, TypeMirror type, Modificateur modificateur){
        this.nom = nom;
        this.type = type;
        this.modificateur = modificateur;
    }
    public Attribut(String nom, TypeMirror type, Visibilite visibilite, Modificateur modificateur){
        this.nom = nom;
        this.type = type;
        this.visibilite = visibilite;
        this.modificateur = modificateur;
    }
    public String getNom(){return this.nom;}
    public TypeMirror getType(){return this.type;}
    public Visibilite getVisibilite(){return this.visibilite;}
    public Modificateur getModificateur(){return this.modificateur;}
    public void setType(TypeMirror type) {this.type = type;}
    public void setVisibilite(Visibilite visibilite) {this.visibilite = visibilite;}
    public void setModificateur(Modificateur modificateur) {this.modificateur = modificateur;}
    @Override
    public String toString(){
        if (this.type != null)
            return this.nom + " : " + this.type.toString();
        else
            return this.nom;
    }
    public void findModifier(Element element){
        for (Modifier modifier : element.getModifiers()){
            if (modifier == Modifier.ABSTRACT)
                this.modificateur = Modificateur.ABSTRACT;
            else if (modifier == Modifier.FINAL)
                this.modificateur = Modificateur.FINAL;
            else if (modifier == Modifier.STATIC)
                this.modificateur = Modificateur.STATIC;
        }
    }
    public void findVisibility(Element element){
        for (Modifier modifier : element.getModifiers()){
            if (modifier == Modifier.PUBLIC)
                this.visibilite = Visibilite.PUBLIC;
            else if (modifier == Modifier.PROTECTED)
                this.visibilite = Visibilite.PROTECTED;
            else if (modifier == Modifier.PRIVATE)
                this.visibilite = Visibilite.PRIVATE;
        }
    }
}
