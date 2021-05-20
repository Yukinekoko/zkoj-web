package indi.snowmeow.zkoj.base.common.enums;

/**
 * @author snowmeow
 * @date 2021/5/17
 */
public enum LanguageEnum {

    OTHER(0),
    C(1),
    CPP(2),
    JAVA(3),
    PYTHON(4);

    private int id;

    LanguageEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
