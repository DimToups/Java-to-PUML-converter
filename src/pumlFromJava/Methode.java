package pumlFromJava;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import java.util.ArrayList;

public class Methode {
    private String nom;
    private TypeMirror type;
    private Visibilite visibilite = Visibilite.NONE;
    private Modificateur modificateur;
    private boolean isConstructor = false;
    private boolean isPumlVisible = true; // Pour afficher ou non dans le diagramme
    private ArrayList<Attribut> parametres = new ArrayList<>();

    public Methode(ExecutableElement executableElement) {
        this.nom = executableElement.getSimpleName().toString();
        this.type = executableElement.getReturnType();
        this.findModifier(executableElement);
        this.findVisibility(executableElement);
        this.setParameters(executableElement);
        if (executableElement.getKind() == ElementKind.CONSTRUCTOR)
            isConstructor = true;
    }

    public String getNom() {
        return this.nom;
    }

    public TypeMirror getType() {
        return this.type;
    }

    public Visibilite getVisibilite() {
        return this.visibilite;
    }

    public Modificateur getModificateur() {
        return this.modificateur;
    }

    public ArrayList<Attribut> getParameters() {
        return this.parametres;
    }

    public void setName(String string) {
        this.nom = string;
    }

    public void setParameters(Element element) {
        ExecutableElement executableElement = (ExecutableElement) element;
        for (VariableElement variableElement : executableElement.getParameters()) {
            Attribut attribut = new Attribut(variableElement);
            this.parametres.add(attribut);
        }
    }

    public void setToPumlInvisible() {
        this.isPumlVisible = false;
    }

    public String MethodetoString() {

        if (this.isPumlVisible) {
            String toString = "";
            if (this.getVisibilite().equals(Visibilite.PUBLIC))
                toString += "+ ";
            else if (this.getVisibilite().equals(Visibilite.PRIVATE))
                toString += "- ";
            else if (this.getVisibilite().equals(Visibilite.PROTECTED))
                toString += "# ";
            else
                toString += "~ ";

            if (isConstructor)
                toString += "<<create>> ";

            toString += this.nom + "(" + this.getStringParameters() + ")";

            //Ajout de son éventuel modificateur static
            if (this.modificateur == Modificateur.STATIC)
                toString += " {static}";
            if (this.modificateur == Modificateur.ABSTRACT)
                toString += " {abstract}";

            if (!type.toString().equals("void"))
                toString += " : " + findUmlType(this.type);

            return toString;
        }
        // Pour les méthodes qu'on ne va pas afficher telles que les méthodes enfants déclarées dans une class parent
        return "";
    }

    private String findUmlType(TypeMirror typeMirror) {
        boolean isUmlMulti = false;
        String umlType = "";
        if (typeMirror.toString().contains("java.util")) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            if(declaredType.getTypeArguments().size() > 0)
                isUmlMulti = true;
            for (TypeMirror typeMirrorCompar : declaredType.getTypeArguments()) {
                umlType = SubstringType(typeMirrorCompar.toString());
            }
        } else {
            umlType = SubstringType(typeMirror.toString());
        }
        if (isUmlMulti)
            umlType += " [*]";
        return umlType;
    }

    // Pour enlever tout ce qui a devant le nom du type
    // Ex : java.xxxx.xxxx.type
    // -> type
    public String SubstringType(String string) {
        if (string.contains(".") || string.contains("<") || string.contains(">") || string.contains("[") || string.contains("]")){
            int index = 0;
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == '.') {
                    index = i;
                }
            }
            if(index != 0)
                string = string.substring(index+1, string.length());
            if (string.charAt(string.length() -1) == '>' || string.charAt(string.length() -1) == '<' || string.charAt(string.length() -1) == ']' || string.charAt(string.length() -1) == '[') {
                string = string.substring(0, string.length()-1);
                string = this.SubstringType(string);
            }
            return string;
        } else {
            return string;
        }
    }

    public String getStringParameters() {
        String parameters = "";

        int i = 0;
        for (Attribut attribut : parametres) {
            if (i == 0)
                parameters += attribut.AttributtoString();
            else
                parameters += ", " + attribut.AttributtoString();
            i++;
        }

        return parameters;
    }

    public void findModifier(Element element) {
        for (Modifier modifier : element.getModifiers()) {
            if (modifier == Modifier.ABSTRACT)
                this.modificateur = Modificateur.ABSTRACT;
            else if (modifier == Modifier.FINAL)
                this.modificateur = Modificateur.FINAL;
            else if (modifier == Modifier.STATIC)
                this.modificateur = Modificateur.STATIC;
        }
    }

    public void findVisibility(Element element) {
        for (Modifier modifier : element.getModifiers()) {
            if (modifier == Modifier.PUBLIC)
                this.visibilite = Visibilite.PUBLIC;
            else if (modifier == Modifier.PROTECTED)
                this.visibilite = Visibilite.PROTECTED;
            else if (modifier == Modifier.PRIVATE)
                this.visibilite = Visibilite.PRIVATE;
        }
    }
}