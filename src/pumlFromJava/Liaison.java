package pumlFromJava;


import java.util.List;

public class Liaison {
    private ClassContent element1;
    private ClassContent element2;
    private TypeLiaison typeLiaison = TypeLiaison.SIMPLE;
    public Liaison(ClassContent element1, ClassContent element2){
        this.element1 = element1;
        this.element2 = element2;
    }
    public Liaison(ClassContent element1, ClassContent element2, TypeLiaison typeLiaison){
        this.element1 = element1;
        this.element2 = element2;
        this.typeLiaison = typeLiaison;
    }
    public String genererLiaison(){
        //La méthode entière est à refaire, ce n'est pas permanent
        String liaisonString = element1.getNom() + " ";
        for (int i = 0; i < 2; i++){
            if ((i & 2) == 0) {
                if (typeLiaison == TypeLiaison.IMPLEMENT){
                    liaisonString += ".";
                }
                else {
                    liaisonString += "-";
                }
            }
        }
        if (typeLiaison == TypeLiaison.HERITAGE || typeLiaison == TypeLiaison.IMPLEMENT){
            liaisonString += "|>";
        }
        liaisonString += " " + element2.getNom() + "\n";
        return liaisonString;
    }
}
