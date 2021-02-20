package indi.snowmeow.zkojweb.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import indi.snowmeow.zkojweb.exception.ApiException;
import indi.snowmeow.zkojweb.exception.ParamErrorException;
import indi.snowmeow.zkojweb.mapper.CheckPointMapper;
import indi.snowmeow.zkojweb.mapper.ProblemMapper;
import indi.snowmeow.zkojweb.model.vo.ProblemCheckPointPreviewVO;
import indi.snowmeow.zkojweb.po.ProblemCheckPointPO;
import indi.snowmeow.zkojweb.service.ProblemCheckPointService;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.util.ListCopyUtil;
import indi.snowmeow.zkojweb.util.ZipFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2021/2/21
 */
@Service
public class ProblemCheckPointServiceImpl implements ProblemCheckPointService {

    @Autowired
    CheckPointMapper checkPointMapper;
    @Autowired
    ProblemMapper problemMapper;

    @Override
    public List<ProblemCheckPointPreviewVO> getFromProblemId(Long problemId) {
        List<ProblemCheckPointPO> checkPointPOs = checkPointMapper.getFromProblemId(problemId);
        List<ProblemCheckPointPreviewVO> result = ListCopyUtil.copy(checkPointPOs, ProblemCheckPointPreviewVO.class);
        return result;
    }

    @Override
    @Transactional
    public void update(Long problemId, MultipartFile file, String checkPointString) {
        // TODO 重构待定
        if (null == problemMapper.getProblemById(problemId)) {
            throw new ApiException("题目不存在");
        }
        checkPointMapper.deleteCheckPoint(problemId);
        //zip 压缩包格式上传
        if (file != null) {
            if (file.isEmpty()){
                throw new ApiException("压缩包错误");
            }
            //再执行插入
            File f = null;
            try {
                f=File.createTempFile("tmp", null);
                file.transferTo(f);
            }
            catch (IOException e) {
                f.delete();
                throw new ParamErrorException("Check Point File Error.");
            }
            ZipFileUtil zipFile = new ZipFileUtil(f);
            if (!zipFile.isZip()){
                f.delete();
                throw new ParamErrorException("Check Point File Is Not A ZIP.");
            }
            if (zipFile.isEncrypted()){
                f.delete();
                throw new ParamErrorException("Check Point File Error.");
            }
            if (zipFile.isLegal()){
                Map<String, String> files= zipFile.readFiles();
                ArrayList<String> inputNames = zipFile.getInputNames();
                ArrayList<String> outputNames = zipFile.getOutputNames();
                String input = "";
                String output = "";
                for (int j = 0; j < inputNames.size(); j++) {
                    String inputName = inputNames.get(j);
                    //检验输入输出的文件数字是否匹配 input_1 对 output_1
                    String inputNumber = inputName.substring(6,inputName.length() );
                    for (int i = 0; i < outputNames.size(); i++){
                        String name = outputNames.get(i);
                        String number = name.substring(7,name.length() );
                        if (number.equals(inputNumber)){
                            input = files.get(inputName);
                            System.out.println(inputName+"："+input);
                            output = files.get(name);
                            System.out.println(name +"："+output);
                            int PUTisSuccess = checkPointMapper.insertCheckPoint(problemId,input,output);
                            if (PUTisSuccess<1){
                                f.delete();
                                throw new ParamErrorException("Unknown Error.");
                            }
                            outputNames.remove(i);
                            break;
                        }
                    }
                }
                for (int i =0;i<outputNames.size(); i++){
                    //Sql 插入数据库
                    output = files.get(outputNames.get(i));
                    int PUTisSuccess = checkPointMapper.insertCheckPoint(problemId,input,output);
                    if (PUTisSuccess<1){
                        f.delete();
                        throw new ParamErrorException("Unknown Error.");
                    }
                    System.out.println(outputNames.get(i));
                }

            }
            f.delete();
        }
        //对json格式的更新
        if (checkPointString !=null) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> sampleList = null;
            try {
                sampleList = objectMapper.readValue(checkPointString, new TypeReference<List<Map<String, Object>>>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            int i = 1;
            for (Map<String, Object> samples : sampleList) {
                String input = (String) samples.get("input");
                String output = (String) samples.get("output");
                System.out.println("input : " + input + "\n" + "output : " + output);
                //input 可以为空 output不可以为空
                if (output == null) {
                    throw new ParamErrorException("Output" + i + "Is Null");
                }
                i++;
                checkPointMapper.insertCheckPoint(problemId, input, output);
            }
        }
    }
}
