package com.example.CreditApplicationSystem.controller;

import com.example.CreditApplicationSystem.exception.handler.GenericExceptionHandler;
import com.example.CreditApplicationSystem.model.entity.CreditApplication;
import com.example.CreditApplicationSystem.model.entity.CreditScore;
import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.service.iml.CreditApplicationServiceImpl;

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
class CreditApplicationControllerTest {
    private MockMvc mvc;

    @Mock
    private CreditApplicationServiceImpl creditApplicationService;

    @InjectMocks
    private CreditApplicationController creditApplicationController;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(creditApplicationController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void getAllCreditApplication() throws Exception{
        // init test values / given
        List<CreditApplication> expectedCreditApplications = getTestCreditApplications();

        // stub - when
        Mockito.when(creditApplicationService.getAllCreditApplication()).thenReturn(expectedCreditApplications);

        MockHttpServletResponse response = mvc.perform(get("/api/creditApplication/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<CreditApplication> actualCreditApplications = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<CreditApplication>>() {
        });
        assertEquals(expectedCreditApplications.size(), actualCreditApplications.size());
    }

    @Test
    void getCreditApplicationById()throws Exception{
        // init test values
        List<CreditApplication> expectedCreditApplications = getTestCreditApplications();

        // stub - given
        Mockito.when(creditApplicationService.getCreditApplicationById(1)).thenReturn(expectedCreditApplications.get(0));

        MockHttpServletResponse response = mvc.perform(get("/api/creditApplication/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        CreditApplication actualCreditApplication = new ObjectMapper().readValue(response.getContentAsString(), CreditApplication.class);
        Assert.assertEquals(expectedCreditApplications.get(0), actualCreditApplication);
    }

    @Test
    void addCreditApplication() throws Exception{
        Customer customer1 = new Customer(3, "Henry", "Cavill", "45879652158", "5069874152", 8500);
        CreditApplication expectedCreditApplication=new CreditApplication(3,customer1,"Approved",20000,new Date());
        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedCreditApplicationJsonStr = ow.writeValueAsString(expectedCreditApplication);
        Mockito.doNothing().when(creditApplicationService).addCreditApplication(expectedCreditApplication);

        MockHttpServletResponse response = mvc.perform(post("/api/creditApplication/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedCreditApplicationJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(creditApplicationService, Mockito.times(1)).addCreditApplication(any());
    }

    @Test
    void checkCreditApplicationStatus() throws Exception{
        // init test values
        List<CreditApplication> expectedCreditApplications = getTestCreditApplications();

        // stub - given
        Mockito.when(creditApplicationService.checkCreditApplicationStatus("81566338254")).thenReturn(expectedCreditApplications.get(0));

        MockHttpServletResponse response = mvc.perform(get("/api/creditApplication/checkCreditApplicationStatus?nationalID=81566338254")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        CreditApplication actualCreditApplication = new ObjectMapper().readValue(response.getContentAsString(), CreditApplication.class);
        Assert.assertEquals(expectedCreditApplications.get(0), actualCreditApplication);
    }

    @Test
    void getCreditApplicationByCustomerId() throws Exception{
        // init test values
        List<CreditApplication> expectedCreditApplications = getTestCreditApplications();

        // stub - given
        Mockito.when(creditApplicationService.getCreditApplicationByCustomerId(1)).thenReturn(expectedCreditApplications.get(0));

        MockHttpServletResponse response = mvc.perform(get("/api/creditApplication/by-customer?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        CreditApplication actualCreditApplication = new ObjectMapper().readValue(response.getContentAsString(), CreditApplication.class);
        Assert.assertEquals(expectedCreditApplications.get(0), actualCreditApplication);
    }

    @Test
    void checkCreditApplicationResult()throws Exception {
    }

    private List<CreditApplication> getTestCreditApplications(){
        //init
        Customer customer1 = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer2 = new Customer(2, "Andrew", "Hooper", "22255942075", "2255942075", 4500);
        List<Customer> customers=new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        CreditApplication creditApplication=new CreditApplication(1,customer1,"Approved",20000,new Date());
        CreditApplication creditApplication1=new CreditApplication(2,customer2,"Approved",10000,new Date());
        List<CreditApplication>creditApplications=new ArrayList<>();
        creditApplications.add(creditApplication1);
        creditApplications.add(creditApplication);
        return creditApplications;
    }
}