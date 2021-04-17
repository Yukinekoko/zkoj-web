package indi.snowmeow.zkoj.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.snowmeow.zkoj.base.model.dto.ProblemListRequestDTO;
import indi.snowmeow.zkoj.base.model.entity.PmsProblem;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
public interface PmsProblemMapper extends BaseMapper<PmsProblem> {

    IPage<PmsProblem> list(Page<PmsProblem> page, ProblemListRequestDTO request);
}
