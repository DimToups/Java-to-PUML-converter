package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
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
    public void setParameters(Element element){
        System.out.println(element.getSimpleName());
        ExecutableElement executableElement = (ExecutableElement) element;
        for(VariableElement variableElement : executableElement.getParameters()){
            Attribut attribut = new Attribut(variableElement.getSimpleName().toString(), variableElement.asType());
            this.parametres.add(attribut);
            System.out.println("\t" + variableElement.getSimpleName().toString() + " : " + variableElement.asType().toString());
        }
    }

    public String MethodetoString(){
        //Integer fullstop = this.type.toString().indexOf(".");
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

        //Ajout de son éventuel modificateur static
        if(this.modificateur == Modificateur.STATIC)
            toString += " {static}";
        if (this.modificateur == Modificateur.ABSTRACT)
            toString += " {abstract}";

        if (type != null)
        {
            //toString += " : " + this.type.toString().substring(this.type.toString().indexOf(".", this.type.toString().indexOf(".")+1)+1, this.type.toString().length());
            //toString += " : " + this.type.toString();
            toString += " : " + SubstringTypeMethode(this.type.toString());
        }

        return toString;
    }

    public String SubstringTypeMethode(String string)
    {
        int index = 0;
        for(int i = 0; i< string.length(); i++){
            if(string.charAt(i) == '.'){
                index = i;
            }
        }
        string = string.substring(index+1, string.length());
        return string;
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
