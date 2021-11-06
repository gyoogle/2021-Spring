package com.rest.api.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Spring REST API")
                .description("REST API development")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        //given
        String name = "Event";
        String description = "String REST API";

        //when
        Event event = new Event();
        event.saveNameAndDescription(name, description);

        //then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);

    }
}