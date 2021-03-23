package indi.snowmeow.zkojweb.controller;

import indi.snowmeow.zkojweb.model.po.ProblemClassPO;
import indi.snowmeow.zkojweb.service.impl.ProblemClassServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.model.vo.ProblemClassListVO;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    @GetMapping("/problem-class")
    public Object getClassesInfo(){
        List<ProblemClassListVO> problemClasses = problemClassService.list();
        return BaseBody.success(problemClasses);
    }

    /**
     * 删除分组
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @DeleteMapping("/problem-class/{id}")
    public Object deleteProblemClass(@PathVariable Long id){
        int result = problemClassService.delete(id);
        if(result == 0) {
            return BaseBody.fail("Unknown Error");
        }
        return BaseBody.success();
    }

    /**
     * 管理员修改分组信息
     * @param requestBody -{id,name,description}
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PutMapping("/problem-class")
    public Object updateProblemClass(@RequestBody Map<String, Object> requestBody){
        String name = (String) requestBody.get("name");
        String description = (String) requestBody.get("description");
        Long id = (Long) requestBody.get("id");
        if (name==null||description == null||id==null)
            return BaseBody.fail("PARAM ERROR");
        if (name.length()>40 ||description.length() > 255){
            return BaseBody.fail("PARAM TOO LONG");
        }
        ProblemClassPO classPO = new ProblemClassPO();
        classPO.setId(id);
        classPO.setName(name);
        classPO.setDescription(description);
        problemClassService.update(classPO);
        return BaseBody.success();
    }

    /**
     * 管理员增加 problem_class 分组
     * @param requestBody -{name,description}
     *
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PostMapping("/problem-class")
    public Object insertProblemClass(@RequestBody Map<String, Object> requestBody) {
        String name = (String) requestBody.get("name");
        String description = (String) requestBody.get("description");
        if (name == null || description == null)
            return BaseBody.fail("PARAM ERROR");
        if (name.length() > 40 || description.length() > 255) {
            return BaseBody.fail("PARAM TOO LONG");
        }
        ProblemClassPO problemClass = new ProblemClassPO();
        problemClass.setName(name);
        problemClass.setDescription(description);
        problemClassService.save(problemClass);

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("id", problemClass.getId());
        return BaseBody.success(resultData);
    }

}
