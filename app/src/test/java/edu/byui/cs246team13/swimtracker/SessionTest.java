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

        assertEquals(testSession.getDate(), today);
        assertEquals(testSession.getPoolLength(), 15.0);
        assertEquals(testSession.getNumLaps(), 10.0);
        assertEquals(testSession.getTime(), 5000.0);
    }
}
