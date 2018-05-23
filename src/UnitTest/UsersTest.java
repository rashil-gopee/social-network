package UnitTest;

import Model.AdultUserModel;
import Model.UserModel;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class UsersTest {
    UserModel mowgli;
    UserModel tarzan;
    UserModel jane;

    @Before
    public void init() {
        try {
            this.tarzan = new AdultUserModel("Tarzan", 21, "Actor", null, 'M', "VIC");
            this.jane = new AdultUserModel("Jane", 18, "Scientist", null, 'F', "NSW");
            this.mowgli = new AdultUserModel("Mowgli", 19, "Wolf", null, 'M', "SA");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testAdd() {
        String str = "Junit is working fine";
        assertEquals("Junit is working fine",str);
    }

    @Test
    public void testAddSpouse() throws Exception{
        ((AdultUserModel)tarzan).addSpouse(this.jane);
        assertFalse(((AdultUserModel) tarzan).verifyIfSingle());
    }

    @Test
    public void testIfConnection() {
        assertFalse(mowgli.verifyConnection("jane"));
    }

}
