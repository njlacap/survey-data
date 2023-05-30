package project.api.surveydata.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import project.api.surveydata.dto.SurveyData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileReaderHelper {

    public static List<SurveyData> convertJsonDataToList() {
        try(InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("survey_dataset.json"))
        {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, new TypeReference<List<SurveyData>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
