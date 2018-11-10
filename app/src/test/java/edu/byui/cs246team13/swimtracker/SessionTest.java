package edu.byui.cs246team13.swimtracker;

import org.junit.Test;

//import java.time.LocalDateTime;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class SessionTest {
    // test to make sure an individual session object is created correctly
    @Test
    public void sessionIsCreated() {
        Date today = new Date();
        Session testSession = new Session(today, 15.0, 10.0, 5000.0);

        assertEquals(testSession.get_date(), today);
        assertEquals(testSession.get_poolLength(), 15.0);
        assertEquals(testSession.get_numLaps(), 10.0);
        assertEquals(testSession.get_time(), 5000.0);
    }
}
