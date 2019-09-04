package com.nicekkong.myrestapi.events;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id") // 동일성 비교시 연관관계로 인한 상호참조로 Stack-overflow를 방지하기 위해 동일성 비교를 위해 해당 필드에 대한 동일함을 체크한다.
@ToString
@Entity
public class Event {

    @Id @GeneratedValue
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

    @Enumerated(EnumType.STRING)    // 기본값이 Ordinary를 String 타입으로 변경해서 JPA를 통해 DB에 저장한다.
    private EventStatus eventStatus = EventStatus.DRAFT;

    public void update() {
        this.free = this.basePrice == 0 && this.maxPrice == 0;
        this.offline = this.location != null && !this.location.isBlank();
    }
}
