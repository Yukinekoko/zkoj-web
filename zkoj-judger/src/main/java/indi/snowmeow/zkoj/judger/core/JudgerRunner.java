package indi.snowmeow.zkoj.judger.core;

import indi.snowmeow.zkoj.judger.model.RunningResult;
import java.io.File;

/**
 * @author snowmeow
 * @date 2021/5/8
 */
public abstract class JudgerRunner {

    static {
        System.load("D:\\NasDrive\\Programmer\\Project\\ZKOJ\\source\\zkoj-web\\zkoj-dll.dll");
    }

    /** 环境包根目录 */
    protected static final String ENV_BASE_PATH =  "D:\\NasDrive\\judger-env\\";

    protected static final int DEFAULT_COMPILE_TIME_LIMIT = 10 * 1000;

    protected static final int DEFAULT_COMPILE_MEMORY_LIMIT = 500;

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

    public abstract RunningResult compile(String errorFilePath, String solutionFolderPath, String sourceFileName);

    public abstract RunningResult run(String inputFilePath, String outputFilePath, String errorFilePath,
                                      String solutionFolderPath, String targetFileName, int timeLimit, int memoryLimit);

}
