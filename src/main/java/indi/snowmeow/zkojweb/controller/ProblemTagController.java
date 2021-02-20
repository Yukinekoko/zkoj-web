package indi.snowmeow.zkojweb.controller;

import indi.snowmeow.zkojweb.model.ProblemTag;
import indi.snowmeow.zkojweb.service.impl.ProblemTagServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.vo.ProblemTagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
@RestController
public class ProblemTagController {

    @Autowired
    ProblemTagServiceImpl problemTagService;

    /**
     * 获取所有算法标签信息
     * @return List<ProblemTag></>
     */
    @GetMapping("/tag")
    public Object getTagList(){
        List<ProblemTagVO> tags = problemTagService.list();
        return BaseBody.success(tags);
    }
}
