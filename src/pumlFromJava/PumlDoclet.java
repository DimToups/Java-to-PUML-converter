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
        ArrayList<String> classesName = new ArrayList<String>();
        ArrayList<String> classesType = new ArrayList<String>();
        PumlDiagram diagram = new PumlDiagram(name, directory);

        for (Element element : docletEnvironment.getIncludedElements())
        {
            if (element.getKind() == ElementKind.CLASS)
            {
                classesName.add(element.getSimpleName().toString());
                classesType.add("class");
            }
            else if (element.getKind() == ElementKind.INTERFACE)
            {
                classesName.add(element.getSimpleName().toString());
                classesType.add("interface");
            }
            else if (element.getKind() == ElementKind.ENUM)
            {
                classesName.add(element.getSimpleName().toString());
                classesType.add("enum");
            }
        }
        diagram.setClasses(classesName, classesType, "western");
        diagram.makeDiagram();

        return true;
    }
}