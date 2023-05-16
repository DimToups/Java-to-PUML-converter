package pumlFromJava;


import java.util.List;

public class Liaison {
    String element1;
    String element2;
    TypeLiaison typeLiaison = TypeLiaison.SIMPLE;
    public Liaison(String element1, String element2){
        this.element1 = element1;
        this.element2 = element2;
    }
    public Liaison(String element1, String element2, TypeLiaison typeLiaison){
        this.element1 = element1;
        this.element2 = element2;
        this.typeLiaison = typeLiaison;
    }
}
