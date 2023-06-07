package pumlFromJava;


import java.util.List;

public class Association {
    private ElementContent element1;
    private ElementContent element2;
    private String mult1;
    private String mult2;
    private Attribut attributLié;
    private TypeAssociation typeAssociation = TypeAssociation.SIMPLE;
    private boolean isPumlVisible = true;
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
    public String genererAssociation(boolean isDca) {
        if(this.isPumlVisible) {
            String associationString = "";

            if(typeAssociation == TypeAssociation.AGREGATION)
                associationString += "\n'@PumlAggregation";
            else if(typeAssociation == TypeAssociation.COMPOSITION)
                associationString += "\n'@PumlComposition";

            associationString += "\n" + element1.getNom() + " ";

            //Ajout de la multiplicité du premier élément
            if(typeAssociation == TypeAssociation.COMPOSITION || typeAssociation == TypeAssociation.AGREGATION) {
                if (mult1 != null)
                    associationString += "\"" + mult1 + "\" ";
                else
                    associationString += "\"@PumlType\" ";
            }

            //Ajout du début de la flèche selon le type d'association
            if (!isDca) {
                if (typeAssociation == TypeAssociation.AGREGATION)
                    associationString += "o";
                else if (typeAssociation == TypeAssociation.COMPOSITION)
                    associationString += "*";
            }

            //Ajout du corps de la flèche
            for (int i = 0; i < 2; i++) {
                if ((i & 2) == 0) {
                    if (typeAssociation == TypeAssociation.IMPLEMENT)
                        associationString += ".";
                    else
                        associationString += "-";
                }
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

                if (attributLié != null)
                    associationString += "\\n " + attributLié.getPumlVisibilite() + " " + attributLié.getNom() + "\" ";
                else
                    associationString += "\\n @PumlAssociation\" ";
            }

            //Ajout du nom du deuxième élément
            associationString += element2.getNom();

            if (typeAssociation == TypeAssociation.DEPENDANCE)
                associationString += " : <<uses>>";

            return associationString + "\n";
        }
        return "";
    }
    public void setMult1(String mult1){
        this.mult1 = mult1;
    }
    public void setMult2(String mult2){
        this.mult2 = mult2;
    }
    public void setAttributLié(Attribut attribut){this.attributLié = attribut;}
    public boolean getPumlVisibilite(){return this.isPumlVisible;}
    public void setToInvisible(){this.isPumlVisible = false;}
    public void IncrementationMult(){
        if(this.mult2 != null && !this.mult2.equals("*"))
            this.mult2 = Integer.toString(Integer.valueOf(this.mult2)+1);
    }
    public void setType(TypeAssociation typeAssociation){
        this.typeAssociation = typeAssociation;
    }
}
