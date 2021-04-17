package indi.snowmeow.zkoj.base.dao;

import indi.snowmeow.zkoj.base.model.dto.ProblemListRequestDTO;
import indi.snowmeow.zkoj.base.model.vo.ProblemPreviewVO;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/16
 */
public interface ProblemDomainMapper {

    List<ProblemPreviewVO> listPreview(ProblemListRequestDTO request);

    List<ProblemPreviewVO> listPreviewFromSearchTitle(ProblemListRequestDTO request);

}
