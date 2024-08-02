package org.ustsinau.chapter2_4.service.impl;

import org.ustsinau.chapter2_4.models.Event;
import org.ustsinau.chapter2_4.repository.EventRepository;
import org.ustsinau.chapter2_4.repository.impl.EventRepositoryImpl;
import org.ustsinau.chapter2_4.service.EventService;

import java.util.List;

public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl() {
        this.eventRepository = new EventRepositoryImpl();
    }

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getById(Integer id) {
        return eventRepository.getById(id);
    }

    @Override
    public Event create(Event event) {

        return eventRepository.create(event);
    }

    @Override
    public Event update(Event event) {
        return eventRepository.update(event);
    }

    @Override
    public void deleteById(Integer id) {
        eventRepository.delete(id);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.getAll();
    }
}
