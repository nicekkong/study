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


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

//    @MockBean
//    EventRepository eventRepository;

    @Test
    public void createEvent() throws Exception {

        EventDto event = EventDto.builder()
                .name("spring")
                .description("nicekkong's world")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 11, 11))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 12, 31, 11, 11))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 10,11))
                .endEventDateTime(LocalDateTime.of(2018, 12, 25, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("반포역 4번 출구")
                .free(true)
                .build();

//        event.setId(100);
//        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("free").value(Matchers.not(false)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
        ;
    }


    @Test
    public void createEvent_Bad_Request() throws Exception {

        Event event = Event.builder()
                .id(100)
                .name("spring")
                .description("nicekkong's world")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 11, 11))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 12, 31, 11, 11))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 10,11))
                .endEventDateTime(LocalDateTime.of(2018, 12, 25, 00, 00))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("반포역 4번 출구")
                .free(true)
                .build();


        System.out.println(objectMapper.writeValueAsString(event));
//        event.setId(100);
//        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createEvent_Bad_request_Empty_Input() throws Exception {

        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(eventDto))
        ).andExpect(status().isBadRequest());

    }



    @Test
    public void createEvent_Bad_Request_Wrong_INput() throws Exception {

        Event event = Event.builder()
                .id(100)
                .name("spring")
                .description("nicekkong's world")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 11, 11))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 12, 31, 11, 11))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 30, 10,11))
                .endEventDateTime(LocalDateTime.of(2018, 12, 7, 00, 00))
                .basePrice(1000000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("반포역 4번 출구")
                .free(true)
                .build();

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
