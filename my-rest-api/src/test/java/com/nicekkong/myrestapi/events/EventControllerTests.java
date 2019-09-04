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
import com.nicekkong.myrestapi.common.TestDescription;
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
//@WebMvcTest       // Web과 관련된 Bean만 만들어서 테스트를 할 수 있게 한다.(MockMVC)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;    // 웹 서버를 띄우지 않기 때문에 빠르게 구동이 가능하다. / DispatcherServlet 까지 만든다.

    @Autowired
    ObjectMapper objectMapper;

//    @MockBean // @WebMvcTest를 사용할 경우
//    EventRepository eventRepository;  // Mock 객체는 리턴되는 값이 모두 null 이다.

    @Test
    @TestDescription("최초 테스트")
    public void createEvent() throws Exception {

        EventDto event = EventDto.builder()
                .name("spring")
                .description("nicekkong's world")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 11, 11))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 12, 31, 11, 11))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 10,11))
//                .endEventDateTime(LocalDateTime.of(2018, 12, 25, 00, 00))
                .endEventDateTime(LocalDateTime.of(2019, 12, 20, 10, 30))
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
                    .accept(MediaTypes.HAL_JSON)    // 원하는 Response
                    .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())    // 201 응답
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("free").value(Matchers.not(false)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
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
    @TestDescription("입력값이 없을 경우 에러가 발생하는 테스트")
    public void createEvent_Bad_request_Empty_Input() throws Exception {

        // Controller 의 @Valid annotation 을 통한 검증을 한다.
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @TestDescription("입력값을 잘못 사용했을 경우 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {

        EventDto event = EventDto.builder()
                .name("spring")
                .description("nicekkong's world")
                .beginEnrollmentDateTime(LocalDateTime.of(2020, 11, 11, 11, 11))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 12, 31, 11, 11))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 10,11))
//                .endEventDateTime(LocalDateTime.of(2018, 12, 25, 00, 00))
                .endEventDateTime(LocalDateTime.of(2019, 12, 20, 10, 30))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("반포역 4번 출구")
                .free(true)
                .build();

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)    // 원하는 Response
                .content(objectMapper.writeValueAsString(event)))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0].objectName").exists())
            .andExpect(jsonPath("$[0].field").exists())
            .andExpect(jsonPath("$[0].defaultMessage").exists())
            .andExpect(jsonPath("$[0].code").exists())
            .andExpect(jsonPath("$[0].rejectedValue").exists())
            ;
    }


    @Test
    @TestDescription("비즈니스 로직 적용 여부 확인")
    public void procBusinessEvent() throws Exception {

        EventDto event = EventDto.builder()
                .name("spring")
                .description("nicekkong's world")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 11, 11, 11))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 12, 31, 11, 11))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 10,11))
//                .endEventDateTime(LocalDateTime.of(2018, 12, 25, 00, 00))
                .endEventDateTime(LocalDateTime.of(2019, 12, 20, 10, 30))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("반포역 4번 출구")
                .build();

//        event.setId(100);
//        Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)    // 원하는 Response
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())    // 201 응답
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

}
