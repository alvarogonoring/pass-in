package alvarogonoring.passin.domain.checkin.exceptions;

public class CheckInAlreadyExists extends RuntimeException {
    public CheckInAlreadyExists(String message) {
        super(message);
    }
}
