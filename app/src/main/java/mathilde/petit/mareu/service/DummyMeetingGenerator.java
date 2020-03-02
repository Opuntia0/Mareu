package mathilde.petit.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mathilde.petit.mareu.model.Meeting;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("Réunion A", "14h00", "Peach",
                    "maxence@lamoze.com, alex@lamoze.com, mathilde@lamoze.com"),
            new Meeting("Réunion B", "16h00", "Mario",
                    "paul@lamoze.com, viviane@lamoze.com\",\n" +
                            "            \"jean@lamoze.com"),
            new Meeting("Réunion C", "19h30", "Luigi",
                    "amandine@lamoze.com, luc@lamoze.com, robert@lamoze.com"),
            new Meeting("Réunion D", "19h00", "Luigi",
                    "amandine@lamoze.com")
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
