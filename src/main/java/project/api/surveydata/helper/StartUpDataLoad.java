package project.api.surveydata.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import project.api.surveydata.repository.SurveyDataRepository;

@Component
public class StartUpDataLoad implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(StartUpDataLoad.class);

    @Autowired
    private SurveyDataRepository surveyDataRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Application start up - saving data to in-memory H2 DB");
        surveyDataRepository.saveAll(FileReaderHelper.convertJsonDataToList());
    }
}
