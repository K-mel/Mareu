package fr.camel.mareu2.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.camel.mareu2.model.Meeting;

/**
 * Created by Camel Benmoussa on 28/07/2021.
 */
public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeeting();

    @Override
    public List<Meeting> getMeeting() {
        return meetings;
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public boolean checkingMeeting(Meeting meeting) {
        List<Meeting> meetingAsTheSameDay = new ArrayList<>();

        Calendar calSelected = Calendar.getInstance();
        calSelected.setTime(meeting.getDateStart());

        for (Meeting mMeetingAsTheSameDay : meetings) {
            Calendar meetingCal = Calendar.getInstance();
            meetingCal.setTime(mMeetingAsTheSameDay.getDateStart());

            if (meetingCal.get(Calendar.DAY_OF_MONTH) == calSelected.get(Calendar.DAY_OF_MONTH))
                meetingAsTheSameDay.add(mMeetingAsTheSameDay);
        }
        if (meetingAsTheSameDay.size() == 0) return true;

        List<Meeting> meetingAsTheSameRoom = new ArrayList<>();
        for (Meeting mMeetingAsTheSameRoom : meetingAsTheSameDay) {
            if (meeting.getRoom().equals(mMeetingAsTheSameRoom.getRoom()))
                meetingAsTheSameRoom.add(mMeetingAsTheSameRoom);
        }

        for (Meeting mMeeting : meetingAsTheSameRoom) {

            if (meeting.getDateStart().before(mMeeting.getDateStart()) && meeting.getDateEnd().before(mMeeting.getDateStart()))
                return true;

            if (meeting.getDateStart().after(mMeeting.getDateEnd()) && meeting.getDateEnd().after(mMeeting.getDateEnd()))
                return true;
        }
        return false;
    }

    @Override
    public List<Meeting> getMeetingDateFilter(Date date) {
        List<Meeting> mMeetingFiltered = new ArrayList<>();

        Calendar calSelected = Calendar.getInstance();
        calSelected.setTime(date);

        for (Meeting meeting : meetings) {
            Calendar meetingCal = Calendar.getInstance();
            meetingCal.setTime(meeting.getDateStart());

            if (meetingCal.get(Calendar.DAY_OF_MONTH) == calSelected.get(Calendar.DAY_OF_MONTH) && meetingCal.get(Calendar.MONTH) == calSelected.get(Calendar.MONTH) && meetingCal.get(Calendar.YEAR) == calSelected.get(Calendar.YEAR))
                mMeetingFiltered.add(meeting);
        }
        return mMeetingFiltered;
    }

    @Override
    public List<Meeting> getMeetingRoomFilter(String room) {
        List<Meeting> mMeetingFiltered = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (meeting.getRoom().equals(room)) mMeetingFiltered.add(meeting);
        }
        return mMeetingFiltered;
    }

}
