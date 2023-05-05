package pumlFromJava;

import java.util.spi.ToolProvider;
public class Java2Puml
{
    public static void main(String[] args)
    {
        ToolProvider toolProvider = ToolProvider.findFirst("javadoc").get();
        System.out.println(toolProvider.name());

        //D:\Programmes\JDK\bin\javadoc.exe -private -sourcepath p21_projet/src/western/src -doclet pumlFromJava.FirstDoclet -docletpath out/production/SAE21 western

        toolProvider.run(System.out, System.err, args);
    }
}