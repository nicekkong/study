/******************************************************
 * Project Name : my-rest-api
 * File Name    : .java
 * Author       : nicekkong@gmail.com
 * Create Date  : 2019-05-22 00:22
 * Description  : 
 ******************************************************/
package com.nicekkong.myrestapi.events;


import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class EventTest {

    @Test
    public void builder() {

        Event event = Event.builder()
                .name("nicekkong")
                .description("Nicekkong's world")
                .build();
    }

    @Test
    public void javaBean() {

        Event event = new Event();

        String name = "Nicekkong";
        event.setName(name);
        event.setDescription("Nicekkong's World");

        assertThat(event.getName()).isEqualTo(name);

    }

    @Test
    public void domainTest() {

        Domain domain = Domain.builder()
                .name("nicekkong")
                .build();

        Domain domain1 = Domain.builder()
                .name("adfadfadfad").build();


        Domain2 domain2 = new Domain2();
        domain2.setName("Adfadfa");


        Domain2 d2 = Domain2.builder()
                .name("asdfadsf")
                .build();
    }
}