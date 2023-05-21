package pumlFromJava;

import javax.lang.model.type.PrimitiveType;

public class Attribut {
    private String nom;
    private PrimitiveType type;
    private Visibilite visibilite;
    public Attribut(String nom){
        this.nom = nom;
    }
    public String getNom(){ return this.nom;}
    public PrimitiveType getType(){ return this.type;}
    public Visibilite getVisibilite(){ return this.visibilite;}
}
