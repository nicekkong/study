/*
 * Copyright (c) 2019.
 * nicekkong JE Foundation
 */

/******************************************************
 * Project Name : my-rest-api
 * File Name    : .java
 * Author       : nicekkong@gmail.com
 * Create Date  : 2019-05-23 22:24
 * Description  : 
 ******************************************************/

package com.nicekkong.myrestapi.events;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
