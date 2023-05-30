package project.api.surveydata.helper;

import org.springframework.data.jpa.domain.Specification;
import project.api.surveydata.dto.SearchCriteria;
import project.api.surveydata.dto.SurveyData;
import project.api.surveydata.util.CustomParamChecker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * @author njlacap
 */
public class SurveyDataSpecification implements Specification<SurveyData> {

    @Serial
    private static final long serialVersionUID = 4159925409694619372L;

    private final List<SearchCriteria> searchCriteriaList = new ArrayList<>();

    public void add(SearchCriteria searchCriteria) {
        searchCriteriaList.add(searchCriteria);
    }

    @Override
    public Predicate toPredicate(Root<SurveyData> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        for (SearchCriteria searchCriteria : searchCriteriaList) {

            if(CustomParamChecker.isNull(searchCriteria.getValue()) || searchCriteria.getValue().equals(false)){
                continue;
            }

            switch (searchCriteria.getKey()) {
                case "age", "currency", "location", "jobTitle", "industry", "workExperience" ->
                        predicateList.add(criteriaBuilder.like(root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue().toString() + "%"));
                case "salaryGTE" ->
                        predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("annualSalary"), (Double) searchCriteria.getValue()));
                case "salaryLTE" ->
                        predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("annualSalary"), (Double) searchCriteria.getValue()));
            }
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
