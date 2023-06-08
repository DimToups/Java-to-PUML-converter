package pumlFromJava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GenerateurDiagramme {
    private String name;
    private String path;
    private String packageName;
    private boolean isDCA = false;
    public GenerateurDiagramme(String name, String path, String packageName){
        this.name = name;
        this.path = path;
        this.packageName = packageName;
    }
    public GenerateurDiagramme(String name, String path, String packageName, boolean isDCA){
        this.name = name;
        this.path = path;
        this.packageName = packageName;
        this.isDCA = isDCA;
    }
    public void setDCA(boolean DCA) {
        isDCA = DCA;
    }
    public void createFile(){
        File file = new File(path + "/" + name);
        try {
            if (!file.createNewFile()){
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(path + "/" + name));
                writer.write("");
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initFile(){
        try {
            String initFile = "@startuml\n" +
                    "'https://plantuml.com/class-diagram\n" +
                    "skinparam style strictuml\n" +
                    "skinparam classAttributeIconSize 0\n" +
                    "skinparam classFontStyle Bold\n" +
                    "\n" +
                    "hide empty members\n" +
                    "\n";
            initFile += "package " + packageName + "{\n";
            FileOutputStream fos = null;
            fos = new FileOutputStream(path + "/" + name, true);
            byte[] b = initFile.getBytes();
            fos.write(b);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void generateElementsForPuml(ArrayList<ElementContent> elements){
        for(ElementContent element : elements){
            try {
                String elementContent = element.genererContenuElement(isDCA) + "\n";
                FileOutputStream fos = null;
                fos = new FileOutputStream(path + "/" + name, true);
                byte[] b = elementContent.getBytes();
                fos.write(b);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void generateLinksForPuml(ArrayList<Association> associations){
        for(Association association : associations){
            try {
                String linkContent = association.genererAssociation(isDCA);
                FileOutputStream fos = null;
                fos = new FileOutputStream(path + "/" + name, true);
                byte[] b = linkContent.getBytes();
                fos.write(b);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void endFile(){
        try {
            String endFile = "\n}\n@enduml";
            FileOutputStream fos = null;
            fos = new FileOutputStream(path + "/" + name, true);
            byte[] b = endFile.getBytes();
            fos.write(b);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
