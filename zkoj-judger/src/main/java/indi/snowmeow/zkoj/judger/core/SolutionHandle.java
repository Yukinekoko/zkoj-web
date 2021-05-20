package indi.snowmeow.zkoj.judger.core;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.base.common.enums.SolutionStatusEnum;
import indi.snowmeow.zkoj.judger.core.runner.CPPRunner;
import indi.snowmeow.zkoj.judger.core.runner.CRunner;
import indi.snowmeow.zkoj.judger.core.runner.JavaRunner;
import indi.snowmeow.zkoj.judger.core.runner.PythonRunner;
import indi.snowmeow.zkoj.judger.model.RunningResult;
import indi.snowmeow.zkoj.judger.model.SolutionRequest;
import indi.snowmeow.zkoj.judger.model.entity.PmsLanguage;
import indi.snowmeow.zkoj.judger.model.entity.PmsProblemCheckPoint;
import indi.snowmeow.zkoj.judger.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import indi.snowmeow.zkoj.judger.service.SolutionService;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 获取源文件名以及编译后的文件名部分还需要改进
 * @author snowmeow
 * @date 2021/5/8
 */
@Component
public class SolutionHandle {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private SolutionService solutionService;

    private List<JudgerRunner> runners;

    private static final String RESOURCE_PATH = new File("").getAbsolutePath() + File.separator + "res" + File.separator;
    /** 评测相关文件基础目录 */
    private static final String PROBLEM_DATA_BASE_PATH = RESOURCE_PATH + "problem" + File.separator;

    private static final String SOLUTION_DATA_PATH = RESOURCE_PATH + "solution" + File.separator;

    public SolutionHandle() {
        initRunner();
    }

    public boolean newSolution(SolutionRequest solutionRequest) {

        List<PmsProblemCheckPoint> checkPointList =
                problemService.findCheckPointListFromProblemId(solutionRequest.getProblemId());
        if (checkPointList == null || checkPointList.isEmpty()) {
            throw new BaseException(ResultCodeEnum.JUDGER_ERROR, "未找到问题测试数据");
        }
        String sourceCode = solutionService.findSourceCodeFromSolutionId(solutionRequest.getSolutionId());
        if (StringUtils.isEmpty(sourceCode)) {
            throw new BaseException(ResultCodeEnum.JUDGER_ERROR, "未获取到源代码");
        }

        initProblemData(solutionRequest.getProblemId(), solutionRequest.getProblemVersion(), checkPointList);
        createSourceCodeFile(solutionRequest.getSolutionId(), sourceCode, solutionRequest.getLanguageId());
        judge(solutionRequest, checkPointList);
        return true;
    }

