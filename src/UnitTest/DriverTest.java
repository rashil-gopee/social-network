package UnitTest;

import Controller.Driver;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class DriverTest {
    Driver driver;

    @Before
    public void init(){
        driver = new Driver();
        driver.init();
    }

    @Test
    public void testUsersAmount(){
        assertTrue(Driver.USERS.size() > 0);
    }
}
