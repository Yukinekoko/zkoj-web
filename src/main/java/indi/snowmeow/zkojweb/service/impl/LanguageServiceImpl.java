package indi.snowmeow.zkojweb.service.impl;

import indi.snowmeow.zkojweb.mapper.LanguageMapper;
import indi.snowmeow.zkojweb.po.LanguagePO;
import indi.snowmeow.zkojweb.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageMapper languageMapper;

    @Override
    public List<LanguagePO> list() {
        return languageMapper.list();
    }
}
