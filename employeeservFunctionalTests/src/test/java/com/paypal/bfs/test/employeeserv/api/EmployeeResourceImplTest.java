package com.paypal.bfs.test.employeeserv.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeResourceImplTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private EmployeeResourceImpl employeeResourceImpl;


    @Autowired
    private EmployeeRepository employeeRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void saveEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);

         // check for 4xx
         this.mvc.perform(post("/v1/bfs/employees")
                .content(objectMapper.writeValueAsBytes(employee)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError()).andReturn();

         //check for 2xx
        employee.setId(4);
        Address address = new Address();
        address.setLine1("line1");
        address.setCountry("india");
        address.setZipCode("221002");
        address.setCity("Noida");
        address.setState("up");
        employee.setAddress(address);
        employee.setLastName("last");
        employee.setFirstName("fname");
        employee.setDateOfBirth("ddmmyy");

        this.mvc.perform(post("/v1/bfs/employees")
                .content(objectMapper.writeValueAsBytes(employee)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()).andReturn();

        // resource already exists error
        this.mvc.perform(post("/v1/bfs/employees")
                .content(objectMapper.writeValueAsBytes(employee)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError()).andReturn();

    }




    @Test
    public void getEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);
        Address address = new Address();
        address.setLine1("line1");
        address.setCountry("india");
        address.setZipCode("221002");
        address.setCity("Noida");
        address.setState("up");
        employee.setAddress(address);
        employee.setLastName("last");
        employee.setFirstName("fname");
        employee.setDateOfBirth("ddmmyy");

        employeeRepository.save(employeeResourceImpl.setEmployeeModel(employee));
        // check for 4xx
        this.mvc.perform(get("/v1/bfs/employees/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError()).andReturn();

        // check for 4xx for invalid id
        this.mvc.perform(get("/v1/bfs/employees/ww")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError()).andReturn();

        // check for 4xx when resource is absent
        this.mvc.perform(get("/v1/bfs/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()).andReturn();





    }

}
