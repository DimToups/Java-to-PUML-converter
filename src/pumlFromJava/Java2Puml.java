package pumlFromJava;

import java.util.spi.ToolProvider;
public class Java2Puml
{
    public static void main(String[] args)
    {
        ToolProvider toolProvider = ToolProvider.findFirst("javadoc").get();
        System.out.println(toolProvider.name());

        //Commande de lancement du doclet FirstDoclet sur Windows 10
        //D:\Programmes\JDK\bin\javadoc.exe -private -sourcepath src/western/src -doclet pumlFromJava.FirstDoclet -docletpath out/production/p21_projet western
        //Commande de lancement du doclet PumlDoclet sur Windows 10
        //D:\Programmes\JDK\bin\javadoc.exe -private -sourcepath src/western/src -doclet pumlFromJava.PumlDoclet -docletpath out/production/p21_projet western

        toolProvider.run(System.out, System.err, args);
    }
}