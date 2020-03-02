package mathilde.petit.mareu;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
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

    private static int ITEMS_COUNT = 4;

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

    // Sort A -> Z
    @Test
    public void sortAZWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        Collections.sort(meetings, new Meeting.MeetingAZComparator());
        String m1 = "Réunion A";
        assertEquals(meetings.get(0).getTopic(), m1);

        String m2 = "Réunion B";
        assertEquals(meetings.get(1).getTopic(), m2);

        String m3 = "Réunion C";
        assertEquals(meetings.get(2).getTopic(), m3);

        String m4 = "Réunion D";
        assertEquals(meetings.get(3).getTopic(), m4);
    }

    // Sort Z -> A
    @Test
    public void sortZAWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        Collections.sort(meetings, new Meeting.MeetingZAComparator());
        String m1 = "Réunion D";
        assertEquals(meetings.get(0).getTopic(), m1);

        String m2 = "Réunion C";
        assertEquals(meetings.get(1).getTopic(), m2);

        String m3 = "Réunion B";
        assertEquals(meetings.get(2).getTopic(), m3);

        String m4 = "Réunion A";
        assertEquals(meetings.get(3).getTopic(), m4);
    }

    // Sort 00H00 -> 23H59
    @Test
    public void sortAscendingDateWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        Collections.sort(meetings, new Meeting.MeetingOldComparator());
        String m1 = "14h00";
        assertEquals(meetings.get(0).getHour(), m1);

        String m2 = "16h00";
        assertEquals(meetings.get(1).getHour(), m2);

        String m3 = "19h00";
        assertEquals(meetings.get(2).getHour(), m3);

        String m4 = "19h30";
        assertEquals(meetings.get(3).getHour(), m4);
    }

    // Sort 23H59 -> 00H00
    @Test
    public void sortDescendingDateWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        Collections.sort(meetings, new Meeting.MeetingRecentComparator());
        String m1 = "14h00";
        assertEquals(meetings.get(3).getHour(), m1);

        String m2 = "16h00";
        assertEquals(meetings.get(2).getHour(), m2);

        String m3 = "19h00";
        assertEquals(meetings.get(1).getHour(), m3);

        String m4 = "19h30";
        assertEquals(meetings.get(0).getHour(), m4);
    }

    // add meeting -> update service 4 elem, add meeting, 5 elem
    @Test
    public void addMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        Meeting m = new Meeting("Reunion", "13h30","Peach", "marc@lamoze.com");
        meetings.add(m);
        assertEquals(meetings.size(), ITEMS_COUNT+1);
    }
}