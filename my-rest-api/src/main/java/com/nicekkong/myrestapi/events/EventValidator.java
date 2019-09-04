/*
 * Copyright (c) 2019.
 * nicekkong JE Foundation
 */

/******************************************************
 * Project Name : my-rest-api
 * File Name    : .java
 * Author       : nicekkong@gmail.com
 * Create Date  : 2019-05-31 01:26
 * Description  : 
 ******************************************************/

package com.nicekkong.myrestapi.events;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public  void validate(EventDto eventDto, Errors error) {
        if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
            error.rejectValue("basePrice", "wrongValue", "BasePrice is wrong");
            error.rejectValue("maxPrice", "wrongValue", "MaxPrice is wrong");   // rejectValue -> field Error
            error.reject("wrongPrices", "Value for the price is wrong");    // reject -> Global Error
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
        endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
        endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            error.rejectValue("endEventDateTime", "wrongValue", "EndEventDateTime is wrong");
        }


    }
}
