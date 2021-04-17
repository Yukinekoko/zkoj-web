package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.dto.ProblemListRequestDTO;
import indi.snowmeow.zkoj.base.model.vo.ProblemDetailVO;
import indi.snowmeow.zkoj.base.model.vo.ProblemPreviewVO;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
public interface ProblemDomainService {

    /**
     * 获取公开问题预览列表
     * */
    List<ProblemPreviewVO> getPreviewList(ProblemListRequestDTO request);

    ProblemDetailVO getDetail(long problemId);

}
