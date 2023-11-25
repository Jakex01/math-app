package com.alibou.security.service;

import com.alibou.security.entity.EventEntity;
import com.alibou.security.dao.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;


    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventEntity> findAllEvents() {
        return eventRepository.findAll();
    }

    public void saveEvent(EventEntity eventEntity){
        var event = EventEntity.builder()
                .id(eventEntity.getId())
                .category(eventEntity.getCategory())
                .description(eventEntity.getDescription())
                .date(eventEntity.getDate())
                .studying_level(eventEntity.getStudying_level())
                .level(eventEntity.getLevel())
                .duration(eventEntity.getDuration())
                .price_full_access(eventEntity.getPrice_full_access())
                .price_one_time_access(eventEntity.getPrice_one_time_access())
                .url(eventEntity.getUrl())
                .lecturer(eventEntity.getLecturer())
                .build();

        eventRepository.save(event);

    }

    public Optional<List<EventEntity>> filterEvent(String level_param, String date) {

        LocalDate finalLocalDate = createLocalDate(date);


        return Optional.of(eventRepository
                .findAll()
                .stream()
                 .filter(event ->event.getDate().isAfter(LocalDate.now()))
                .filter(event -> level_param == null || event.getLevel().name().equals(level_param))
                .filter(event -> (event.getDate() != null && event.getDate().isEqual(finalLocalDate)))
                .collect(Collectors.toList()));
    }



    public LocalDate createLocalDate(String date) {
        return Optional.ofNullable(date)
                .filter(d -> !d.isEmpty())
                .map(d -> {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    try {
                        return LocalDate.parse(d, dtf);
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .orElse(null);
    }


}
