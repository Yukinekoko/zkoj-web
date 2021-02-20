package indi.snowmeow.zkojweb.service.impl;

import indi.snowmeow.zkojweb.exception.ApiException;
import indi.snowmeow.zkojweb.mapper.ProblemTagMapper;
import indi.snowmeow.zkojweb.po.ProblemTagPO;
import indi.snowmeow.zkojweb.service.ProblemTagService;
import indi.snowmeow.zkojweb.util.ListCopyUtil;
import indi.snowmeow.zkojweb.model.vo.ProblemTagVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
@Service
public class ProblemTagServiceImpl implements ProblemTagService {

    @Autowired
    ProblemTagMapper problemTagMapper;

    @Override
    public ProblemTagVO getFromId(Long id) {
        ProblemTagVO result = new ProblemTagVO();
        ProblemTagPO tagPO = problemTagMapper.getFromId(id);
        BeanUtils.copyProperties(tagPO, result);
        return result;
    }

    @Override
    public ProblemTagVO getFromName(String name) {
        ProblemTagVO result = new ProblemTagVO();
        BeanUtils.copyProperties(problemTagMapper.getFromName(name), result);
        return result;
    }

    @Override
    public long save(String name) {
        ProblemTagPO tag = problemTagMapper.getFromName(name);
        if (tag != null) {
           throw new ApiException("TAG ALREADY EXISTS");
        }

        tag = new ProblemTagPO();
        tag.setName(name);
        int result = problemTagMapper.save(tag);
        if (result == 0) {
            throw new ApiException("Unknown Error");
        }
        return tag.getId();
    }

    @Override
    public List<ProblemTagVO> list() {
        List<ProblemTagPO> tagPOs = problemTagMapper.list();
        return ListCopyUtil.copy(tagPOs, ProblemTagVO.class);
    }

    @Override
    public int delete(Long id) {
        return problemTagMapper.delete(id);
    }

    @Override
    public void update(Long id, String name) {
        ProblemTagPO tag = new ProblemTagPO();
        tag.setId(id);
        tag.setName(name);
        int result =  problemTagMapper.update(tag);
        if (result == 0) {
            throw new ApiException("插入失败");
        }
    }


}
