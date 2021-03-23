package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.vo.ProblemCheckPointPreviewVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/21
 */
public interface ProblemCheckPointService {

    /**
     * 根据problemId获取测试点集合
     * */
    List<ProblemCheckPointPreviewVO> getFromProblemId(Long problemId);

    void update(Long problemId, MultipartFile file, String checkPointString);

}
