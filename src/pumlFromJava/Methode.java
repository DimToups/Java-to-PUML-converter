package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
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
    public Methode(String nom, TypeMirror type){
        this.nom = nom;
        this.type = type;
    }
    public String getNom(){ return this.nom;}
    public TypeMirror getType(){return this.type;}
    public Visibilite getVisibilite(){return this.visibilite;}
    public Modificateur getModificateur(){return this.modificateur;}
    public void setType(TypeMirror type) {this.type = type;}
    public void setVisibilite(Visibilite visibilite) {this.visibilite = visibilite;}
    public void setModificateur(Modificateur modificateur) {this.modificateur = modificateur;}

    public String MethodetoString(){
        String toString = "";
        if (this.getVisibilite().equals(Visibilite.PUBLIC))
        {
            toString += "+ " + this.nom + "(" + this.getParameters() + ")";
        }
        else if (this.getVisibilite().equals(Visibilite.PRIVATE))
        {
            toString += "- " + this.nom + "(" + this.getParameters() + ")";
        }
        else if (this.getVisibilite().equals(Visibilite.PROTECTED))
        {
            toString += "# " + this.nom + "(" + this.getParameters() + ")";
        }

        if (type != null)
        {
            toString += " : " + this.type.toString();
        }

        return toString;
    }
    public String getParameters(){
        String parameters = "";

        int i = 0;
        for (Attribut attribut : parametres){
            if (i == 0)
                parameters += attribut.toString();
            else
                parameters += ", " + attribut.toString();
            i++;
        }

        return parameters;
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
