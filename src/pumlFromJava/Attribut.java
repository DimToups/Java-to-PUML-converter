package pumlFromJava;

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

    public String AttributtoString(){
        String toString = "";

        if (this.getVisibilite().equals(Visibilite.PUBLIC))
        {
            toString += "+" + this.nom + this.type.toString();
        }
        if (this.getVisibilite().equals(Visibilite.PRIVATE))
        {
            toString += "-" + this.nom + this.type.toString();
        }
        if (this.getVisibilite().equals(Visibilite.PROTECTED))
        {
            toString += "#" + this.nom + this.type.toString();
        }

        return toString;
    }
}
