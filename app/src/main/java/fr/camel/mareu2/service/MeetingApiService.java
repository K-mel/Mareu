package fr.camel.mareu2.service;

import java.util.Date;
import java.util.List;

import fr.camel.mareu2.model.Meeting;

/**
 * Created by Camel Benmoussa on 28/07/2021.
 */
public interface MeetingApiService {

    List<Meeting> getMeeting();

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    boolean checkingMeeting(Meeting meeting);

    List<Meeting> getMeetingDateFilter(Date date);

    List<Meeting> getMeetingRoomFilter(String room);

}
