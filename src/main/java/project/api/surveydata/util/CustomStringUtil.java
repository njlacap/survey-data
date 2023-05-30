package project.api.surveydata.util;

public final class CustomStringUtil {

    private static final String REGEX_DOUBLE_FORMAT = "[^\\d]";
    public static final String SUFFIX = ".00";

    private CustomStringUtil() { }

    public static String removeMultiplePoint(String value) {
        return value.replaceAll(REGEX_DOUBLE_FORMAT,"") + SUFFIX;
    }

}
