package shop.mtcoding.sporting_server.core.enums.field.etc;

public enum PlayerInfoAge {
    /*
     * 값 사용법 : AgeRange.parse("20대").getValue();
     * AgeRange ageRange = AgeRange.parse("20대");
     * String value = ageRange.getValue(); // "20대"
     */

    AGE_10_UNDER("10대 이하"),
    AGE_20("20대"),
    AGE_30("30대"),
    AGE_40("40대"),
    AGE_50("50대"),
    AGE_60("60대"),
    AGE_70_OVER("70대 이상");

    private final String value;

    PlayerInfoAge(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PlayerInfoAge parse(String value) {
        switch (value) {
            case "10대 이하":
                return AGE_10_UNDER;
            case "20대":
                return AGE_20;
            case "30대":
                return AGE_30;
            case "40대":
                return AGE_40;
            case "50대":
                return AGE_50;
            case "60대":
                return AGE_60;
            case "70대 이상":
                return AGE_70_OVER;
            default:
                throw new IllegalArgumentException("Invalid value: " + value);
        }
    }
}
