package indi.snowmeow.zkoj.judger.core.runner;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.judger.core.JudgerRunner;
import indi.snowmeow.zkoj.judger.model.RunningResult;

import java.io.File;

/**
 * @author snowmeow
 * @date 2021/5/13
 */
public class PythonRunner extends JudgerRunner {

    private static final String RUN_COMMAND = ENV_BASE_PATH + "Python"
            + File.separator + "python %s";

    @Override
    public RunningResult compile(String errorFilePath, String solutionFolderPath, String sourceFileName) {
        throw new BaseException(ResultCodeEnum.JUDGER_ERROR, "错误地执行了PythonRunner.compile()！");
    }

    @Override
    public RunningResult run(String inputFilePath, String outputFilePath, String errorFilePath, String solutionFolderPath, String targetFileName, int timeLimit, int memoryLimit) {
        String sourceFilePath = solutionFolderPath + targetFileName + ".py";
        String command = String.format(RUN_COMMAND, sourceFilePath);
        return execute(command, inputFilePath, outputFilePath, errorFilePath, timeLimit, memoryLimit);

    }
}
