package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.dto.ProblemListRequestDTO;
import indi.snowmeow.zkoj.base.model.entity.PmsProblem;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
public interface PmsProblemService {

    List<PmsProblem> list(ProblemListRequestDTO request);

    PmsProblem getFromId(long id);
}
