package project.api.surveydata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import project.api.surveydata.dto.SurveyData;

public interface SurveyDataRepository extends JpaRepository<SurveyData, Long>, JpaSpecificationExecutor<SurveyData> {
}
