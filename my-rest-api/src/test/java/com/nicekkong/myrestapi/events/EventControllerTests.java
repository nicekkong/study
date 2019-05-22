/*
 * Copyright (c) 2019.
 * nicekkong JE Foundation
 */

/******************************************************
 * Project Name : my-rest-api
 * File Name    : .java
 * Author       : nicekkong@gmail.com
 * Create Date  : 2019-05-22 23:54
 * Description  : 
 ******************************************************/

package com.nicekkong.myrestapi.events;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createEvent() throws Exception {
        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isCreated());

    }
}
