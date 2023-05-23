package pumlFromJava;

import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;

public class Methode {
    private String nom;
    private TypeMirror type;
    private Visibilite visibilite;
    private Modificateur modificateur;
    private ArrayList<Attribut> parametres = new ArrayList<>();
    public Methode(String nom){
        this.nom = nom;
    }
    public String getNom(){ return this.nom;}
    public TypeMirror getType(){return this.type;}
    public Visibilite getVisibilite(){return this.visibilite;}
    public Modificateur getModificateur(){return this.modificateur;}
    public void setType(TypeMirror type) {this.type = type;}
    public void setVisibilite(Visibilite visibilite) {this.visibilite = visibilite;}
    public void setModificateur(Modificateur modificateur) {this.modificateur = modificateur;}
    public String getParameters(){
        String parameters = "";

        int i = 0;
        for (Attribut attribut : parametres){
            if (i == 0){
                parameters += attribut.getNom() + " : " + attribut.getType();
            }
            else{
                parameters += ", " + attribut.getNom() + " : " + attribut.getType();
            }
            i++;
        }

        return parameters;
    }
}
