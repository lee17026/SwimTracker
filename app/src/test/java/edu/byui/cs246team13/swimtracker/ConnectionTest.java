package edu.byui.cs246team13.swimtracker;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ConnectionTest {
    @Test
    public void connectionIsSet() {
        // test if the device is connected to the internet
        assertTrue("FAILURE - Device not connected.", testInternetConnection());
    }

    public boolean testInternetConnection() {
        return false;
    }

    @Test
    public void dataIsTransferred() {
        // tests to make sure the row was inserted into the database
        assertTrue("FAILURE - Row not inserted.", false);
    }
}
