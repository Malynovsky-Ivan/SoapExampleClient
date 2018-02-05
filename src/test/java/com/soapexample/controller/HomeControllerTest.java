package com.soapexample.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.soapexample.ProjectContants.DEFAULT_COUNT_RESULT;
import static com.soapexample.ProjectContants.DEFAULT_VIDEO_SCREEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class HomeControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void homeTest() throws Exception {
        mockMvc.perform(get("/", "/home"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("fileName", equalTo(DEFAULT_VIDEO_SCREEN)))
                .andExpect(model().attribute("countMatches", equalTo(DEFAULT_COUNT_RESULT)))
                .andExpect(model().attributeExists("files"))
                .andExpect(view().name("home"));
    }
    @Test
    public void getFileTest() throws Exception {
        mockMvc.perform(post("/getFile").param("fileName", "file1.txt"))
                .andExpect(view().name("redirect:home"))
                .andExpect(redirectedUrl("home"));

    }

    @Test
    public void searchWordTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "file21.txt", "text/plain", "Test text".getBytes());
        mockMvc.perform(fileUpload("/searchWord").file(file).param("searchWord", "Word"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:home"))
                .andExpect(redirectedUrl("home"));
    }
}
