package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/** 问题相关Mapper
 * @author snowmeow
 * @date 2020/10/19
 */
@Mapper
public interface ProblemMapper {

    /**
     * 获取问题总数量
     * @param tagId - 筛选标签id;可Null
     * @param classId - 筛选分类id;可Null
     * @param difficulty - 筛选难度;可Null
     * @param searchText - 搜索关键字
     * @param isSearchId - 是否为搜索ID；
     * @return 问题总数量
     * */
    public int getProblemCount(@Param("difficulty") Byte difficulty, @Param("tag_id") Long tagId,
                               @Param("class_id") Long classId, @Param("search_text") String searchText,
                               @Param("is_search_id") Boolean isSearchId);

    /**
     * 获取问题详情
     */
    public Problem getProblemDetail(@Param("problem_id")long problemId);
    /**
     * 获取问题列表
     * @param limit - 返回数量
     * @param offset - 偏移值
     * @param userId - 用户ID；可为null
     * @param difficulty - 筛选难度；可为null
     * @param classId - 筛选分类；可为null
     * @param tagId - 标签ID；可为null
     * @return 问题列表
     * */
    public List<Problem> getList(@Param("offset") Integer offset, @Param("limit") Integer limit,
                                        @Param("user_id") Long userId, @Param("difficulty") Byte difficulty,
                                        @Param("class_id")  Long classId, @Param("tag_id") Long tagId);
    /**
     * 根据关键字获取问题列表
     * @param limit - 每页数量
     * @param offset - 偏移值
     * @param userId - 用户ID；可为null
     * @param searchText - 搜索关键字
     * @param isSearchId - 当为true时指定对ID进行搜索；false时对标题进行搜索；
     * @return 问题列表
     * */
    public List<Problem> searchPreviewList(@Param("offset") Integer offset, @Param("limit") Integer limit,
                                           @Param("user_id") Long userId, @Param("search_text") String searchText,
                                           @Param("is_search_id") Boolean isSearchId);

    /**
     * 获取用户对指定题目的评测状态
     * 状态只有{通过，尝试过，为尝试过}三种
     * @param userId 用户ID
     * @param problemId 问题ID
     * @return 用户对指定题目的评测状态
     * */
    Integer getStatus(@Param("problem_id") Long problemId, @Param("user_id") Long userId);

    /**
     * 根据关键字查询题目
     * @param search -关键字
     * @param isNumber - 是否为数字
     * @param offset - 偏移量
     * @param limit - 每页数量 默认20
     * @return List<Problem> 问题列表
     */
    public List<Problem> getProblemBySearch(@Param("offset")Integer offset,@Param("limit") Integer limit,
                                            @Param("search")String search , @Param("is_number")Boolean isNumber);

    /**
     *  根据条件查询题目
     * @param difficulty - 难度
     * @param classId - 分类id
     * @param tagId -标签id
     * @param uploadUsername - 上传人用户名
     * @param uploadUserId - 上传人uid
     * @param offset - 偏移量
     * @param limit - 每页数量 默认20
     * @return List<Problem> 问题列表
     */
    public List<Problem> getProblemByConditions(@Param("difficulty")Byte difficulty,
                                                @Param("class_id")Long classId,
                                                @Param("tag_id")Long tagId,
                                                @Param("upload_username")String uploadUsername,
                                                @Param("upload_user_id") Long uploadUserId,
                                                @Param("offset")Integer offset,@Param("limit") Integer limit);

    /**
     * 增加问题
     * @param problem
     * @return 数据影响行数
     *
     */
    public  int insertProblem(Problem problem);

    public int insertSourceCode(Long problemId,String sourceCode , Long languageId);

    public Problem getProblemById(Long problemId);

    public  int updateProblemInfo(Map<String, Object> problemMap);

    public int countProblemByConditions(@Param("difficulty")Byte difficulty,
                                        @Param("class_id")Long classId,
                                        @Param("tag_id")Long tagId,
                                        @Param("upload_username")String uploadUsername,
                                        @Param("upload_user_id") Long uploadUserId);

    public int countProblemBySearch(@Param("search")String search , @Param("is_number")Boolean isNumber);
}
