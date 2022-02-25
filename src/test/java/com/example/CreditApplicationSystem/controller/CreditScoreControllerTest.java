package com.example.CreditApplicationSystem.controller;

import com.example.CreditApplicationSystem.exception.handler.GenericExceptionHandler;
import com.example.CreditApplicationSystem.model.entity.CreditScore;
import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.service.iml.CreditScoreServiceImpl;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CreditScoreControllerTest {
    private MockMvc mvc;

    @Mock
    private CreditScoreServiceImpl creditScoreService;

    @InjectMocks
    private CreditScoreController creditScoreController;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(creditScoreController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void addCreditScore() throws Exception {
        // init test values
        List<CreditScore> expectedCreditScores = getTestCreditScores();
        Customer customer1 = new Customer(3, "Henry", "Cavill", "45879652158", "5069874152", 8500);
        CreditScore expectedCreditScore=new CreditScore(3,750,customer1);
        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedCreditScoreJsonStr = ow.writeValueAsString(expectedCreditScore);
        Mockito.doNothing().when(creditScoreService).addCreditScore(expectedCreditScore);

        MockHttpServletResponse response = mvc.perform(post("/api/creditScore/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedCreditScoreJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(creditScoreService, Mockito.times(1)).addCreditScore(any());
    }

    @Test
    void deleteCreditScore() throws Exception {
        // stub - given
        Mockito.when(creditScoreService.deleteCreditScore(any())).thenReturn(true);

        MockHttpServletResponse response = mvc.perform(delete("/api/creditScore/delete?id=1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        String actualResponseStr = response.getContentAsString();
//        Assert.assertEquals("true", actualResponseStr);
    }

    @Test
    void getCreditScoreById() throws Exception {
        // init test values
        List<CreditScore> expectedCreditScores = getTestCreditScores();

        // stub - given
        Mockito.when(creditScoreService.getCreditScoreById(1)).thenReturn(expectedCreditScores.get(0));

        MockHttpServletResponse response = mvc.perform(get("/api/creditScore/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        CreditScore actualCreditScore = new ObjectMapper().readValue(response.getContentAsString(), CreditScore.class);
        Assert.assertEquals(expectedCreditScores.get(0), actualCreditScore);
    }

    @Test
    void getAllCreditScore() throws Exception {
        // init test values / given
        List<CreditScore> expectedCreditScores = getTestCreditScores();

        // stub - when
        Mockito.when(creditScoreService.getAllCreditScore()).thenReturn(expectedCreditScores);

        MockHttpServletResponse response = mvc.perform(get("/api/creditScore/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<CreditScore> actualCreditScores = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<CreditScore>>() {
        });
        assertEquals(expectedCreditScores.size(), actualCreditScores.size());
    }


    @Test
    void getCreditScoreByCustomerNationalID() throws Exception {
        // init test values
        List<CreditScore> expectedCreditScores = getTestCreditScores();

        // stub - given
        Mockito.when(creditScoreService.getCreditScoreByCustomerNationalID("81566338254")).thenReturn(expectedCreditScores.get(0));

        MockHttpServletResponse response = mvc.perform(get("/api/creditScore/getCreditScoreByNationalID?nationalID=81566338254")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        CreditScore actualCreditScore = new ObjectMapper().readValue(response.getContentAsString(), CreditScore.class);
        Assert.assertEquals(expectedCreditScores.get(0), actualCreditScore);
    }

    private List<CreditScore> getTestCreditScores(){
        Customer customer1 = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer2 = new Customer(2, "Andrew", "Hooper", "22255942075", "2255942075", 4500);
        CreditScore creditScore1=new CreditScore(1,450,customer1);
        CreditScore creditScore2=new CreditScore(2,680,customer2);
        List<CreditScore> creditScores=new ArrayList<>();
        creditScores.add(creditScore1);
        creditScores.add(creditScore2);
        return creditScores;
    }
}