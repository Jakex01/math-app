package com.alibou.security.controller;


import com.alibou.security.entity.EventEntity;
import com.alibou.security.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/filter")
    public Optional<List<EventEntity>> filteredEvents(
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String date){

return eventService.filterEvent(level,date);
    }
    @GetMapping("/all")
    public ResponseEntity<List<EventEntity>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAllEvents());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestBody @Valid EventEntity event
    ){
        eventService.saveEvent(event);

        return ResponseEntity.accepted().build();
    }

}

