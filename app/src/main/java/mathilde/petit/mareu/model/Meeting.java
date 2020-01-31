package mathilde.petit.mareu.model;

import java.util.Comparator;
import java.util.List;

/**
 * Model object representing a Meeting
 */
public class Meeting {

    /** Topic */
    private String topic;

    /** Hour */
    private String hour;

    /** Place */
    private String place;

    /** List of attendees */
    private String attendees;

    /**
     * Constructor
     * @param
     * @return
     */
    public Meeting(String topic, String hour, String place, String attendees) {
        this.topic = topic;
        this.hour = hour;
        this.place = place;
        this.attendees = attendees;
    }

    public String getTopic() {
        return topic;
    }

    public String getHour() {
        return hour;
    }

    public String getPlace() {
        return place;
    }

    public String getAttendees() {
        return attendees;
    }

    // A à Z
    public static class MeetingAZComparator implements Comparator<Meeting> {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return o1.topic.compareTo(o2.topic);
        }
    }

    // Z à A
    public static class MeetingZAComparator implements Comparator<Meeting> {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return o1.topic.compareTo(o2.topic);
        }
    }

    // Croissant
    public static class MeetingRecentComparator implements Comparator<Meeting> {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return 0;
        }
    }

    // Décroissant
    public static class MeetingOldComparator implements Comparator<Meeting> {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return 0;
        }
    }

    public String getMeetingFullName_toString() {
        String res =  getTopic() + " - ";
        res = res + getHour();
        res = res + " - ";
        res = res + getPlace();
        return res;
    }

}
