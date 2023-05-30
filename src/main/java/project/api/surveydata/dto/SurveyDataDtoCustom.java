package project.api.surveydata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SurveyDataDtoCustom extends BaseSurveyDataDto {
    private String age;
    private String industry;
    private String jobTitle;
    private Double annualSalary;
    private String currency;
    private String location;
    private String workExperience;
    private String jobTitleOther;
    private String currencyOther;
}
