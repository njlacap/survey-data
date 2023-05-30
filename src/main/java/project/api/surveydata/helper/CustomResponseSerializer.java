package project.api.surveydata.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.api.surveydata.dto.SurveyDataDtoCustom;

import java.io.IOException;
import java.util.List;

public final class CustomResponseSerializer {

    private final static Logger log = LoggerFactory.getLogger(CustomResponseSerializer.class);

    public static List<?> serialize(String includedFields, List<SurveyDataDtoCustom> data) throws IOException {
        log.info("Custom Serializer - data list: {}",data.toString());

        List<String> fields = List.of(includedFields.split(","));

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.readerFor(new TypeReference<List<SurveyDataDtoCustom>>() {});
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(data));

        for(JsonNode jn : jsonNode) {
            ((ObjectNode)jn).remove(fields);
        }

        return objectReader.readValue(jsonNode);
    }
}