    /**
     * 执行判题
     * */
    protected void judge(SolutionRequest solutionRequest, List<PmsProblemCheckPoint> checkPointList) {
        String problemDataFolderPath = PROBLEM_DATA_BASE_PATH
                + String.format("p_%d_%d", solutionRequest.getProblemId(), solutionRequest.getProblemVersion());
        String solutionFolderPath = SOLUTION_DATA_PATH + String.format("s_%d", solutionRequest.getSolutionId());
        long languageId = solutionRequest.getLanguageId();
        long solutionId = solutionRequest.getSolutionId();
        String compileErrorFilePath = solutionFolderPath + File.separator + "error_0";
        JudgerRunner runner;
        if (languageId == 1) {
            // C
            runner = runners.get(0);
        } else if (languageId == 2) {
            // CPP
            runner = runners.get(1);
        } else if (languageId == 3) {
            // JAVA
            runner = runners.get(2);
        } else if (languageId == 4) {
            // Python
            runner = runners.get(3);
        } else {
            throw new BaseException(ResultCodeEnum.JUDGER_ERROR, "语言ID错误");
        }

        if (languageId != 4) {
            solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.C);
            RunningResult compileResult =
                    runner.compile(compileErrorFilePath, solutionFolderPath + File.separator, getSourceFileName(languageId));
            if (compileResult.getCode() != 0) {
                // TODO 非常规编译运行异常
                solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.CE);
                System.out.println("编译运行异常");
                System.out.println(compileResult.toString());
                return;
            }
            File compileErrorFile = new File(compileErrorFilePath);
            if (compileErrorFile.length() != 0) {
                solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.CE);
                return;
            }
        }

        solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.R);
        for (int i = 0; i < checkPointList.size(); i++) {
            String inputFilePath = problemDataFolderPath + File.separator + "input_" + (i + 1);
            String outputFilePath = solutionFolderPath + File.separator + "output_" + (i + 1);
            String errorFilePath = solutionFolderPath + File.separator + "error_" + (i + 1);
            RunningResult result =
                    runner.run(inputFilePath, outputFilePath, errorFilePath,
                            solutionFolderPath + File.separator, getSourceFileName(languageId),
                            solutionRequest.getTimeLimit(), solutionRequest.getMemoryLimit());

            File errorFile = new File(errorFilePath);
            if (errorFile.length() != 0){
                solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.RE);
                return;
            }

            if (result.getCode() != 0) {
                switch (result.getCode()) {
                    case 1 :
                        // 未知异常
                        solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.RE);
                        break;
                    case 2 :
                        // 超内存
                        solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.MLE);
                        break;
                    case 3 :
                        // 超时
                        solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.TLE);
                        break;
                    case 4 :
                        //运行时异常
                        solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.RE);
                }
                return;
            }

            // 答案对比
            StringBuilder outputStringBuilder = new StringBuilder();
            try {
                FileReader outputFileReader = new FileReader(outputFilePath);
                char[] buf = new char[1024];
                int bufCount = 0;
                while ((bufCount = outputFileReader.read(buf)) != -1) {
                    outputStringBuilder.append(buf, 0, bufCount);
                }
                outputFileReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!compareResult(checkPointList.get(i).getOutput(), outputStringBuilder.toString())) {
                solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.WA);
                return;
            }
        }
        solutionService.updateSolutionStatus(solutionId, SolutionStatusEnum.AC);
    }

    protected boolean compareResult(String sampleOutput, String clientOutput) {
        clientOutput = trimEnd(clientOutput);
        clientOutput = trimEnd(sampleOutput);
        String[] sample = sampleOutput.split("\n");
        String[] client = clientOutput.split("\n");
        if (sample.length != client.length) {
            return false;
        }
        for (int i = 0; i < sample.length; i++) {
            String s1 = trimEnd(sample[i]);
            String s2 = trimEnd(client[i]);
            if (!s1.equals(s2)) {
                return false;
            }
        }
        return true;
    }

    // TODO 在不同操作系统下对换行符回车符的处理问题
    protected String trimEnd(String str) {
        char[] c = str.toCharArray();
        int len = c.length;
        while (len > 0 && (c[len - 1] == ' ' || c[len - 1] == '\n' || c[len - 1] == '\r')) {
            len--;
        }
        return new String(c, 0, len);
    }

    /**
     * 初始化创建各个语言的Runner类
     * */
    protected void initRunner() {
        runners = new ArrayList<>(4);
        runners.add(new CRunner());
        runners.add(new CPPRunner());
        runners.add(new JavaRunner());
        runners.add(new PythonRunner());
    }

    /**
     * 初始化问题测试数据：检查问题数据是否存在，是否完整，如果不存在或不完整则创建数据
     * @param problemId 问题ID
     * @param version 问题版本号
     * @param checkPointList 测试数据列表
     * */
    protected synchronized void initProblemData(long problemId, short version,
                                                List<PmsProblemCheckPoint> checkPointList) {
        String dataFolderPath = PROBLEM_DATA_BASE_PATH + String.format("p_%d_%d", problemId, version);
        File dataFolderFile = new File(dataFolderPath);
        if (!dataFolderFile.exists()) {
            createProblemDataFile(dataFolderFile, checkPointList);
        } else if (!checkProblemDataFile(dataFolderFile, checkPointList.size())) {
            refreshProblemDataFile(dataFolderFile, checkPointList);
        }
    }

    /**
     * 刷新问题的测试数据
     * @param dataFolderFile 问题测试数据文件夹
     * @param checkPointList 测试数据列表
     * */
    protected synchronized void refreshProblemDataFile(File dataFolderFile, List<PmsProblemCheckPoint> checkPointList) {
        if (!dataFolderFile.delete()) {
            throw new BaseException(ResultCodeEnum.JUDGER_ERROR, "问题数据文件夹删除失败");
        }
        createProblemDataFile(dataFolderFile, checkPointList);
    }

    /**
     * 在文件系统创建指定问题的测试数据
     * @param dataFolderFile 问题测试数据文件夹
     * @param checkPointList 测试数据列表
     * */
    protected synchronized void createProblemDataFile(File dataFolderFile, List<PmsProblemCheckPoint> checkPointList) {
        if (!dataFolderFile.mkdirs()) {
            throw new BaseException(ResultCodeEnum.JUDGER_ERROR);
        }
        try {
            for (int i = 0; i < checkPointList.size(); i++) {
                File checkPointFile = new File(dataFolderFile, String.format("input_%d", i + 1));
                FileWriter fw = new FileWriter(checkPointFile);
                fw.write(checkPointList.get(i).getInput());
                fw.flush();
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查文件系统中是否有指定的问题测试数据
     * @param dataFolderFile 问题测试数据文件夹
     * @param size 测试点数量
     * @return 是否存在问题测试数据
     * */
    protected boolean checkProblemDataFile(File dataFolderFile, int size) {
        for (int i = 0; i < size; i++) {
            File file = new File(dataFolderFile, String.format("input_%d", i + 1));
            if (!file.exists()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在文件系统创建源代码文件
     * @param solutionId 评测ID
     * @param sourceCode 源代码
     * @param languageId 语言ID
     * */
    protected void createSourceCodeFile(long solutionId, String sourceCode, long languageId) {
        File solutionFolderFile = new File(SOLUTION_DATA_PATH + String.format("s_%d", solutionId));
        if (!solutionFolderFile.mkdirs()) {
            throw new BaseException(ResultCodeEnum.JUDGER_ERROR, "评测文件夹已存在，创建失败");
        }
        File sourceCodeFile;
        if(languageId == 1) {
            // C
            sourceCodeFile = new File(solutionFolderFile, "Main.c");
        } else if(languageId == 2) {
            // CPP
            sourceCodeFile = new File(solutionFolderFile, "Main.cc");
        } else if(languageId == 3) {
            // JAVA
            sourceCodeFile = new File(solutionFolderFile, "Main.java");
        } else if(languageId == 4) {
            //　PYTHON
            sourceCodeFile = new File(solutionFolderFile, "Main.py");
        } else {
            throw new BaseException(ResultCodeEnum.JUDGER_ERROR, "语言ID不存在！");
        }
        try {
            FileWriter fw = new FileWriter(sourceCodeFile);
            fw.write(sourceCode);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据不同语言获取不同的预设文件名
     * @param languageId 语言ID
     * @return 文源文件名称
     * */
    protected String getSourceFileName(long languageId) {
        if (languageId == 1) {
            return "Main";
        } else if (languageId == 2) {
            return "Main";
        } else if (languageId == 3) {
            return "Main";
        } else if (languageId == 4) {
            return "Main";
        } else {
            throw new BaseException(ResultCodeEnum.JUDGER_ERROR, "SolutionHandler.getSourceFileName()");
        }
    }
}
