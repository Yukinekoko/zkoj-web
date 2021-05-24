package indi.snowmeow.zkoj.base.common.util;

import indi.snowmeow.zkoj.base.common.enums.LanguageEnum;

/**
 * @author snowmeow
 * @date 2021/5/24
 */
public class LanguageUtil {

    public static LanguageEnum getLanguageEnum(long languageId) {
        if (languageId == 1) {
            return LanguageEnum.C;
        } else if (languageId == 2) {
            return LanguageEnum.CPP;
        } else if (languageId == 3) {
            return LanguageEnum.JAVA;
        } else if (languageId == 4) {
            return LanguageEnum.PYTHON;
        }
        return null;
    }
}
