package pumlFromJava;

import javax.lang.model.element.ElementKind;
import java.util.ArrayList;

public class ClassContent{
    String className;
    ElementKind classType;
    ArrayList<Attribut> classAttributs = new ArrayList<>();
    ArrayList<Methode> classMethods = new ArrayList<>();
}