package pumlFromJava;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import java.util.ArrayList;

public class Methode {
    private String nom;
    private TypeMirror type;
    private Visibilite visibilite;
    private Modificateur modificateur;
    private boolean isConstructor = false;
    private ArrayList<Attribut> parametres = new ArrayList<>();
    public Methode(ExecutableElement executableElement){
        this.nom = executableElement.getSimpleName().toString();
        this.type = executableElement.getReturnType();
        this.findModifier(executableElement);
        this.findVisibility(executableElement);
        this.setParameters(executableElement);
        if (executableElement.getKind() == ElementKind.CONSTRUCTOR)
            isConstructor = true;
    }
    public String getNom(){ return this.nom;}
    public TypeMirror getType(){return this.type;}
    public Visibilite getVisibilite(){return this.visibilite;}
    public Modificateur getModificateur(){return this.modificateur;}
    public void setName(String string) {this.nom = string;}
    public void setType(TypeMirror type) {this.type = type;}
    public void setVisibilite(Visibilite visibilite) {this.visibilite = visibilite;}
    public void setModificateur(Modificateur modificateur) {this.modificateur = modificateur;}
    public void setParameters(Element element){
        System.out.println("\t" + element.toString());
        ExecutableElement executableElement = (ExecutableElement) element;
        for(VariableElement variableElement : executableElement.getParameters()){
            Attribut attribut = new Attribut(variableElement);
            this.parametres.add(attribut);
            System.out.println("\t\t" + attribut.getNom() + " : " + attribut.getType());
        }
    }
    public String MethodetoString(){
        //Integer fullstop = this.type.toString().indexOf(".");
        String toString = "";
        if (this.getVisibilite().equals(Visibilite.PUBLIC))
            toString += "+ ";
        else if (this.getVisibilite().equals(Visibilite.PRIVATE))
            toString += "- ";
        else if (this.getVisibilite().equals(Visibilite.PROTECTED))
            toString += "# ";

        if (isConstructor)
            toString += "<<create>> ";

        toString += this.nom + "(" + this.getParameters() + ")";

        //Ajout de son éventuel modificateur static
        if(this.modificateur == Modificateur.STATIC)
            toString += " {static}";
        if (this.modificateur == Modificateur.ABSTRACT)
            toString += " {abstract}";

        if (!type.toString().equals("void")) {
            if (this.nom.equals("getCaptives"))
                System.out.println(this.nom);
            toString += " : " + findUmlType(this.type);
        }

        return toString;
    }
    private String findUmlType(TypeMirror typeMirror){
        boolean isUmlMulti = false;
        String umlType = "";
        if (typeMirror.toString().contains("java.util")){
            isUmlMulti = true;
            DeclaredType declaredType = (DeclaredType) typeMirror;
            for (TypeMirror typeMirrorCompar : declaredType.getTypeArguments()){
                System.out.println(SubstringType(typeMirrorCompar.toString()));
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
            return string;
        }
        else{
            return string;
        }
    }

    public String getParameters(){
        String parameters = "";

        int i = 0;
        for (Attribut attribut : parametres){
            if (i == 0)
                parameters += attribut.AttributtoString();
            else
                parameters += ", " + attribut.AttributtoString();
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
