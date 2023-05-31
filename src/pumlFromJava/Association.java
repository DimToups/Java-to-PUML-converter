package pumlFromJava;


import java.util.List;

public class Association {
    private ElementContent element1;
    private ElementContent element2;
    private char mult1;
    private char mult2;
    private TypeAssociation typeAssociation = TypeAssociation.SIMPLE;

    public Association(ElementContent element1, ElementContent element2) {
        this.element1 = element1;
        this.element2 = element2;
    }
    public Association(ElementContent element1, ElementContent element2, TypeAssociation typeAssociation) {
        this.element1 = element1;
        this.element2 = element2;
        this.typeAssociation = typeAssociation;
    }
    public Association(ElementContent element1, ElementContent element2, TypeAssociation typeAssociation, char mult1, char mult2) {
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
    public String genererAssociation() {
        //La méthode entière sera à refaire pour rendre le tout plus agréable visuellement parlant
        String associationString = "\n" + element1.getNom() + " ";
        for (int i = 0; i < 2; i++) {
            if ((i & 2) == 0) {
                if (typeAssociation == TypeAssociation.IMPLEMENT) {
                    associationString += ".";
                } else {
                    associationString += "-";
                }
            }
        }
        if (typeAssociation == TypeAssociation.HERITAGE || typeAssociation == TypeAssociation.IMPLEMENT) {
            associationString += "|>";
        }
        associationString += " " + element2.getNom();
        return associationString;
    }
    public void setMult1(char mult1){
        this.mult1 = mult1;
    }
    public void setMult2(char mult2){
        this.mult2 = mult2;
    }
}
