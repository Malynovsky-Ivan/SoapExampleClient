package com.soapexample.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.soapexample.ProjectContants.DEFAULT_VIDEO_SCREEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)

@SpringBootTest
public class HomeControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    List<String> list = Arrays.asList("english.mp4", "file1.txt", "story.mp4", "video.mp4");

    @Test
    public void getFileTest() throws Exception {
      /*  ResultMatcher ok = status().isOk();
        ResultMatcher msg = model()
                .attribute("fileName", DEFAULT_VIDEO_SCREEN);
        MockHttpServletRequestBuilder builder = get("/");
        this.mockMvc.perform(builder)
                .andExpect(ok)
                .andExpect(msg);*/
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("fileName", equalTo(DEFAULT_VIDEO_SCREEN)))
                .andExpect(model().attribute("files", equalTo(list)))
                .andExpect(view().name("home"));

    }
}
