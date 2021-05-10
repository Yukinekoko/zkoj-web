package indi.snowmeow.zkoj.judger.core;

import indi.snowmeow.zkoj.base.common.base.BaseException;
import indi.snowmeow.zkoj.base.common.enums.ResultCodeEnum;
import indi.snowmeow.zkoj.judger.model.SolutionRequest;
import indi.snowmeow.zkoj.judger.model.entity.PmsLanguage;
import indi.snowmeow.zkoj.judger.model.entity.PmsProblemCheckPoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author snowmeow
 * @date 2021/5/8
 */
public class SolutionHandle {

    private static final String RESOURCE_PATH = new File("").getAbsolutePath() + File.separator + "res" + File.separator;
    /** 评测相关文件基础目录 */
    private static final String PROBLEM_DATA_BASE_PATH = RESOURCE_PATH + "problem" + File.separator;

    private static final String SOLUTION_DATA_PATH = RESOURCE_PATH + "solution" + File.separator;

    public boolean newSolution(SolutionRequest solutionRequest) {
        return false;
    }

    protected void runAndJudge() {

    }

    protected boolean compile() {
        return false;
    }

    protected synchronized void initProblemData(long problemId, short version,
                                                List<PmsProblemCheckPoint> checkPointList) {
        String dataFolderPath = PROBLEM_DATA_BASE_PATH + String.format("p_%s_%s", problemId, version);
        File dataFolderFile = new File(dataFolderPath);
        if (!dataFolderFile.exists()) {
            createProblemDataFile(dataFolderFile, checkPointList);
        } else if (checkProblemDataFile(dataFolderFile, checkPointList.size())) {
            refreshProblemDataFile(dataFolderFile, checkPointList);
        }
    }

    protected synchronized void refreshProblemDataFile(File dataFolderFile, List<PmsProblemCheckPoint> checkPointList) {
        if (!dataFolderFile.delete()) {
            throw new BaseException(ResultCodeEnum.JUDGER_ERROR, "问题数据文件夹删除失败");
        }
        createProblemDataFile(dataFolderFile, checkPointList);
    }

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

    protected boolean checkProblemDataFile(File dataFolderFile, int size) {
        for (int i = 0; i < size; i++) {
            File file = new File(dataFolderFile, String.format("input_%d", i + 1));
            if (!file.exists()) {
                return false;
            }
        }
        return true;
    }

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
}
