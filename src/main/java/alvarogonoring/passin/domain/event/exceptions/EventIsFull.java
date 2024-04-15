package alvarogonoring.passin.domain.event.exceptions;

public class EventIsFull extends RuntimeException {
    public EventIsFull(String message) {
        super(message);
    }
}
