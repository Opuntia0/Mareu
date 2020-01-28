package mathilde.petit.mareu.service;

import java.util.List;

import mathilde.petit.mareu.model.Meeting;

/**
 * Meeting API client
 */
public interface MeetingApiService {

    /**
     * Get all my Meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Deletes a meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);
}
