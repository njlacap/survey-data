package project.api.surveydata.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import project.api.surveydata.dto.*;
import project.api.surveydata.helper.SurveyDataSpecification;
import project.api.surveydata.repository.*;

import java.util.ArrayList;
import java.util.List;

import static project.api.surveydata.util.CustomParamChecker.isNull;

/**
 * @author njlacap
 */
@Service
public class SurveyDataService {

    private static final Logger log = LoggerFactory.getLogger(SurveyDataService.class);

    private SurveyDataRepository surveyDataRepository;
    private ModelMapper modelMapper;

    public SurveyDataService() { }

    @Autowired
    public SurveyDataService(SurveyDataRepository surveyDataRepository, ModelMapper modelMapper) {
        this.surveyDataRepository = surveyDataRepository;
        this.modelMapper = modelMapper;
    }

//    public List<? extends BaseSurveyDataDto> searchFilterAndSort(SurveyDataSpecification surveyDataSpecification, String sort, String fields) {
//        log.info("REST API GET - Querying and sorting result");
//        return switch (fields) {
//            case "age,industry,jobTitle" ->
//                    convertToDto(surveyDataRepository.findAll(surveyDataSpecification, PageRequest.of(0, Integer.MAX_VALUE, Sort.by(sort).ascending())).toList(), SurveyDataDtoCustomF.class);
//            case "annualSalary,currency,location" ->
//                    convertToDto(surveyDataRepository.findAll(surveyDataSpecification, PageRequest.of(0, Integer.MAX_VALUE, Sort.by(sort).ascending())).toList(), SurveyDataDtoCustomS.class);
//            case "age,industry,jobTitle,annualSalary,currency,location" ->
//                    convertToDto(surveyDataRepository.findAll(surveyDataSpecification, PageRequest.of(0, Integer.MAX_VALUE, Sort.by(sort).ascending())).toList(), SurveyDataDtoCustomT.class);
//            default ->
//                    convertToDto(surveyDataRepository.findAll(surveyDataSpecification, PageRequest.of(0, Integer.MAX_VALUE, Sort.by(sort).ascending())).toList(), SurveyDataDtoCustom.class);
//        };
//    }

    public List<? extends BaseSurveyDataDto> searchFilterAndSort(SurveyDataSpecification surveyDataSpecification, String sort) {
        log.info("REST API GET - Querying and sorting result");
        return convertToDto(surveyDataRepository.findAll(surveyDataSpecification, PageRequest.of(0, Integer.MAX_VALUE, Sort.by(sort).ascending())).toList(), SurveyDataDtoCustom.class);
    }

    public Long searchFilterAndCount(SurveyDataSpecification surveyDataSpecification) {
        log.info("REST API GET - Querying and counting result");
        return surveyDataRepository.count(surveyDataSpecification);
    }

    private <T> List<T> convertToDto(List<SurveyData> toList, Class<T> tClass) {
        List<T> dtoList = new ArrayList<>();
        for (SurveyData surveyData : toList) {
            dtoList.add(modelMapper.map(surveyData, tClass));
        }
        return dtoList;
    }

    /**
     * @deprecated For Basic filtering cases only
     */
    @Deprecated
    public List<?> search(String ageBracket, String industry, String jobTitle, Double salaryGte, Double salaryLte, String currency, String location, String workExperience) {
        return surveyDataRepository.findAll(createSpecs(ageBracket,industry,jobTitle,salaryGte,salaryLte,currency,location,workExperience));
    }

    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static Specification<SurveyData> createSpecs(String ageBracket, String industry, String jobTitle, Double salaryGte, Double salaryLte, String currency, String location, String workExperience) {

        Specification<SurveyData> surveyDataSpecification = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get("id"),1));

        validateAgeSpecs(ageBracket, surveyDataSpecification);
        validateIndustrySpecs(industry, surveyDataSpecification);
        validateJobTitleSpecs(jobTitle, surveyDataSpecification);
        validateCurrencySpecs(currency, surveyDataSpecification);
        validateLocationSpecs(location, surveyDataSpecification);
        validateWorkExperienceSpecs(workExperience, surveyDataSpecification);
        validateSalarySpecs(salaryGte, salaryLte, surveyDataSpecification);

        return surveyDataSpecification;
    }

    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static void validateAgeSpecs(String ageBracket, Specification<SurveyData> surveyDataSpecification) {
        if(!isNull(ageBracket)) {
            surveyDataSpecification.and(getSurveyDataByAgeBracket(ageBracket));
        }
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static void validateIndustrySpecs(String industry, Specification<SurveyData> surveyDataSpecification) {
        if(!isNull(industry)) {
            surveyDataSpecification.and(getSurveyDataByIndustry(industry));
        }
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static void validateJobTitleSpecs(String jobTitle, Specification<SurveyData> surveyDataSpecification) {
        if(!isNull(jobTitle)) {
            surveyDataSpecification.and(getSurveyDataByJobTitle(jobTitle));
        }
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static void validateCurrencySpecs(String currency, Specification<SurveyData> surveyDataSpecification) {
        if(!isNull(currency)) {
            surveyDataSpecification.and(getSurveyDataByCurrency(currency));
        }
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static void validateLocationSpecs(String location, Specification<SurveyData> surveyDataSpecification) {
        if(!isNull(location)) {
            surveyDataSpecification.and(getSurveyDataByLocation(location));
        }
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static void validateWorkExperienceSpecs(String workExperience, Specification<SurveyData> surveyDataSpecification) {
        if(!isNull(workExperience)) {
            surveyDataSpecification.and(getSurveyDataByWorkExp(workExperience));
        }
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static void validateSalarySpecs(Double salaryGte, Double salaryLte, Specification<SurveyData> surveyDataSpecification) {
        if(!isNull(salaryLte)) {
            surveyDataSpecification.and(getSurveyDataBySalaryLte(salaryLte));
        } else if(!isNull(salaryGte)) {
            surveyDataSpecification.and(getSurveyDataBySalaryGte(salaryGte));
        }
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static Specification<SurveyData> getSurveyDataByAgeBracket(String ageBracket) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("age"), "%"+ageBracket+"%");
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static Specification<SurveyData> getSurveyDataByIndustry(String industry) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("industry"), "%"+industry+"%");
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static Specification<SurveyData> getSurveyDataByJobTitle(String jobTitle) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("job_title"), "%"+jobTitle+"%");
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static Specification<SurveyData> getSurveyDataBySalaryGte(Double salaryGte) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("salary"), salaryGte);
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static Specification<SurveyData> getSurveyDataBySalaryLte(Double salaryLte) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("salary"), salaryLte);
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static Specification<SurveyData> getSurveyDataByCurrency(String currency) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("currency"), "%"+currency+"%");
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static Specification<SurveyData> getSurveyDataByLocation(String location) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("location"), "%"+location+"%");
    }
    /**
     * @deprecated
     * For Basic filtering cases only
     */
    @Deprecated
    private static Specification<SurveyData> getSurveyDataByWorkExp(String work) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("workExperience"), "%"+work+"%");
    }


}
