package indi.snowmeow.zkoj.judger.core;

import indi.snowmeow.zkoj.judger.model.RunningResult;
import java.io.File;

/**
 * @author snowmeow
 * @date 2021/5/8
 */
public class JudgerRunner {

    /** 环境包根目录 */
    private static final String ENV_BASE_PATH =  "D:\\NasDrive\\judger-env\\env\\";

    private static final int DEFAULT_COMPILE_TIME_LIMIT = 10 * 1000;

    private static final int DEFAULT_COMPILE_MEMORY_LIMIT = 500;

    /** 运行进程
     * @param cmdLine           命令行指令
     * @param inputFilePath     输入文件路径(可为NULL)
     * @param outputFilePath    输出文件路径(可为NULL)
     * @param errorFilePath     错误输出文件路径（可为NULL）
     * @param timeLimit         时间限制(ms, 0表示无限制)
     * @param memoryLimit       内存限制(KB, 0表示无限制)
     * @return                  运行结果
     * */
    public native RunningResult execute(String cmdLine,
                                    String inputFilePath,
                                    String outputFilePath,
                                    String errorFilePath,
                                    int timeLimit,
                                    int memoryLimit);

    public RunningResult compileJava(String sourceFilePath, String errorFilePath) {
        String commandFormat = ENV_BASE_PATH + "Java"
                + File.separator + "bin" + File.separator + "javac %s";
        String command = String.format(commandFormat, sourceFilePath);
        return execute(command, null, null, errorFilePath,
                DEFAULT_COMPILE_TIME_LIMIT, DEFAULT_COMPILE_MEMORY_LIMIT);
    }

    public RunningResult compileC(String sourceFilePath, String compileOutputFilePath, String errorFilePath) {
        String commandFormat = ENV_BASE_PATH + "MinGW"
                + File.separator + "bin" + File.separator + "gcc %s -o %s -fno-asm -Wall -lm --static";
        String command = String.format(commandFormat, sourceFilePath, compileOutputFilePath);
        return execute(command, null, null, errorFilePath,
                DEFAULT_COMPILE_TIME_LIMIT, DEFAULT_COMPILE_MEMORY_LIMIT);
    }

    public RunningResult compileCPP(String sourceFilePath, String compileOutputFilePath, String errorFilePath) {
        String commandFormat = ENV_BASE_PATH + "MinGW"
                + File.separator + "bin" + File.separator + "g++ -fno-asm -Wall -lm --static -o %s %s";
        String command = String.format(commandFormat, compileOutputFilePath, sourceFilePath);
        return execute(command, null, null, errorFilePath,
                DEFAULT_COMPILE_TIME_LIMIT, DEFAULT_COMPILE_MEMORY_LIMIT);
    }

    public RunningResult runJava(String solutionPath, String className,
                                 String inputFilePath, String outputFilePath, String errorFilePath,
                                 int timeLimit, int memoryLimit) {
        String commandFormat = ENV_BASE_PATH + "Java"
                + File.separator + "bin" + File.separator + "java -cp %s %s";
        String command = String.format(commandFormat, solutionPath, className);
        return execute(command, inputFilePath, outputFilePath, errorFilePath, timeLimit, memoryLimit);
    }

    public RunningResult runC(String solutionPath, String programName, String inputFilePath,
                              String outputFilePath, String errorFilePath, int timeLimit, int memoryLimit) {
        String command = solutionPath + programName;
        return execute(command, inputFilePath, outputFilePath, errorFilePath, timeLimit, memoryLimit);
    }

    public RunningResult runCPP(String solutionPath, String programName, String inputFilePath,
                                String outputFilePath, String errorFilePath, int timeLimit, int memoryLimit) {
        String command = solutionPath + programName;
        return execute(command, inputFilePath, outputFilePath, errorFilePath, timeLimit, memoryLimit);
    }

    public RunningResult runPython(String sourceFilePath, String inputFilePath, String outputFilePath,
                                   String errorFilePath, int timeLimit, int memoryLimit) {
        String commandFormat = ENV_BASE_PATH + "Python"
                + File.separator + "python %s";
        String command = String.format(commandFormat, sourceFilePath);
        return execute(command, inputFilePath, outputFilePath, errorFilePath, timeLimit, memoryLimit);
    }


}
