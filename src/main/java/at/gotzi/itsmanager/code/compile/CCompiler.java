package at.gotzi.itsmanager.code.compile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class CCompiler {

    private final String compilerPath;
    private final String code;
    private final File txtFile;
    private final File exeFile;

    public CCompiler(String compilerPath, String code, File txtFile, File exeFile) throws IOException {
        this.compilerPath = compilerPath;
        this.code = code;
        this.txtFile = txtFile;
        this.exeFile = exeFile;

        this.createTxtFile();
    }

    private void createTxtFile() throws IOException {
        txtFile.createNewFile();
        FileWriter fileWriter = new FileWriter(txtFile);
        fileWriter.write(code);
        fileWriter.flush();
        fileWriter.close();
    }

    public synchronized String compile() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(compilerPath +
                " " + txtFile.getPath() +
                " -o " + exeFile.getPath());
        Process process = runtime.exec(compilerPath +
                " " + txtFile.getPath() +
                " -o " + exeFile.getPath());

        return "ErrorOutput: \n" +process.errorReader().lines().collect(Collectors.joining("\n"))
                + "\n\nOther: \n" + process.inputReader().lines().collect(Collectors.joining("\n"));
    }
}
