package fr.camel.mareu2.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import fr.camel.mareu2.model.Meeting;
import fr.camel.mareu2.model.User;

import static android.graphics.Color.rgb;

/**
 * Created by Camel Benmoussa on 28/07/2021.
 */
public class DummyMeetingGenerator {

    private static int actualColor;

    public static int getActualColor() {
        return actualColor;
    }

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(generateColor(), "Salle A", generateStartMeeting(), generateEndMeeting(), "Sujet 1", User.listParticipants),
            new Meeting(generateColor(), "Salle B", generateStartMeeting(), generateEndMeeting(), "Sujet 2", User.listParticipants),
            new Meeting(generateColor(), "Salle C", generateStartMeeting(), generateEndMeeting(), "Sujet 3", User.listParticipants),
            new Meeting(generateColor(), "Salle D", generateStartMeeting(), generateEndMeeting(), "Sujet 4", User.listParticipants)
    );

    static List<Meeting> generateMeeting() {
        return new ArrayList<>(DUMMY_MEETING);
    }

    public static int generateColor() {
        actualColor = rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        return actualColor;
    }

    private static Date generateStartMeeting() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }

    private static Date generateEndMeeting() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }
}
