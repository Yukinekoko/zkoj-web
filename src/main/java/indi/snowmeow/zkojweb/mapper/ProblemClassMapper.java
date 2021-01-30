package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.ProblemClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @author snowmeow
 * @date 2020/10/21
 */
@Mapper
public interface ProblemClassMapper {

    /**
     * 获取指定题目的所属分类
     * @param problemId - 题目ID
     * @return (id, name, description) - 指定题目的所属分类
     * */
    public ProblemClass getClassFromProblemId(@Param("problem_id") long problemId);

    /**
     * 根据id获取指定分类对象
     * @param id - class_id
     * @return 指定分类对象
     * */
    public ProblemClass getClassFromId(@Param("id") long id);

    /**
     * 根据id获取指定分类对象
     * 该返回值对象仅包括分类的 id 与 name，忽略了description属性
     * @param id class_id
     * @return 指定分类对象（description为null）
     * */
    public ProblemClass getClassPreviewFromId(@Param("id") long id);
    /**
     *  获取所有分类信息
     * @return 分类对象 Map
     */
    public List<Map<String, Object>> getClassesInfo();
    /**
     *  新增分组信息
     * @return 数据影响行数
     */
    public int insertProblemClass(ProblemClass problemClass);

    /**
     *  查看是否已经存在同名同描述的标签
     * @param name
     * @return ProblemClass
     */
    public ProblemClass getProblemClassByName(String name);

    /**
     * 修改分组信息
     * @param problemClass
     */
    public int updateProblemClass(ProblemClass problemClass);

    public int deleteProblemClass(Long classId);
}
