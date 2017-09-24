package com.oddle.app;

import com.oddle.app.configuration.AppConfig;
import com.oddle.app.model.City;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class AppControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void test_homepage() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
        this.mockMvc.perform(builder)
                .andExpect(ok); // not 400 bcz we do not want to show error to client
    }

    @Test
    public void test_invalid_request() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status().isOk();

        City city = new City();
        city.setCityName("xxx");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/list");
        this.mockMvc.perform(builder)
                .andExpect(ok)
                .andReturn().getResponse().getContentAsString().contains("Please enter a valid city name");
    }

    @Test
    public void test_search_page() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status().isOk();
        City city = new City();
        city.setCityName("Singapore");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/list");
        mockMvc.perform(builder)
                .andExpect(ok)
                .andReturn().getResponse().getContentAsString().contains("Singapore");
    }
}