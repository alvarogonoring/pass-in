package alvarogonoring.passin.config;

import alvarogonoring.passin.domain.attendee.exceptions.AttendeeAlreadyRegistered;
import alvarogonoring.passin.domain.attendee.exceptions.AttendeeNotFound;
import alvarogonoring.passin.domain.checkin.exceptions.CheckInAlreadyExists;
import alvarogonoring.passin.domain.event.exceptions.EventIsFull;
import alvarogonoring.passin.domain.event.exceptions.EventNotFound;
import alvarogonoring.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFound.class)
    public ResponseEntity handleEventNotFound(EventNotFound eventNotFound) {

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFound.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFound attendeeNotFound) {

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyRegistered.class)
    public ResponseEntity handleAttendeeAlreadyRegistered(AttendeeAlreadyRegistered attendeeAlreadyRegistered) {

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExists.class)
    public ResponseEntity handleCheckInAlreadyExists(CheckInAlreadyExists checkInAlreadyExists) {

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(EventIsFull.class)
    public ResponseEntity<ErrorResponseDTO> handleEventIsFull(EventIsFull eventIsFull) {

        return ResponseEntity.badRequest().body(new ErrorResponseDTO(eventIsFull.getMessage()));
    }
}
