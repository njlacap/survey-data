package project.api.surveydata.util;

public final class CustomParamChecker {

    private CustomParamChecker() { }

    public static <T> boolean isNull(T t) {
        return t == null;
    }
}
