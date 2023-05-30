package project.api.surveydata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "project.api.surveydata.repository")
public class SurveyDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyDataApplication.class, args);
	}


}
