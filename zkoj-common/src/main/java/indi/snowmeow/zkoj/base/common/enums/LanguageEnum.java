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

    private final long id;

    LanguageEnum(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
