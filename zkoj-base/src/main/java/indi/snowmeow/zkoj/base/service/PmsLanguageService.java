package indi.snowmeow.zkoj.base.service;

import indi.snowmeow.zkoj.base.model.entity.PmsLanguage;
import indi.snowmeow.zkoj.base.model.vo.LanguageVO;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/4/23
 */
public interface PmsLanguageService {

    /**
     * 返回所有语言列表；不包括ID为0代表缺省值的OTHER
     * */
    List<LanguageVO> list();

    /**
     * 获取语言对象
     * */
    PmsLanguage find(long id);
}
