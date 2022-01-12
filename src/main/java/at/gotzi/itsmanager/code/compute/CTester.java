package at.gotzi.itsmanager.code.compute;

import at.gotzi.itsmanager.api.workspace.question.Input;
import at.gotzi.itsmanager.api.workspace.question.Question;

import java.io.*;
import java.util.stream.Collectors;

public class CTester {

    private final Question.QuestionTest questionTest;
    private final File exeFile;
    private final StringBuilder expectedBuilder = new StringBuilder();

    private boolean finished = false;
    private Process process;

    public CTester(Question.QuestionTest questionTest, File exeFile) {
        this.questionTest = questionTest;
        this.exeFile = exeFile;
    }

    private synchronized void setFinished() {
        this.finished = true;
    }

    private synchronized void appendExpected(String str) {
        expectedBuilder.append(str);
    }

    private synchronized StringBuilder getExpectedBuilder() {
        return expectedBuilder;
    }

    private synchronized void setProcess(Process process) {
        this.process = process;
    }

    private synchronized void destroyProcess() {
        this.process.destroy();
    }

    public String compute() throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("debug 1");
        Thread thread = new Thread(() -> {
            System.out.println("Thread 1");
            Process process = null;
            try {
                System.out.println("Thread 2");
                process = runtime.exec(exeFile.getPath());
                setProcess(process);
                System.out.println("Thread 3");
                if (!questionTest.getInputs().isEmpty()) {
                    BufferedWriter inputWriter = process.outputWriter();
                    for (Input input : questionTest.getInputs()) {
                        inputWriter.write(input.input() + "\n");
                    }

                    try {
                        inputWriter.close();
                    } catch (Exception ignored) {}
                }

                BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
                appendExpected(output.lines().collect(Collectors.joining("\n")));
                setFinished();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        Thread.sleep(500);
        destroyProcess();
        thread.stop();
        System.out.println("debug 5");
        if (!this.finished)
            return "--Runtime Error--";
        System.out.println("debug 6");
        return getExpectedBuilder().toString();
    }
}


/*
#include <stdio.h>

int main() {
    char c;

    printf("Hello World");
    scanf("%c", &c);
    printf("nice Wert: %c", c);

    return 0;
}
 */
