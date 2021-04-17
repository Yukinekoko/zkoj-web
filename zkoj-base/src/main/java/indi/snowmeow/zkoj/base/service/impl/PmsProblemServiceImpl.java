package indi.snowmeow.zkoj.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.snowmeow.zkoj.base.dao.PmsProblemMapper;
import indi.snowmeow.zkoj.base.model.dto.ProblemListRequestDTO;
import indi.snowmeow.zkoj.base.model.entity.PmsProblem;
import indi.snowmeow.zkoj.base.model.vo.ProblemPreviewVO;
import indi.snowmeow.zkoj.base.service.PmsProblemService;
import indi.snowmeow.zkoj.base.util.ListCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/15
 */
@Service
public class PmsProblemServiceImpl implements PmsProblemService {

    @Autowired
    PmsProblemMapper pmsProblemMapper;

    @Override
    public List<PmsProblem> list(ProblemListRequestDTO request) {
        Page<PmsProblem> page = new Page<>(request.getPage(), request.getLimit());
        return pmsProblemMapper.list(page, request).getRecords();
    }

    @Override
    public PmsProblem getFromId(long id) {
        QueryWrapper<PmsProblem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_private", false);
        queryWrapper.eq("id", id);
        return pmsProblemMapper.selectOne(queryWrapper);
    }


}
