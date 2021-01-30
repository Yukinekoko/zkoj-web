package indi.snowmeow.zkojweb.mapper;

import indi.snowmeow.zkojweb.model.Language;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * languageMapper
 * @author snowmeow
 * @date 2020/11/11
 */
@Mapper
public interface LanguageMapper {

    /**
     * 根据id获取language对象
     * @param id 语言id
     * @return language对象
     * */
    Language findById(@Param("id") Long id);
    /**
     * 获取语言列表
     * @return List<Language></
     */
    public List<Language> getLanguageList();
}
