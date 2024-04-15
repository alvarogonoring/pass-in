package alvarogonoring.passin.services;

import alvarogonoring.passin.domain.attendee.Attendee;
import alvarogonoring.passin.domain.checkin.CheckIn;
import alvarogonoring.passin.domain.checkin.exceptions.CheckInAlreadyExists;
import alvarogonoring.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        this.verifyCheckInExists(attendee.getId());

        CheckIn checkIn = new CheckIn();

        checkIn.setAttendee(attendee);
        checkIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepository.save(checkIn);
    }

    private void verifyCheckInExists(String attendeeId) {
        Optional<CheckIn> isCheckedIn = this.getCheckIn(attendeeId);

        if (isCheckedIn.isPresent()) throw new CheckInAlreadyExists("Attendee already checked-in");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
}
