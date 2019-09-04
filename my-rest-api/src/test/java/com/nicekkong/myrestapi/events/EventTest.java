/******************************************************
 * Project Name : my-rest-api
 * File Name    : .java
 * Author       : nicekkong@gmail.com
 * Create Date  : 2019-05-22 00:22
 * Description  : 
 ******************************************************/
package com.nicekkong.myrestapi.events;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JUnitParamsRunner.class)
public class EventTest {

    @Test
    public void builder() {

        Event event = Event.builder()
                .name("nicekkong")
                .description("Nicekkong's world")
                .build();

        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {

        Event event = new Event();


        // Given
        String name = "Nicekkong";
        String description = "Nicekkong's World";

        // When
        event.setName(name);
        event.setDescription(description);


        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);

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


    public void testFree() {

        // Given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        // when
        event.update();

        // then
        assertThat(event.isFree()).isTrue();


        // Given
        Event event2 = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        // when
        event2.update();

        // then
        assertThat(event2.isFree()).isFalse();

    }

    @Test
    @Parameters
    public void testOffline(String location, boolean isOffline) {
        // Given
        Event event = Event.builder()
                .location(location)
                .build();

        // when
        event.update();

        // then
        assertThat(event.isOffline()).isEqualTo(isOffline);

    }

    private Object[] parametersForTestOffline() {
        return new Object[] {
                new Object[] {"반포역", true},
                new Object[] {"", false},
                new Object[] {null, false}
        };
    }


    @Test
//    @Parameters({
//            "0, 0, true",
//            "100, 0, false",
//            "0, 100, false"
//
//    })
//    @Parameters(method = "parametersForTestFreeWithParams")
    @Parameters // Code Convention에 의해 parametersFor[메서드명]() 을 찾는다.
    public void testFreeWithParams(int basePrice, int maxPrice, boolean isFree) {

        // Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // when
        event.update();

        // then
        assertThat(event.isFree()).isEqualTo(isFree);

    }

    private Object[] parametersForTestFreeWithParams() {
        return new Object[] {
                new Object[] {0, 0, true},
                new Object[] {100, 0, false},
                new Object[] {0, 100, false}
        };
    }

}