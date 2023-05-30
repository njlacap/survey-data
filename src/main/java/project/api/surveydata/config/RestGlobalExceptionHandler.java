package project.api.surveydata.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.api.surveydata.exception.SurveyException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestGlobalExceptionHandler {

    @ExceptionHandler(SurveyException.class)
    public ResponseEntity<?> surveyException(SurveyException ex)
    {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintException(ConstraintViolationException ex)
    {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
