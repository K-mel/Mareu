package fr.camel.mareu2;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import fr.camel.mareu2.model.Meeting;
import fr.camel.mareu2.service.DummyMeetingGenerator;
import fr.camel.mareu2.service.MeetingApiService;

import static android.graphics.Color.rgb;
import static org.junit.Assert.*;
import fr.camel.mareu2.di.DI;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingServiceTest {
    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meeting = service.getMeeting();
        List<Meeting> expectedMeeting = DummyMeetingGenerator.DUMMY_MEETING;
        assertThat(meeting, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedMeeting.toArray())));
    }

    @Test
    public void createMeetingWithSuccess() {
        Date dateStart = new Date();
        Date dateEnd = new Date();

        List<String> list = new ArrayList<>(Arrays.asList("1@1.1", "2@2.2"));
        Meeting test = new Meeting(rgb(100, 150, 200), "Salle A", dateStart, dateEnd, "test", list);
        service.createMeeting(test);
        assertTrue(service.getMeeting().contains(test));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeeting().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeeting().contains(meetingToDelete));
    }

    @Test
    public void checkingMeetingTest() {
        //region meetingTrue
        Date dateStartTrue = new Date();
        Date dateEndTrue = new Date();

        List<String> listTrue = new ArrayList<>(Arrays.asList("1@1.1", "2@2.2"));
        Meeting testTrue = new Meeting(rgb(100, 150, 200), "Salle A", dateStartTrue, dateEndTrue, "test", listTrue);
        assertTrue(service.checkingMeeting(testTrue));

        //endregion

        //region meetingFalse
        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.HOUR_OF_DAY, 8);
        calStart.set(Calendar.MINUTE, 0);

        Calendar calEnd = Calendar.getInstance();
        calEnd.set(Calendar.HOUR_OF_DAY, 9);
        calEnd.set(Calendar.MINUTE, 0);

        List<String> listFalse = new ArrayList<>(Arrays.asList("1@1.1", "2@2.2"));
        Meeting testFalse = new Meeting(rgb(100, 150, 200), "Salle A", calStart.getTime(), calEnd.getTime(), "test", listFalse);
        assertFalse(service.checkingMeeting(testFalse));
        //endregion
    }

    @Test
    public void checkingDateFilter() {
        List<String> list = new ArrayList<>(Arrays.asList("1@1.1", "2@2.2"));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        Meeting test = new Meeting(rgb(100, 150, 200), "Salle A", cal.getTime(), cal.getTime(), "test", list);
        service.createMeeting(test);

        assertTrue(service.getMeetingDateFilter(cal.getTime()).contains(test));

    }

    @Test
    public void checkingRoomFilter() {
        List<String> list = new ArrayList<>(Arrays.asList("1@1.1", "2@2.2"));
        Calendar cal = Calendar.getInstance();
        Meeting test = new Meeting(rgb(100, 150, 200), "Salle A", cal.getTime(), cal.getTime(), "test", list);
        service.createMeeting(test);

        assertEquals(2, service.getMeetingRoomFilter("Salle A").size());

    }
}