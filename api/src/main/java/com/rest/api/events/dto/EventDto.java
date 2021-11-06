package com.rest.api.events.dto;


import com.rest.api.events.Event;
import com.rest.api.events.EventStatus;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class EventDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotNull
    private LocalDateTime beginEnrollmentDateTime;
    @NotNull
    private LocalDateTime closeEnrollmentDateTime;
    @NotNull
    private LocalDateTime beginEventDateTime;
    @NotNull
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 없으면 온라인 모임
    @Min(0)
    private int basePrice; // (optional)
    @Min(0)
    private int maxPrice;  // (optional)
    @Min(0)
    private int limitOfEnrollment;
    private EventStatus eventStatus;

    @Builder
    public EventDto(String name, String description, LocalDateTime beginEnrollmentDateTime, LocalDateTime closeEnrollmentDateTime, LocalDateTime beginEventDateTime, LocalDateTime endEventDateTime, String location, int basePrice, int maxPrice, int limitOfEnrollment, EventStatus eventStatus) {
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
        this.eventStatus = eventStatus;
    }

    public Event toEntity() {
        return Event.builder()
                .name(name)
                .description(description)
                .beginEnrollmentDateTime(beginEnrollmentDateTime)
                .closeEnrollmentDateTime(closeEnrollmentDateTime)
                .beginEventDateTime(beginEventDateTime)
                .endEventDateTime(endEventDateTime)
                .location(location)
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .limitOfEnrollment(limitOfEnrollment)
                .eventStatus(eventStatus)
                .build();
    }
}