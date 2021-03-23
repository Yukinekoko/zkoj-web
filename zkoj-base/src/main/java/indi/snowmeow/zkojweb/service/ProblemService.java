package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.dto.*;
import indi.snowmeow.zkojweb.model.Problem;
import indi.snowmeow.zkojweb.model.ProblemClass;
import indi.snowmeow.zkojweb.model.ProblemTag;
import indi.snowmeow.zkojweb.model.vo.ProblemAdminPreviewVO;
import indi.snowmeow.zkojweb.model.vo.ProblemDetailVO;
import indi.snowmeow.zkojweb.model.vo.ProblemListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author snowmeow
 * @date 2020/10/21
 */
public interface ProblemService {

    /**
     * 获取问题列表
     * @param requestDTO - 请求参数DTO
     * */
    ProblemListVO list(ProblemListRequestDTO requestDTO);

    /**
     * 获取问题总数，不包括未公开的
     * @return 问题总数
     * */
    int countByPublic(ProblemCountDTO requestDTO);
    /**
     * 获取问题的详情
     * @param problemId - 问题ID
     * @return 问题详细对象
     */
    ProblemDetailVO getProblemDetail(long problemId);


}
