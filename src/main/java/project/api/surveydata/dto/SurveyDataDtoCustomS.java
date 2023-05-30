package project.api.surveydata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDataDtoCustomS extends BaseSurveyDataDto {
    private Double annualSalary;
    private String currency;
    private String location;
}
