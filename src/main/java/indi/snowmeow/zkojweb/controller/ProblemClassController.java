package indi.snowmeow.zkojweb.controller;

import indi.snowmeow.zkojweb.service.impl.ProblemClassServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.vo.ProblemClassListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2021/2/20
 */
@RestController
public class ProblemClassController {

    @Autowired
    ProblemClassServiceImpl problemClassService;

    /**
     *  获取所有分类信息
     * @return 分类信息 Map
     */
    @GetMapping("/class")
    public Object getClassesInfo(){
        List<ProblemClassListVO> problemClasses = problemClassService.list();
        return BaseBody.success(problemClasses);
    }
}
