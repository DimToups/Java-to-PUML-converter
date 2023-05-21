package pumlFromJava;


import java.util.List;

public class Association {
    private ClassContent element1;
    private ClassContent element2;
    private TypeAssociation typeAssociation = TypeAssociation.SIMPLE;

    public Association(ClassContent element1, ClassContent element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    public Association(ClassContent element1, ClassContent element2, TypeAssociation typeAssociation) {
        this.element1 = element1;
        this.element2 = element2;
        this.typeAssociation = typeAssociation;
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
}
