package pumlFromJava;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;
import western.Dame;

import javax.lang.model.element.*;

import javax.lang.model.SourceVersion;
import javax.lang.model.type.TypeMirror;
import java.util.*;

public class PumlDoclet implements Doclet{
    private String name;
    private String directory;
    private String packageName;

    private static boolean debug = true;
    @Override
    public void init(Locale locale, Reporter reporter) {  }
    @Override
    public String getName() {
        return "PumlDoclet";
    }
    @Override
    public Set<? extends Option> getSupportedOptions() {
        Set<Option> options = new HashSet<>();
        //-d
        options.add(
                new Option() {
                    @Override
                    public int getArgumentCount() {
                        return 1;
                    }

                    @Override
                    public String getDescription() {
                        return "Détermine le répertoire de stockage du fichier créé.";
                    }

                    @Override
                    public Kind getKind() {
                        return Kind.STANDARD;
                    }

                    @Override
                    public List<String> getNames() {
                        List<String> names = new ArrayList<>();
                        names.add("-d");
                        return names;
                    }

                    @Override
                    public String getParameters() {
                        return "-d <p1>";
                    }

                    @Override
                    public boolean process(String s, List<String> list) {
                        directory = list.get(0);
                        return true;
                    }
                }
        );
        //-out
        options.add(
                new Option() {
                    @Override
                    public int getArgumentCount() {
                        return 1;
                    }
                    @Override
                    public String getDescription() {
                        return "Fixe le nom du fichier créé.";
                    }
                    @Override
                    public Kind getKind() {
                        return Kind.STANDARD;
                    }
                    @Override
                    public List<String> getNames() {
                        List<String> names = new ArrayList<>();
                        names.add("-out");
                        return names;
                    }
                    @Override
                    public String getParameters() {return "-out <p1>";}
                    @Override
                    public boolean process(String s, List<String> list) {
                        if (!list.get(0).endsWith(".puml"))
                            name = list.get(0) + ".puml";
                        else
                            name = list.get(0);
                        return true;
                    }
                }
        );
        return options;
    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
    @Override
    public boolean run(DocletEnvironment docletEnvironment) {
        // Créé une liste de classes qu'on va remplir en parcourant les
        ArrayList<ClassContent> classes = new ArrayList<>();
        ArrayList<Liaison> liaisons = new ArrayList<>();
        PumlDiagram diagram = new PumlDiagram(name, directory);

        //Index d'ajout de valeurs dans classes[i]
        int i = 0;
        //Traitement de tous les fichiers pour obtenir toutes les informations sur leur contenu
        for (Element element : docletEnvironment.getIncludedElements())
        {
            //Détermination du type de fichier à traiter
            //(Classe/Interface/Enumération)
            if (element.getKind() == ElementKind.CLASS)
            {
                classes.add(new ClassContent());
                classes.get(i).className = element.getSimpleName().toString();
                classes.get(i).classType = ElementKind.CLASS;

                //Recherche d'un héritage
                TypeElement typeElement = (TypeElement) element;
                for (Element elementCompar : docletEnvironment.getIncludedElements()){
                    if (elementCompar.toString().equals(typeElement.getSuperclass().toString())) {
                        Liaison newLiaison = new Liaison(element.getSimpleName().toString(), elementCompar.getSimpleName().toString(), TypeLiaison.HERITAGE);
                        liaisons.add(newLiaison);
                    }
                }
                for (TypeMirror interfaceElement : ((TypeElement) element).getInterfaces()){
                    for (Element elementCompar : docletEnvironment.getIncludedElements()){
                        if (elementCompar.toString().equals(interfaceElement.toString())){
                            Liaison newLiaison = new Liaison(element.getSimpleName().toString(), elementCompar.getSimpleName().toString(), TypeLiaison.IMPLEMENT);
                            liaisons.add(newLiaison);
                        }
                    }
                }

                //Traitement de tous les éléments interne de la classe
                for (Element enclosedElement : element.getEnclosedElements()){
                    //Traitement des attributs
                    if (enclosedElement.getKind().isField() && !enclosedElement.asType().toString().contains(".")){
                        classes.get(i).classAttributs.add(new Attribut(enclosedElement.getSimpleName().toString()));
                    }
                    //Traitement des liens
                    if (enclosedElement.getKind() == ElementKind.INTERFACE) {
                        Liaison newLiaison = new Liaison(element.getSimpleName().toString(), enclosedElement.getSimpleName().toString(), TypeLiaison.IMPLEMENT);
                        liaisons.add(newLiaison);
                    }
                    if (enclosedElement.getKind().isField()){
                        for (Element elementCompar : docletEnvironment.getIncludedElements()) {
                            //Lien simple
                            if (elementCompar.asType() == enclosedElement.asType() && elementCompar != element) {
                                Liaison newLiaison = new Liaison(element.getSimpleName().toString(), elementCompar.getSimpleName().toString(), TypeLiaison.SIMPLE);
                                liaisons.add(newLiaison);
                            }
                        }
                    }
                }
                i++;
            }
            else if (element.getKind() == ElementKind.INTERFACE)
            {
                classes.add(new ClassContent());
                classes.get(i).className = element.getSimpleName().toString();
                classes.get(i).classType = ElementKind.INTERFACE;
                i++;
            }
            else if (element.getKind() == ElementKind.ENUM)
            {
                classes.add(new ClassContent());
                classes.get(i).className = element.getSimpleName().toString();
                classes.get(i).classType = ElementKind.ENUM;
                //Traitement de tous les éléments interne de l'interface
                for (Element enclosedElement : element.getEnclosedElements()){
                    if (enclosedElement.getKind().isField()){
                        classes.get(i).classAttributs.add(new Attribut(enclosedElement.getSimpleName().toString()));
                    }
                }
                i++;
            }
            else if (element.getKind() == ElementKind.PACKAGE)
                packageName = element.getSimpleName().toString();
        }
        diagram.setClasses(classes, liaisons, packageName);
        diagram.makeDiagram();

        return true;
    }
}