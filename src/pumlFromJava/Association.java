package pumlFromJava;


import java.util.List;

public class Association {
    private ElementContent element1;
    private ElementContent element2;
    private String mult1;
    private String mult2;
    private Attribut attributLié;
    private TypeAssociation typeAssociation = TypeAssociation.SIMPLE;
    private boolean isPumlVisible = true; // Pour afficher ou non dans le diagramme
    public Association(ElementContent element1, ElementContent element2) {
        this.element1 = element1;
        this.element2 = element2;
    }
    public Association(ElementContent element1, ElementContent element2, TypeAssociation typeAssociation) {
        this.element1 = element1;
        this.element2 = element2;
        this.typeAssociation = typeAssociation;
    }
    public Association(ElementContent element1, ElementContent element2, TypeAssociation typeAssociation, String mult1, String mult2) {
        this.element1 = element1;
        this.element2 = element2;
        this.typeAssociation = typeAssociation;
        this.mult1 = mult1;
        this.mult2 = mult2;
    }
    public ElementContent getElement1(){
        return element1;
    }
    public ElementContent getElement2(){
        return element2;
    }

    public TypeAssociation getTypeAssociation() {
        return typeAssociation;
    }

    public boolean getPumlVisibilite(){return this.isPumlVisible;}

    public void setMult1(String mult1){
        this.mult1 = mult1;
    }

    public void setMult2(String mult2){
        this.mult2 = mult2;
    }

    public void setAttributLié(Attribut attribut){this.attributLié = attribut;}

    public void setToInvisible(){this.isPumlVisible = false;}

    public void setType(TypeAssociation typeAssociation){
        this.typeAssociation = typeAssociation;
    }

    public String genererAssociation(boolean isDca) {
        if(this.isPumlVisible) {
            String associationString = "";

            if(typeAssociation == TypeAssociation.AGREGATION)
                associationString += "\n'@PumlAggregation\n";
            else if(typeAssociation == TypeAssociation.COMPOSITION)
                associationString += "\n'@PumlComposition\n";

            associationString += element1.getNom() + " ";

            //Ajout de la multiplicité du premier élément
            if (typeAssociation == TypeAssociation.COMPOSITION || typeAssociation == TypeAssociation.AGREGATION) {
                if (mult1 != null)
                    associationString += "\"" + mult1 + "\" ";
                else if (mult1 == null && (typeAssociation != TypeAssociation.COMPOSITION && typeAssociation != TypeAssociation.AGREGATION))
                    associationString += "\"@PumlType\" ";
            }

            //Ajout du début de la flèche selon le type d'association
            if (typeAssociation == TypeAssociation.AGREGATION && !isDca)
                associationString += "o";
            else if (typeAssociation == TypeAssociation.COMPOSITION && !isDca)
                associationString += "*";

            //Ajout du corps de la flèche
            if (typeAssociation == TypeAssociation.IMPLEMENT)
                associationString += ".";
            else
                associationString += "-";
            for (int i = element1.getPumlEtage(); i < element2.getPumlEtage(); i++) {
                if (typeAssociation == TypeAssociation.IMPLEMENT)
                    associationString += ".";
                else
                    associationString += "-";
            }

            //Ajout de la pointe de la flèche
            if (!isDca && (typeAssociation == TypeAssociation.AGREGATION || typeAssociation == TypeAssociation.COMPOSITION) || typeAssociation == TypeAssociation.DEPENDANCE) {
                associationString += "> ";
            }
            if (typeAssociation == TypeAssociation.HERITAGE || typeAssociation == TypeAssociation.IMPLEMENT) {
                associationString += "|> ";
            }

            //ajout de la multiplicité du deuxième élément
            if(typeAssociation == TypeAssociation.COMPOSITION || typeAssociation == TypeAssociation.AGREGATION) {
                if (mult2 != null)
                    associationString += "\"" + mult2;
                else
                    associationString += "\"@PumlType";

                if (attributLié != null && !isDca)
                    associationString += "\\n " + attributLié.getPumlVisibilite() + " " + attributLié.getNom();
                else if(!isDca)
                    associationString += "\\n @PumlAssociation";
                associationString += "\" ";
            }

            //Ajout du nom du deuxième élément
            associationString += element2.getNom();

            if (typeAssociation == TypeAssociation.DEPENDANCE)
                return associationString += " : \"<<uses>>\"\\n >\n";
            else
                return associationString + " : \"          \\n >\"\n";
        }
        return "";
    }

    public void IncrementationMult(){
        if(this.mult2 != null && !this.mult2.equals("[*]") && !this.mult2.equals("*"))
            this.mult2 = Integer.toString(Integer.valueOf(this.mult2)+1);
    }
}
