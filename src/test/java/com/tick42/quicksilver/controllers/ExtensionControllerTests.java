package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.DTO.ExtensionDTO;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.User;
import com.tick42.quicksilver.services.ExtensionServiceImpl;
import com.tick42.quicksilver.services.base.ExtensionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExtensionControllerTests {

    @MockBean
    ExtensionServiceImpl service;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void get_() throws Exception {

        ExtensionDTO extension = new ExtensionDTO();
        extension.setName("Sample name");

        when(service.findById(5, null)).thenReturn(extension);

        mockMvc.perform(get("/api/extensions/{id}", 5))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sample")));
    }
}
