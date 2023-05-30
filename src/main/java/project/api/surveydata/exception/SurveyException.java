package project.api.surveydata.exception;

public class SurveyException extends RuntimeException {
    public SurveyException(String errorMessage)
    {
        super(errorMessage);
    }
}
