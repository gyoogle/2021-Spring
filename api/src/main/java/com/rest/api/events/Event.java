package com.rest.api.events;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Event {

    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice;  // (optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    private EventStatus eventStatus = EventStatus.DRAFT;

    @Builder
    public Event(Integer id, String name, String description,
                 LocalDateTime beginEnrollmentDateTime, LocalDateTime closeEnrollmentDateTime,
                 LocalDateTime beginEventDateTime, LocalDateTime endEventDateTime,
                 String location, int basePrice, int maxPrice, int limitOfEnrollment,
                 boolean offline, boolean free, EventStatus eventStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.beginEnrollmentDateTime = beginEnrollmentDateTime;
        this.closeEnrollmentDateTime = closeEnrollmentDateTime;
        this.beginEventDateTime = beginEventDateTime;
        this.endEventDateTime = endEventDateTime;
        this.location = location;
        this.basePrice = basePrice;
        this.maxPrice = maxPrice;
        this.limitOfEnrollment = limitOfEnrollment;
        this.offline = offline;
        this.free = free;
        this.eventStatus = eventStatus;
    }

    public void saveNameAndDescription(String name, String description) {
        this.name = name;
        this.description = description;
    }
}