package com.example.CreditApplicationSystem.controller;

import com.example.CreditApplicationSystem.exception.handler.GenericExceptionHandler;
import com.example.CreditApplicationSystem.model.entity.Customer;
import com.example.CreditApplicationSystem.service.iml.CustomerServiceImpl;
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
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    private MockMvc mvc;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void add() throws Exception{
        // init test values
        List<Customer> expectedCustomers = getTestCustomers();
        Customer expectedCustomer = new Customer();
        expectedCustomer.setFirstName("Henry");
        expectedCustomer.setLastName("Cavill");
        expectedCustomer.setNationalID("28795641582");
        expectedCustomer.setPhoneNumber("5064789852");
        expectedCustomer.setMonthlyIncome(7500);
        expectedCustomers.add(expectedCustomer);

        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedcustomerJsonStr = ow.writeValueAsString(expectedCustomer);
        Mockito.doNothing().when(customerService).addCustomer(expectedCustomer);

        MockHttpServletResponse response = mvc.perform(post("/api/customer/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedcustomerJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(customerService, Mockito.times(1)).addCustomer(any());
    }

    @Test
    void getCustomerById() throws Exception {
        // init test values
        List<Customer> expectedCustomers = getTestCustomers();

        // stub - given
        Mockito.when(customerService.getCustomerById(1)).thenReturn(expectedCustomers.get(0));

        MockHttpServletResponse response = mvc.perform(get("/api/customer/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Customer actualCustomer = new ObjectMapper().readValue(response.getContentAsString(), Customer.class);
        Assert.assertEquals(expectedCustomers.get(0), actualCustomer);
    }

    @Test
    void deleteCustomer() throws Exception {
        // stub - given
        Mockito.when(customerService.deleteCustomer(any())).thenReturn(true);

        MockHttpServletResponse response = mvc.perform(delete("/api/customer/delete?id=1")
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
    void updateCustomer() throws Exception {
        // init test values
        List<Customer> expectedCustomers = getTestCustomers();
        Customer expectedCustomer = new Customer();
        expectedCustomer.setFirstName("Henry");
        expectedCustomer.setLastName("Cavill");
        expectedCustomer.setNationalID("28795641582");
        expectedCustomer.setPhoneNumber("5064789852");
        expectedCustomer.setMonthlyIncome(7500);
        expectedCustomers.add(expectedCustomer);

        // stub - given

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedCustomerJsonStr = ow.writeValueAsString(expectedCustomer);
        Mockito.when(customerService.updateCustomer(expectedCustomer)).thenReturn(expectedCustomers.get(0));

        MockHttpServletResponse response = mvc.perform(put("/api/customer/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedCustomerJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();


        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void getAllCustomer() throws Exception {
        // init test values / given
        List<Customer> expectedCustomers = getTestCustomers();

        // stub - when
        Mockito.when(customerService.getAllCustomer()).thenReturn(expectedCustomers);

        MockHttpServletResponse response = mvc.perform(get("/api/customer/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Customer> actualCustomers = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Customer>>() {
        });
        assertEquals(expectedCustomers.size(), actualCustomers.size());

    }

    @Test
    void getCustomerByNationalID()  throws Exception{
        // init test values
        List<Customer> expectedCustomers = getTestCustomers();

        // stub - given
        Mockito.when(customerService.getCustomerByNationalID("81566338254")).thenReturn(expectedCustomers.get(0));

        MockHttpServletResponse response = mvc.perform(get("/api/customer/getCustomerByNationalID?nationalID=81566338254")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Customer actualCustomer = new ObjectMapper().readValue(response.getContentAsString(), Customer.class);
        Assert.assertEquals(expectedCustomers.get(0), actualCustomer);
    }


    private List<Customer> getTestCustomers(){
        Customer customer = new Customer(1, "Dale", "Gomez", "81566338254", "81547833825", 5500);
        Customer customer1 = new Customer(2, "Andrew", "Hooper", "22255942075", "2255942075", 4500);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer);
        return customers;
    }
}