package fr.camel.mareu2.di;

import fr.camel.mareu2.service.DummyMeetingApiService;
import fr.camel.mareu2.service.MeetingApiService;

/**
 * Created by Camel Benmoussa on 28/07/2021.
 */
public class DI {

    private static MeetingApiService service = new DummyMeetingApiService();

    /**
     * Get an instance on @{@link MeetingApiService}
     *
     * @return
     */
    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link MeetingApiService}. Useful for tests, so we ensure the context is clean.
     *
     * @return
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}
