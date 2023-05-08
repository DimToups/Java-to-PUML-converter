package pumlFromJava;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

public class PumlDoclet implements Doclet{
    @Override
    public void init(Locale locale, Reporter reporter) {  }
    @Override
    public String getName() {
        return "PumlDoclet";
    }
    @Override
    public Set<? extends Option> getSupportedOptions() {
        return null;
    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return null;
    }

    @Override
    public boolean run(DocletEnvironment docletEnvironment) {
        return false;
    }
}
