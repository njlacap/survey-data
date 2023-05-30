package project.api.surveydata.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.api.surveydata.SurveyDataApplication;
import project.api.surveydata.dto.SearchCriteria;
import project.api.surveydata.dto.SurveyDataDtoCustom;
import project.api.surveydata.dto.SurveyDataDtoCustomS;
import project.api.surveydata.helper.SurveyDataSpecification;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = SurveyDataApplication.class)
public class SurveyDataServiceTest {
    public static final String YOUNG_AGE_BRACKET = "18-24";
    public static final String YOUNG_AGE_18 = "18";
    public static final String YOUNG_AGE_35 = "35";
    public static final String INDUSTRY_GOVERNMENT = "Government";
    public static final String JOB_TITLE_FINANCE_DIRECTOR = "Finance Director";
    public static final String CURRENCY_USD = "USD";
    public static final String LOCATION_CHICAGO_USA = "Chicago suburbs, IL, USA";
    public static final String AGE_KEY = "age";
    public static final String INDUSTRY_KEY = "industry";
    public static final String JOB_TITLE_KEY = "jobTitle";
    public static final double ANNUAL_SALARY_VALUE_1 = 44459.0D;
    public static final double ANNUAL_SALARY_VALUE_2 = 150000D;
    public static final int COUNT_VALUE = 44;
    ModelMapper modelMapper;

    @Spy
    @Autowired
    SurveyDataService surveyDataService;

    @Test
    public void serviceInvocationWithNoParametersDefaultSorting() {

        List<SurveyDataDtoCustom> surveyDataDtoCustoms = (List<SurveyDataDtoCustom>) surveyDataService.searchFilterAndSort(new SurveyDataSpecification(),"age","");

        Assertions.assertEquals(surveyDataDtoCustoms.get(0).getAge(), YOUNG_AGE_BRACKET);
    }

    @Test
    public void serviceInvocationWithParametersDefaultSorting() {

        SurveyDataSpecification surveyDataSpecification = new SurveyDataSpecification();
        surveyDataSpecification.add(new SearchCriteria(AGE_KEY,YOUNG_AGE_BRACKET));
        surveyDataSpecification.add(new SearchCriteria(INDUSTRY_KEY,INDUSTRY_GOVERNMENT));

        List<SurveyDataDtoCustom> surveyDataDtoCustoms = (List<SurveyDataDtoCustom>) surveyDataService.searchFilterAndSort(surveyDataSpecification,"age","");

        Assertions.assertEquals(surveyDataDtoCustoms.get(0).getAge(),YOUNG_AGE_BRACKET);
        Assertions.assertEquals(surveyDataDtoCustoms.get(0).getIndustry(), INDUSTRY_GOVERNMENT);
    }

    @Test
    public void serviceInvocationWithParametersCustomSorting() {

        SurveyDataSpecification surveyDataSpecification = new SurveyDataSpecification();
        surveyDataSpecification.add(new SearchCriteria(JOB_TITLE_KEY,JOB_TITLE_FINANCE_DIRECTOR));

        List<SurveyDataDtoCustom> surveyDataDtoCustoms = (List<SurveyDataDtoCustom>) surveyDataService.searchFilterAndSort(surveyDataSpecification,"annualSalary","");

        Assertions.assertEquals(surveyDataDtoCustoms.get(0).getAnnualSalary(), ANNUAL_SALARY_VALUE_1);
        Assertions.assertEquals(surveyDataDtoCustoms.get(0).getJobTitle(), JOB_TITLE_FINANCE_DIRECTOR);
    }

    @Test
    public void serviceInvocationWithParametersCustomSortingIncludingCustomFields() {

        SurveyDataSpecification surveyDataSpecification = new SurveyDataSpecification();
        surveyDataSpecification.add(new SearchCriteria(JOB_TITLE_KEY,JOB_TITLE_FINANCE_DIRECTOR));
        surveyDataSpecification.add(new SearchCriteria(AGE_KEY,YOUNG_AGE_35));

        List<SurveyDataDtoCustomS> surveyDataDtoCustoms = (List<SurveyDataDtoCustomS>) surveyDataService.searchFilterAndSort(surveyDataSpecification,"age","annualSalary,currency,location");

        Assertions.assertEquals(surveyDataDtoCustoms.get(0).getAnnualSalary(), ANNUAL_SALARY_VALUE_2);
        Assertions.assertEquals(surveyDataDtoCustoms.get(0).getCurrency(), CURRENCY_USD);
        Assertions.assertEquals(surveyDataDtoCustoms.get(0).getLocation(), LOCATION_CHICAGO_USA);
    }

    @Test
    public void serviceInvocationWithParametersCount() {

        SurveyDataSpecification surveyDataSpecification = new SurveyDataSpecification();
        surveyDataSpecification.add(new SearchCriteria(INDUSTRY_KEY, INDUSTRY_GOVERNMENT));
        surveyDataSpecification.add(new SearchCriteria(AGE_KEY, YOUNG_AGE_18));

        Long surveyDataResut = surveyDataService.searchFilterAndCount(surveyDataSpecification);

        Assertions.assertEquals(surveyDataResut, COUNT_VALUE);
    }
}
