package indi.snowmeow.zkojweb.controller;

import indi.snowmeow.zkojweb.model.po.LanguagePO;
import indi.snowmeow.zkojweb.service.impl.LanguageServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
@Validated
@RestController
public class LanguageController {

    @Autowired
    LanguageServiceImpl languageService;

    /**
     * 获取语言列表
     */
    @GetMapping("/language")
    public Object getLanguageList(){
        List<LanguagePO> languages = languageService.list();
        return BaseBody.success(languages);
    }
}
