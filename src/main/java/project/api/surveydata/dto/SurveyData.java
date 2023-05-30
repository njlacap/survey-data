package project.api.surveydata.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.api.surveydata.helper.SurveyDataJsonParser;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@JsonDeserialize(using = SurveyDataJsonParser.class)
public class SurveyData {

    public SurveyData(Date timestamp, String age, String industry, String jobTitle, Double annualSalary, String currency, String location, String workExperience, String jobTitleOther, String currencyOther) {
        this.timestamp = timestamp;
        this.age = age;
        this.industry = industry;
        this.jobTitle = jobTitle;
        this.annualSalary = annualSalary;
        this.currency = currency;
        this.location = location;
        this.workExperience = workExperience;
        this.jobTitleOther = jobTitleOther;
        this.currencyOther = currencyOther;
    }

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "timestamp")
    private Date timestamp;
    @Column(name = "age")
    private String age;
    @Column(name = "industry")
    private String industry;
    @Column(name = "job_title")
    private String jobTitle;
    @Column(name = "annual_salary", precision = 2)
    private Double annualSalary;
    @Column(name = "currency")
    private String currency;
    @Column(name = "location")
    private String location;
    @Column(name = "work_experience")
    private String workExperience;
    @Column(name = "job_title_other",length = 500)
    private String jobTitleOther;
    @Column(name = "currency_other")
    private String currencyOther;

}
