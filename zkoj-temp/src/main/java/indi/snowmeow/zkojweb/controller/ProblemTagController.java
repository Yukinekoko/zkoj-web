package indi.snowmeow.zkojweb.controller;

import com.alibaba.druid.util.StringUtils;
import indi.snowmeow.zkojweb.exception.ApiException;
import indi.snowmeow.zkojweb.model.po.ProblemTagPO;
import indi.snowmeow.zkojweb.service.impl.ProblemTagServiceImpl;
import indi.snowmeow.zkojweb.util.BaseBody;
import indi.snowmeow.zkojweb.model.vo.ProblemTagVO;
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

    /**
     * 删除算法标签
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @DeleteMapping("/tag/{id}")
    public Object deleteProblemTag(@PathVariable Long id){
        int result = problemTagService.delete(id);
        if(result == 0) {
            return BaseBody.fail("Unknown Error");
        }
        return BaseBody.success();

    }

    /**
     * 管理员增加算法标签
     * @param requestBody -{name}
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PostMapping("/tag")
    public Object insertTag(@RequestBody Map<String, Object> requestBody){
        String name = (String) requestBody.get("name");
        if (name == null) {
            throw new ApiException("Param Error");
        }
        if (name.length() > 40) {
            throw new ApiException("Param Too Long");
        }

        long resultId = problemTagService.save(name);
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("id", resultId);
        return BaseBody.success(resultData);
    }

    /**
     * 管理员修改算法标签
     * @param requestBody -{id,name}
     */
    @RequiresRoles(value = {"SUPER_ADMIN","ADMIN"}, logical = Logical.OR)
    @PutMapping("/tag")
    public Object updateProblemTag(@RequestBody Map<String, Object> requestBody){
        String name = (String) requestBody.get("name");
        Long id = (Long) requestBody.get("id");
        if (StringUtils.isEmpty(name) || id == null)
            return BaseBody.fail("PARAM ERROR");
        if (name.length()>40)
            return BaseBody.fail("PARAM TOO LONG");

        problemTagService.update(id, name);
        return BaseBody.success();
    }
}
