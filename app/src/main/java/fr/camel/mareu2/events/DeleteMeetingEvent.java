package fr.camel.mareu2.events;

import fr.camel.mareu2.model.Meeting;

/**
 * Created by Camel Benmoussa on 28/07/2021.
 */
public class DeleteMeetingEvent {

    /**
     * Neighbour to delete
     */
    public Meeting meeting;

    /**
     * Constructor.
     *
     * @param meeting
     */
    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
