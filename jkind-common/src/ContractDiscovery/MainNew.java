package ContractDiscovery;

import jkind.lustre.Program;
import jkind.lustre.parsing.LustreParseUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainNew {

    static String folderName = "/Users/sohahussein/git/jkind/testing/discovery-examples/";
    static String tFileNameNew = folderName + "RepairLibrary.lus";
    static String rFileName = folderName + "SimplePadReset.runPadSteps(IZZZZZ)V#27_0.txt";


    public static void main(String[] args) throws DiscoveryException, IOException {

        String programStrNew = null;

        try {
            programStrNew = new String(Files.readAllBytes(Paths.get(tFileNameNew)), "UTF-8");

        } catch (IOException e) {
            System.out.println("Problem reading file. " + e.getMessage());
        }

        Program newProgram = LustreParseUtil.program(programStrNew);


        System.out.println("here is the new program read\n" + newProgram.toString());

        //System.out.println("classpath is:" + System.getProperty("java.class.path"));


    }
}
