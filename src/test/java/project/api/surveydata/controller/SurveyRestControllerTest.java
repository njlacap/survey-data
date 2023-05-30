package project.api.surveydata.controller;


import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.api.surveydata.config.CustomMDCFilter;
import project.api.surveydata.config.RestGlobalExceptionHandler;
import project.api.surveydata.dto.BaseSurveyDataDto;
import project.api.surveydata.dto.SurveyData;
import project.api.surveydata.dto.SurveyDataDtoCustom;
import project.api.surveydata.repository.SurveyDataRepository;
import project.api.surveydata.service.SurveyDataService;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SurveyRestController.class)
public class SurveyRestControllerTest {

    public static final String AGE_KEY = "age";
    public static final String INDUSTRY_KEY = "industry";
    public static final String SORT_KEY = "sort";
    public static final String FIELDS_KEY = "fields";
    public static final String COUNT_KEY = "count";
    public static final String CORRELATION_ID = "Correlation_ID";
    public static final String MDC_FILTER_UUID = "MDCFilter.UUID";

    MockMvc mockMvc;
    ModelMapper modelMapper;
    @MockBean
    SurveyDataRepository surveyDataRepository;
    @MockBean
    SurveyDataService surveyDataService;
    @Autowired
    @InjectMocks
    SurveyRestController surveyRestController;

    List<SurveyData> surveyDataList = new ArrayList<>();
    List<? extends BaseSurveyDataDto> surveyDataDtoCustomList = new ArrayList<>();


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(surveyRestController)
                .setControllerAdvice(new RestGlobalExceptionHandler())
                .addFilters(new CustomMDCFilter(CORRELATION_ID, MDC_FILTER_UUID))
                .build();
    }

    @Before
    public void generateTestData() {
        modelMapper = new ModelMapper();
        generateSurveyData();
        generateDtoSurveyData();
    }

    @Test
    public void getApiWithNoParametersShouldReturnHttpStatusOK() throws Exception {

        doReturn(surveyDataDtoCustomList).when(surveyDataService).searchFilterAndSort(any(),any(),any());

        mockMvc.perform(get("/survey").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(log())
                .andReturn();

        verify(surveyDataService, times(1)).searchFilterAndSort(any(),any(),any());

    }

    @Test
    public void getApiWithFiltersShouldReturnHttpStatusOK() throws Exception {

        doReturn(surveyDataDtoCustomList).when(surveyDataService).searchFilterAndSort(any(),any(),any());

        mockMvc.perform(get("/survey")
                .contentType(MediaType.APPLICATION_JSON)
                .param(AGE_KEY,"18-24")
                .param(INDUSTRY_KEY,"Researcher"))
                .andExpect(status().isOk())
                .andDo(log())
                .andReturn();

        verify(surveyDataService, times(1)).searchFilterAndSort(any(),any(),any());

    }

    @Test
    public void getApiWithFiltersAndSortShouldReturnHttpStatusOK() throws Exception {

        doReturn(surveyDataDtoCustomList).when(surveyDataService).searchFilterAndSort(any(),any(),any());

        mockMvc.perform(get("/survey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(AGE_KEY,"18-24")
                        .param(INDUSTRY_KEY,"Researcher")
                        .param(SORT_KEY,"annualSalary"))
                .andExpect(status().isOk())
                .andDo(log())
                .andReturn();

        verify(surveyDataService, times(1)).searchFilterAndSort(any(),any(),any());

    }

    @Test
    public void getApiWithFiltersAndSelectedFieldsShouldReturnHttpStatusOK() throws Exception {

        doReturn(surveyDataDtoCustomList).when(surveyDataService).searchFilterAndSort(any(),any(),any());

        mockMvc.perform(get("/survey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(AGE_KEY,"18-24")
                        .param(INDUSTRY_KEY,"Researcher")
                        .param(FIELDS_KEY,"age,industry,jobTitle"))
                .andExpect(status().isOk())
                .andDo(log())
                .andReturn();

        verify(surveyDataService, times(1)).searchFilterAndSort(any(),any(),any());

    }

    @Test
    public void getApiWithFiltersAndSortAndSelectedFieldsShouldReturnHttpStatusOK() throws Exception {

        doReturn(surveyDataDtoCustomList).when(surveyDataService).searchFilterAndSort(any(),any(),any());

        mockMvc.perform(get("/survey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(AGE_KEY,"18-24")
                        .param(INDUSTRY_KEY,"Researcher")
                        .param(SORT_KEY,"annualSalary")
                        .param(FIELDS_KEY,"age,industry,jobTitle"))
                .andExpect(status().isOk())
                .andDo(log())
                .andReturn();

        verify(surveyDataService, times(1)).searchFilterAndSort(any(),any(),any());

    }

    @Test
    public void getApiWithCountShouldReturnHttpStatusOK() throws Exception {

        doReturn(surveyDataDtoCustomList).when(surveyDataService).searchFilterAndSort(any(),any(),any());

        mockMvc.perform(get("/survey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(COUNT_KEY,"true"))
                .andExpect(status().isOk())
                .andDo(log())
                .andReturn();

        verify(surveyDataService, times(1)).searchFilterAndCount(any());

    }

    @Test
    public void getApiWithFiltersAndSortAndSelectedFieldsAndCountShouldReturnHttpStatusOK() throws Exception {

        doReturn(surveyDataDtoCustomList).when(surveyDataService).searchFilterAndSort(any(),any(),any());

        mockMvc.perform(get("/survey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(AGE_KEY,"18-24")
                        .param(INDUSTRY_KEY,"Researcher")
                        .param(SORT_KEY,"annualSalary")
                        .param(FIELDS_KEY,"age,industry,jobTitle")
                        .param(COUNT_KEY,"true"))

                .andExpect(status().isOk())
                .andDo(log())
                .andReturn();

        verify(surveyDataService, times(1)).searchFilterAndCount(any());

    }

    private void generateSurveyData() {
        surveyDataList.add(new SurveyData(Date.from(Instant.now()),"18-24","Government","Researcher",1000D,"USD","US","10 years","",""));
        surveyDataList.add(new SurveyData(Date.from(Instant.now()),"25-30","Hospital","Doctor",5000D,"USD","US","8 years","",""));
    }

    private void generateDtoSurveyData() {
        for(SurveyData surveyData : surveyDataList) {
            surveyDataDtoCustomList.add(modelMapper.map(surveyData, (Type) SurveyDataDtoCustom.class));
        }
    }
}
