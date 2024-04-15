package alvarogonoring.passin.services;

import alvarogonoring.passin.domain.attendee.Attendee;
import alvarogonoring.passin.domain.event.Event;
import alvarogonoring.passin.domain.event.exceptions.EventIsFull;
import alvarogonoring.passin.domain.event.exceptions.EventNotFound;
import alvarogonoring.passin.dto.attendee.AttendeeIdDTO;
import alvarogonoring.passin.dto.attendee.AttendeeRequestDTO;
import alvarogonoring.passin.dto.event.EventIdDTO;
import alvarogonoring.passin.dto.event.EventRequestDTO;
import alvarogonoring.passin.dto.event.EventResponseDTO;
import alvarogonoring.passin.repositories.AttendeeRepository;
import alvarogonoring.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId) {
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO) {
        Event event = new Event();
        event.setTitle(eventDTO.title());
        event.setDetails(eventDTO.details());
        event.setMaximumAttendees(eventDTO.maximumAttendees());
        event.setSlug(this.createSlug(eventDTO.title()));

        this.eventRepository.save(event);

        return new EventIdDTO(event.getId());
    }

    private Event getEventById(String eventId) {
        return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFound("Event not found with id: " + eventId));
    }

    private String createSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);

        return normalized.replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);

        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size()) throw new EventIsFull("Event is full");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());

        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }
}
