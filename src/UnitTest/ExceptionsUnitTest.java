package UnitTest;

import Model.AdultUserModel;
import Model.ChildUserModel;
import Model.UserModel;

import Exception.NoSuchAgeException;

import Model.YoungChildUserModel;
import Utils.Helper;
import org.junit.Before;
import org.junit.Test;


import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class ExceptionsUnitTest {
    UserModel adult, child, youngChild;

    @Before
    public void init() throws Exception {
        this.adult = new AdultUserModel("Adult", 32, "Lecturer", null, 'M', "NSW");
        Set<String> parents = new HashSet<>();
        parents.add("Parent1");
        parents.add("Parent2");
        this.child = new ChildUserModel("Child", 7, "student", null, 'F', "VIC", parents);
        this.youngChild = new YoungChildUserModel("YoungChild", 1, "student", null, 'F', "VIC", parents);
    }

    @Test
    public void testNoSuchAgeException() {
        try {
            new AdultUserModel("Jim Carter", 152, "Actor", null, 'M', "VIC");
            fail();
        }
        catch (Exception e){
            assertThat(e.getMessage(), is("Age cannot be greater than 150."));
        }
    }

    @Test
    public void testNoParentException(){
        Set<String> parents = new HashSet<>();
        try{
            new ChildUserModel("Child",4, "testing", null, 'F', "VIC", parents);
            fail();
        }
        catch (Exception e){
            assertThat(e.getMessage(), is("Child should have 2 parents."));
        }
    }

    @Test
    public void testNotToBeClassmatesException(){
        try {
            ((AdultUserModel) adult).addClassMate(child);
            fail();
        }
        catch (Exception e){
            assertThat(e.getMessage(), is("Adult cannot be classmate with a child."));
        }
    }

    @Test
    public void testNotToBeFriendsException(){
        try {
            ((AdultUserModel) adult).addFriend(child);
            fail();
        }
        catch (Exception e){
            assertThat(e.getMessage(), is("Adult cannot be friend with a child."));
        }
    }

    @Test
    public void testTooYoungException(){
        try {
            ((AdultUserModel) adult).addFriend(youngChild);
            fail();
        }
        catch (Exception e){
            assertThat(e.getMessage(), is("Cannot be friend with young child."));
        }
    }


}
