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
        String liaisonString = element1.getNom() + " ";
        for (int i = 0; i < 2; i++){
            liaisonString += "-";
        }
        liaisonString += " " + element2.getNom();
        return liaisonString;
    }
}
