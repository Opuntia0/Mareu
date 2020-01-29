package mathilde.petit.mareu;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import mathilde.petit.mareu.di.DI;
import mathilde.petit.mareu.model.Meeting;
import mathilde.petit.mareu.service.DummyMeetingGenerator;
import mathilde.petit.mareu.service.MeetingApiService;

import static org.junit.Assert.*;

/**
 * Unit test on Meeting service
 */
public class MeetingServiceUnitTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    // TODO vérif tests
    // Constructor
    @Test
    public void createAndGetMeetingWithSuccess() {
        Meeting m = new Meeting("Reunion", "13h30","Peach", "marc@lamoze.com");
        assertEquals("Reunion", m.getTopic());
        assertEquals("Peach", m.getPlace());
        assertEquals("marc@lamoze.com", m.getAttendees());
        assertEquals("13h30", m.getHour());
    }

    // Get meeting with success
    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertArrayEquals(meetings.toArray(), expectedMeetings.toArray());
    }

    // Delete meeting with success
    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }
}