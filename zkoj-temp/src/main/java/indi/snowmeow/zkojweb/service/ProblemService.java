package indi.snowmeow.zkojweb.service;

import indi.snowmeow.zkojweb.model.dto.*;
import indi.snowmeow.zkojweb.model.Problem;
import indi.snowmeow.zkojweb.model.ProblemClass;
import indi.snowmeow.zkojweb.model.ProblemTag;
import indi.snowmeow.zkojweb.model.vo.ProblemAdminPreviewVO;
import indi.snowmeow.zkojweb.model.vo.ProblemDetailVO;
import indi.snowmeow.zkojweb.model.vo.ProblemListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author snowmeow
 * @date 2020/10/21
 */
public interface ProblemService {

    /**
     * 获取问题列表
     * @param requestDTO - 请求参数DTO
     * */
    ProblemListVO list(ProblemListRequestDTO requestDTO);

    /**
     * 获取问题总数，不包括未公开的
     * @return 问题总数
     * */
    int countByPublic(ProblemCountDTO requestDTO);
    /**
     * 获取问题的详情
     * @param problemId - 问题ID
     * @return 问题详细对象
     */
    ProblemDetailVO getProblemDetail(long problemId);

    /**
     * 根据关键字查询题目
     * @param search -关键字
     * @param sortType - 排序类型
     * @param page - 页码
     * @param limit - 每页数量 默认20
     * @return List<Problem> 问题列表
     */
    List<Problem> getProblemBySearch(Integer page, Integer limit,
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

    public Object insertProblem(MultipartFile file,String problemDataString,String checkPointString);

    public boolean update(ProblemUpdateDTO requestDTO);

    public int countProblemByConditions(@Param("difficulty")Byte difficulty,
                                        @Param("class_id")Long classId,
                                        @Param("tag_id")Long tagId,
                                        @Param("upload_username")String uploadUsername,
                                        @Param("upload_user_id") Long uploadUserId);

    public int countProblemBySearch(@Param("search")String search);

    /**
     * 获取管理后台的问题列表
     * */
    List<ProblemAdminPreviewVO> getAdminPreviewList(ProblemAdminPreviewDTO requestDTO);

    /**
     * 获取问题总数，包括private的
     */
    int count(ProblemAdminPreviewDTO requestDTO);

}
