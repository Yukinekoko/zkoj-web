package indi.snowmeow.zkoj.base.service.impl;

import indi.snowmeow.zkoj.base.common.util.BeanUtil;
import indi.snowmeow.zkoj.base.dao.PmsLanguageMapper;
import indi.snowmeow.zkoj.base.model.entity.PmsLanguage;
import indi.snowmeow.zkoj.base.model.vo.LanguageVO;
import indi.snowmeow.zkoj.base.service.PmsLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/23
 */
@Service
public class PmsLanguageServiceImpl implements PmsLanguageService {

    @Autowired
    PmsLanguageMapper pmsLanguageMapper;

    @Override
    public List<LanguageVO> list() {
        List<PmsLanguage> languageEntities = pmsLanguageMapper.selectList(null);
        for (int i = 0; i < languageEntities.size(); i++) {
            if (languageEntities.get(i).getId() == 0) {
                languageEntities.remove(i);
                break;
            }
        }
        List<LanguageVO> result = BeanUtil.copy(languageEntities, LanguageVO.class);
        return result;
    }

    @Override
    public PmsLanguage find(long id) {
        return pmsLanguageMapper.selectById(id);
    }
}
