package project.api.surveydata.helper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.api.surveydata.dto.SurveyData;
import project.api.surveydata.exception.SurveyException;
import project.api.surveydata.util.CustomStringUtil;

import java.io.IOException;
import java.io.Serial;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SurveyDataJsonParser extends StdDeserializer {

    private static final Logger log = LoggerFactory.getLogger(SurveyDataJsonParser.class);
    @Serial
    private static final long serialVersionUID = -6077135882080235494L;

    public SurveyDataJsonParser() {
        this(null);
    }

    public SurveyDataJsonParser(Class vc) {
        super(vc);
    }


    @Override
    public SurveyData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        log.info("Deserializing JSON data");

        Date newtimestamp;

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        final String timestamp = jsonNode.get("Timestamp").asText();
        final String age = jsonNode.get("How old are you?").asText();
        final String industry = jsonNode.get("What industry do you work in?").asText();
        final String jobTitle = jsonNode.get("Job title").asText();
        String annualSalary = jsonNode.get("What is your annual salary?").asText();
        String cleanSalary = CustomStringUtil.removeMultiplePoint(annualSalary);
        final String currency = jsonNode.get("Please indicate the currency").asText();
        final String location = jsonNode.get("Where are you located? (City/state/country)").asText();
        final String workExperience = jsonNode.get("How many years of post-college professional work experience do you have?").asText();
        final String jobTitleOther = jsonNode.get("If your job title needs additional context, please clarify here:").asText();
        final String currencyOther = jsonNode.get("If \"Other,\" please indicate the currency here:").asText();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
           newtimestamp = sdf.parse(timestamp);
        } catch (ParseException e) {
            throw new SurveyException(e.getMessage());
        }

        return new SurveyData(newtimestamp,age,industry,jobTitle,Double.parseDouble(cleanSalary),currency,location,workExperience,(jobTitleOther.length()>500) ? jobTitleOther.substring(0,499) : jobTitleOther,currencyOther);

    }
}
