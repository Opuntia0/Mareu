package mathilde.petit.mareu.model;

import java.util.Comparator;
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
            return o1.topic.toLowerCase().compareTo(o2.topic.toLowerCase());
        }
    }

    // Z à A
    public static class MeetingZAComparator implements Comparator<Meeting> {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return o2.topic.toLowerCase().compareTo(o1.topic.toLowerCase());
        }
    }

    // Croissant
    public static class MeetingRecentComparator implements Comparator<Meeting> {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return (o2.getHours()*60 + o2.getMinutes()) - (o1.getHours()*60 + o1.getMinutes());
        }
    }

    // Décroissant
    public static class MeetingOldComparator implements Comparator<Meeting> {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return (o1.getHours()*60 + o1.getMinutes()) - (o2.getHours()*60 + o2.getMinutes());
        }
    }

    public int getHours(){
        String[] data = this.hour.split("h");
        return Integer.parseInt(data[0]);
    }

    public int getMinutes(){
        String[] data = this.hour.split("h");
        return Integer.parseInt(data[1]);
    }

    public String getMeetingFullName_toString() {
        String res =  getTopic() + " - ";
        res = res + getHour();
        res = res + " - ";
        res = res + getPlace();
        return res;
    }

}
