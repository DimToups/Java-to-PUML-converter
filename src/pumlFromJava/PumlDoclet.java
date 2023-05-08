package pumlFromJava;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import java.util.*;

public class PumlDoclet implements Doclet{
    @Override
    public void init(Locale locale, Reporter reporter) {  }
    @Override
    public String getName() {
        return "PumlDoclet";
    }
    @Override
    public Set<? extends Option> getSupportedOptions() {
        Set<Option> options = new HashSet<>();
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
                        return Kind.OTHER;
                    }
                    @Override
                    public List<String> getNames() {
                        List<String> names = new ArrayList<>();
                        names.add("-out");
                        return names;
                    }
                    @Override
                    public String getParameters() {
                        return "-out <p1>";
                    }
                    @Override
                    public boolean process(String s, List<String> list) {
                        return true;
                    }
                }
        );
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
        return true;
    }
}
