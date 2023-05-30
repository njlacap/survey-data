package project.api.surveydata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDataDtoCustomT extends BaseSurveyDataDto {
    private String age;
    private String industry;
    private String jobTitle;
    private Double annualSalary;
    private String currency;
    private String location;
}
