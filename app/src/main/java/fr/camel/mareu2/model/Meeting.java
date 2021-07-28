package fr.camel.mareu2.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Camel Benmoussa on 28/07/2021.
 */
public class Meeting {

    private int color;

    private String room;

    private Date dateStart;

    private Date dateEnd;

    public Date getDateEnd() {
        return dateEnd;
    }

    private String subject;

    private List<String> participantsList;

    public Date getDateStart() {
        return dateStart;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getRoom() {
        return room;
    }

    private String getSubject() {
        return subject;
    }

    public String getParticipantsList() {
        StringBuilder participants = new StringBuilder();
        for (String participant : participantsList) {
            participants.append(participant).append(", ");
        }
        return participants.toString();
    }

    public String getInfo() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        return this.getRoom() + " - " + dateFormat.format(dateStart).replace(':', 'h') + " - " + this.getSubject();
    }

    public Meeting(int color, String room, Date dateStart, Date dateEnd, String subject, List<String> participantsList) {
        this.color = color;
        this.room = room;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.subject = subject;
        this.participantsList = participantsList;
    }
}
