package pumlFromJava;

import java.util.spi.ToolProvider;
public class Java2Puml
{
    public static void main(String[] args)
    {
        ToolProvider toolProvider = ToolProvider.findFirst("javadoc").get();
        System.out.println(toolProvider.name());

        //D:\Programmes\JDK\bin\javadoc.exe -private -sourcepath src/western/src -doclet FirstDoclet -docletpath out/production/p21_projet western

        toolProvider.run(System.out, System.err, args);
    }
}