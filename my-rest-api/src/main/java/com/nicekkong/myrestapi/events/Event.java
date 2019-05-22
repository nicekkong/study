/******************************************************
 * Project Name : my-rest-api
 * File Name    : .java
 * Author       : nicekkong@gmail.com
 * Create Date  : 2019-05-22 00:13
 * Description  : 
 ******************************************************/

package com.nicekkong.myrestapi.events;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Event {

    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    private EventStatus eventStatus;

}
