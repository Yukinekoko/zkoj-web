package indi.snowmeow.zkoj.judger.core.runner;

import indi.snowmeow.zkoj.judger.core.JudgerRunner;
import indi.snowmeow.zkoj.judger.model.RunningResult;

import java.io.File;

/**
 * @author snowmeow
 * @date 2021/5/13
 */
public class CPPRunner extends JudgerRunner {

    private static final String COMPILE_COMMAND = ENV_BASE_PATH + "MinGW"
            + File.separator + "bin" + File.separator + "g++ -fno-asm -Wall -lm --static -o %s %s";

/*    private static final String RUN_COMMAND = ENV_BASE_PATH + "Java"
            + File.separator + "bin" + File.separator + "java -cp %s %s";*/

    @Override
    public RunningResult compile(String errorFilePath, String solutionFolderPath, String sourceFileName) {
        String sourceFilePath = solutionFolderPath + sourceFileName + ".cc";
        String compileOutputFilePath = solutionFolderPath + "Main";
        String command = String.format(COMPILE_COMMAND, compileOutputFilePath, sourceFilePath);
        return execute(command, null, null, errorFilePath,
                DEFAULT_COMPILE_TIME_LIMIT, DEFAULT_COMPILE_MEMORY_LIMIT);

    }

    @Override
    public RunningResult run(String inputFilePath, String outputFilePath, String errorFilePath, String solutionFolderPath, String targetFileName, int timeLimit, int memoryLimit) {
        String command = solutionFolderPath + "Main";
        return execute(command, inputFilePath, outputFilePath, errorFilePath, timeLimit, memoryLimit);

    }
}
