package ContractDiscovery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jkind.lustre.Program;
import jkind.lustre.parsing.LustreParseUtil;
public class Main {

    static String folderName = "/Users/sohahussein/git/jkind/testing/discovery-examples/";
    //static String tFileName = folderName + "PadModelReset.lus";
    static String tFileName = folderName + "RepairLibrary.lus";
    static String rFileName = folderName + "SimplePadReset.runPadSteps(IZZZZZ)V#27_0.txt";


    public static void main(String[] args) throws DiscoveryException, IOException {

        String programStr = null;
        try {
            programStr = new String(Files.readAllBytes(Paths.get(tFileName)), "UTF-8");

        } catch (IOException e) {
            System.out.println("Problem reading file. " + e.getMessage());
        }

        Program program = LustreParseUtil.program(programStr);
        System.out.println("here is the program read\n" + program.toString());

        System.out.println("classpath is:" + System.getProperty("java.class.path"));


    }
}
