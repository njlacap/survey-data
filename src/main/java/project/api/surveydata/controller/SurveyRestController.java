package project.api.surveydata.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.api.surveydata.dto.BaseSurveyDataDto;
import project.api.surveydata.dto.SearchCriteria;
import project.api.surveydata.dto.SurveyDataDtoCustom;
import project.api.surveydata.helper.CustomResponseSerializer;
import project.api.surveydata.helper.SurveyDataSpecification;
import project.api.surveydata.helper.ValuesAllowed;
import project.api.surveydata.service.SurveyDataService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author njlacap
 */
@RestController
@RequestMapping("survey")
@Validated
public class SurveyRestController {

    private static final Logger log = LoggerFactory.getLogger(SurveyRestController.class);

    private SurveyDataService surveyDataService;

    public SurveyRestController() { }
    @Autowired
    public SurveyRestController(SurveyDataService surveyDataService) {
        this.surveyDataService = surveyDataService;
    }

    @GetMapping()
    public ResponseEntity<?> get(
            @RequestParam(required = false, value = "age")
            String age,
            @RequestParam(required = false, value = "industry")
            String industry,
            @RequestParam(required = false, value = "jobTitle")
            String jobTitle,
            @RequestParam(required = false, value = "salaryGTE")
            Double salaryGte,
            @RequestParam(required = false, value = "salaryLTE")
            Double salaryLte,
            @RequestParam(required = false, value = "currency")
            String currency,
            @RequestParam(required = false, value = "location")
            String location,
            @RequestParam(required = false, value = "work_exp")
            String workExp,
            @RequestParam(required = false, value = "sort", defaultValue = "age")
            @ValuesAllowed(values = {"age","industry","jobTitle","annualSalary","currency","location"})
            String sort,
            @RequestParam(required = false, value = "count", defaultValue = "false")
            Boolean count,
            @RequestParam(required = false, value = "fields", defaultValue = "")
            @ValuesAllowed(values = {"Separated by comma: {age|industry|jobTitle|annualSalary|currency|location}"})
            String fields
    ) throws IOException {
        log.info("REST API GET - Invoked");
        SurveyDataSpecification surveyDataSpecification = new SurveyDataSpecification();
        log.info("REST API GET - Generating specifications based on criteria");
        generateSpecificationsWithCriteria(age, industry, jobTitle, salaryGte, salaryLte, currency, location, workExp, surveyDataSpecification);
        log.info("REST API GET - Responding...");

        List<? extends BaseSurveyDataDto> dataList = surveyDataService.searchFilterAndSort(surveyDataSpecification, sort);

        return count ? new ResponseEntity<>(surveyDataService.searchFilterAndCount(surveyDataSpecification),HttpStatus.OK) :
                new ResponseEntity<>(CustomResponseSerializer.serialize(fields, (List<SurveyDataDtoCustom>) dataList), HttpStatus.OK);
    }

    /**
     * Non-dynamic adding of specification.
     *
     * @implNote Can use stream for specs creation if params are in a form of collection
     */
    private static void generateSpecificationsWithCriteria(String age
                                                            , String industry
                                                            , String jobTitle
                                                            , Double salaryGte
                                                            , Double salaryLte
                                                            , String currency
                                                            , String location
                                                            , String workExp
                                                            , SurveyDataSpecification surveyDataSpecification)
    {
        surveyDataSpecification.add(new SearchCriteria("age", age));
        surveyDataSpecification.add(new SearchCriteria("industry", industry));
        surveyDataSpecification.add(new SearchCriteria("jobTitle", jobTitle));
        surveyDataSpecification.add(new SearchCriteria("salaryGTE", salaryGte));
        surveyDataSpecification.add(new SearchCriteria("salaryLTE", salaryLte));
        surveyDataSpecification.add(new SearchCriteria("currency", currency));
        surveyDataSpecification.add(new SearchCriteria("location", location));
        surveyDataSpecification.add(new SearchCriteria("workExperience", workExp));
    }
}
