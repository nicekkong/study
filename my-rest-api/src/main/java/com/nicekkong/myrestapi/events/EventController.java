/*
 * Copyright (c) 2019.
 * nicekkong JE Foundation
 */

/******************************************************
 * Project Name : my-rest-api
 * File Name    : .java
 * Author       : nicekkong@gmail.com
 * Create Date  : 2019-05-23 00:06
 * Description  : 
 ******************************************************/

package com.nicekkong.myrestapi.events;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value="/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {

    private EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody Event event) {

        Event newEvent = this.eventRepository.save(event);

        URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        event.setId(99);
//        return ResponseEntity.created(createdUri).body(event);
        return ResponseEntity.created(createdUri).body(event);
    }
}
