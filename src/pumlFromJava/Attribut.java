package pumlFromJava;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;

public class Attribut {
    private String nom;
    private TypeMirror type;
    private Visibilite visibilite;
    private Modificateur modificateur;
    private boolean isPumlVisible = true;
    public Attribut(VariableElement variableElement){
        System.out.println("\t" + variableElement.toString());
        this.nom = variableElement.getSimpleName().toString();
        this.type = variableElement.asType();
        this.findModifier(variableElement);
        this.findVisibility(variableElement);
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

        //Gestion de la visibilité
        if (this.getVisibilite() != null){
            if (this.getVisibilite().equals(Visibilite.PUBLIC))
                toString += "+ ";
            else if (this.getVisibilite().equals(Visibilite.PRIVATE))
                toString += "- ";
            else if (this.getVisibilite().equals(Visibilite.PROTECTED))
                toString += "# ";
        }
        //Ajout du nom de l'attribut
        toString += this.getNom() + " : " + findUmlType(this.getType());

        //Ajout de son éventuel modificateur static
        if(this.modificateur == Modificateur.STATIC)
            toString += " {static}";

        return toString;
    }
    private String findUmlType(TypeMirror typeMirror){
        boolean isUmlMulti = false;
        String umlType = "";
        if (typeMirror.toString().contains("java.util")){
            isUmlMulti = true;
            DeclaredType declaredType = (DeclaredType) typeMirror;
            for (TypeMirror typeMirrorCompar : declaredType.getTypeArguments()){
                umlType = SubstringType(typeMirrorCompar.toString());
            }
        }
        else{
            umlType = SubstringType(typeMirror.toString());
        }
        if (isUmlMulti)
            umlType += " *";
        return umlType;
    }
    private String SubstringType(String string) {
        if (string.contains(".")){
            int index = 0;
            for(int i = 0; i< string.length(); i++){
                if(string.charAt(i) == '.'){
                    index = i;
                }
            }
            string = string.substring(index+1, string.length());
            if (string.contains(">"))
                string = string.substring(0, string.length()-1);
            return string;
        }
        else{
            return string;
        }
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
    public boolean getPumlVisibility(){
        return this.isPumlVisible;
    }
    void setToInvisible(){
        this.isPumlVisible = false;
    }
    void setToVisible(){
        this.isPumlVisible = false;
    }
}