package indi.snowmeow.zkoj.base.controller;

import indi.snowmeow.zkoj.base.common.base.BaseResult;
import indi.snowmeow.zkoj.base.model.vo.LanguageVO;
import indi.snowmeow.zkoj.base.service.PmsLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2021/4/23
 */
@RestController
public class LanguageController {

    @Autowired
    PmsLanguageService pmsLanguageService;

    @GetMapping("/language")
    public BaseResult<Map<String, Object>> getList() {
        Map<String, Object> result = new HashMap<>();
        List<LanguageVO> languages = pmsLanguageService.list();
        result.put("language_list", languages);
        return BaseResult.success(result);
    }
}
