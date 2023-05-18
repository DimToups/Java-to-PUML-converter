package pumlFromJava;

import javax.lang.model.type.PrimitiveType;

public class Methode {
    String nom;
    PrimitiveType type;
    Visibilite visibilite;
    public Methode(String nom){
        this.nom = nom;
    }
    public String getNom(){ return this.nom;}
    public PrimitiveType getType(){ return this.type;}
    public Visibilite getVisibilite(){ return this.visibilite;}
}
