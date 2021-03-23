package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.po.LanguagePO;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
public interface LanguageService {

    /**
     * 获取语言列表
     */
    List<LanguagePO> list();

}
