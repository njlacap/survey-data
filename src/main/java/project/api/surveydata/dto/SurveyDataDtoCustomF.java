package project.api.surveydata.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDataDtoCustomF extends BaseSurveyDataDto {
    private String age;
    private String industry;
    private String jobTitle;
}
