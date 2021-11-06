package com.rest.api.events;

import com.rest.api.events.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {

    private final EventRepository eventRepository;

    @PostMapping
    public ResponseEntity createEvent(@RequestBody EventDto eventDto) {
        Event event = eventDto.toEntity();
        Event newEvent = eventRepository.save(event);
        URI createURI = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        return ResponseEntity.created(createURI).body(newEvent);
    }

}