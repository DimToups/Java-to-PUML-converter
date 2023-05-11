package pumlFromJava;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;
import javax.lang.model.element.Element;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import java.sql.Array;
import java.util.*;

public class PumlDoclet implements Doclet{
    private String name;
    private String directory;

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
        PumlDiagram diagram = new PumlDiagram(name, directory);

        //Index d'ajout de valeurs dans classes[i]
        int i = 0;
        //Traitement de tous les fichiers
        for (Element element : docletEnvironment.getIncludedElements())
        {
            //Détermination du type de fichier à traiter
            //(Classe/Interface/Enumération)
            if (element.getKind() == ElementKind.CLASS)
            {
                classes.add(new ClassContent());
                classes.get(i).className = element.getSimpleName().toString();
                classes.get(i).classType = ElementKind.CLASS;
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
                i++;
            }
        }
        diagram.setClasses(classes, "western");
        diagram.makeDiagram();

        return true;
    }
}