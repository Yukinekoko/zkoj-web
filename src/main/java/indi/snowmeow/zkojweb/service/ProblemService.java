package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.Language;
import indi.snowmeow.zkojweb.model.Problem;
import indi.snowmeow.zkojweb.model.ProblemClass;
import indi.snowmeow.zkojweb.model.ProblemTag;
import indi.snowmeow.zkojweb.vo.ProblemDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author snowmeow
 * @date 2020/10/21
 */
public interface ProblemService {

    /**
     * 获取问题列表
     * @param page - 页码；从1开始
     * @param limit - 每页数量
     * @return 问题列表
     * @param userId - 用户ID；可为null
     * @param difficulty - 筛选难度；可为null
     * @param classId - 筛选分类；可为null
     * @param tagId  - 筛选算法标签；可为null
     * @param searchText - 搜索关键字；可为null
     * */
    public List<Problem> getList(int page, int limit, Long userId, Byte difficulty,
                                        Long classId, Long tagId, String searchText);

    /**
     * 获取问题总数
     * @param difficulty - 筛选难度
     * @param tagId - 筛选算法标签ID
     * @param classId - 筛选分类ID
     * @param searchText - 搜索关键字
     * @return 问题总数
     * */
    public int getCount(Byte difficulty, Long tagId, Long classId,
                        String searchText);
    /**
     * 获取问题的详情
     * @param problemId - 问题ID
     * @return 问题详细对象
     */
    public ProblemDetailVO getProblemDetail(long problemId);
    /**
     *  获取所有算法标签信息
     * @return 算法标签对象
     */
    public List<ProblemTag> getTagList();
    /**
     * 获取所有分类信息
     * @return 分类信息 map
     */
    public List<Map<String,Object>> getClassesInfo();
    /**
     * 根据关键字查询题目
     * @param search -关键字
     * @param sortType - 排序类型
     * @param page - 页码
     * @param limit - 每页数量 默认20
     * @return List<Problem> 问题列表
     */
    public List<Problem> getProblemBySearch(Integer page, Integer limit,
                                            String search , String sortType
                                            );
    /**
     *  根据条件查询题目
     * @param difficulty - 难度
     * @param classId - 分类id
     * @param tagId -标签id
     * @param uploadUsername - 上传人用户名
     * @param uploadUserId - 上传人uid
     * @param sortType - 排序类型
     * @param page - 偏移量
     * @param limit - 每页数量 默认20
     * @return List<Problem> 问题列表
     */
    public List<Problem> getProblemByConditions(Byte difficulty, Long classId,
                                                Long tagId, String uploadUsername, Long uploadUserId,
                                                String sortType,Integer page,Integer limit);
    /**
     *  新增算法标签
     * @param problemTag - 算法标签命名
     * @return 数据影响行数
     */
    public int insertTag (ProblemTag problemTag);
    /**
     *  新增问题分组
     * @param problemClass - 问题分组对象
     * @return 数据影响行数
     */
    public int insertProblemClass (ProblemClass problemClass);
    /**
     * 获取算法标签
     * @param name - 算法标签名字
     * @retrun ProblemTag
     */
    public ProblemTag getTagByName(String name);
    /**
     *  查看是否已经存在同名同描述的标签
     * @param name
     * @return ProblemClass
     */
    public ProblemClass getProblemClassByName(String name );
    /**
     * 修改分组信息
     * @param problemClass
     */
    public int updateProblemClass(ProblemClass problemClass);

    /**
     *
     * 修改算法标签信息
     * @param problemTag
     */
    public int updateProblemTag(ProblemTag problemTag);
    /**
     * 增加问题
     * @param problem
     * @return 数据影响行数
     *
     */
    public  int insertProblem(Problem problem);
    /**
     * 根据id获取指定分类对象
     * @param id - class_id
     * @return 指定分类对象
     * */
    public ProblemClass getClassFromId(@Param("id") long id);
    /**
     * 根据id 获取指定标签对象
     * @param id - tag_id
     * @return 指定标签对象
     */
    public ProblemTag getTagById(Long id);
    /**
     * 获取语言列表
     * @return List<Language></
     */
    public List<Language> getLanguageList();

    public Object insertProblem(MultipartFile file,String problemDataString,String checkPointString);

    public int insertProblemTag (Long problemId , Long tagId);

    public int insertProblemLimit(Long problemId, Long languageId , Integer memory , Integer time);

    public int insertSourceCode(Long problemId, String sourceCode , Long languageId);

    public int insertCheckPoint(Long problemId, String input , String output);

    public Problem getProblemById(Long problemId);

    public int updateProblemTagList(Long[] tagId , Long problemId);

    public  int updateProblemInfo(Map<String, Object> problemMap);

    public  int deleteProblemTag(Long tagId);

    public int deleteProblemClass(Long classId);

    public Object updateProblemCheckPoint(Long problemId, MultipartFile file,String checkPointString);

    public int countProblemByConditions(@Param("difficulty")Byte difficulty,
                                        @Param("class_id")Long classId,
                                        @Param("tag_id")Long tagId,
                                        @Param("upload_username")String uploadUsername,
                                        @Param("upload_user_id") Long uploadUserId);

    public int countProblemBySearch(@Param("search")String search);

    public List<Map<String, Object>> getProblemCheckPoint(Long problemId);
}
