package indi.snowmeow.zkoj.base.common.enums;

/**
 * @author snowmeow
 * @date 2021/5/17
 */
public enum SolutionStatusEnum {

    AC(1),
    WA(2),
    R(3),
    C(4),
    W(5),
    CE(6),
    RE(7),
    TLE(8),
    MLE(9),
    PE(10);

    private long id;

    SolutionStatusEnum(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
