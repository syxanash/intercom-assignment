package simonemarzulli.intercom;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import simonemarzulli.intercom.bean.Customer;
import simonemarzulli.intercom.exceptions.InputFormatException;

public class PartyPlannerTest {
    private static final String TEST_PATH = "testcases/";

    /*
     * This unit test is used to verify that giving a bad file name a
     * FileNotFoundException should be thrown by the class
     */
    @Test(expected = java.io.FileNotFoundException.class)
    public void testWrongFilename() throws Exception {
        new PartyPlanner(new File("test.json"));
    }

    /*
     * When given a bad JSON file content the class should return an
     * InputFormatException
     */
    @Test(expected = InputFormatException.class)
    public void testBadFileContent() throws Exception {
        new PartyPlanner(new File(TEST_PATH + "customers.json"));
    }

    /*
     * Inside the file there's one record containing a location within 100km
     * from the dublin office this location will be contained in the invited
     * customers list
     */
    @Test
    public void testInRange() throws Exception {
        PartyPlanner planner = new PartyPlanner(new File(TEST_PATH + "customers_in.json"));
        List<Customer> customers = planner.getInvitedCustomers();

        assertEquals(customers.get(0).getUserId(), 1);
    }

    /*
     * The file contains a customer located outside of the 100km range thus the
     * list of customers will be empty
     */
    @Test
    public void testOutOfRange() throws Exception {
        PartyPlanner planner = new PartyPlanner(new File(TEST_PATH + "customers_out.json"));
        List<Customer> customers = planner.getInvitedCustomers();

        assertEquals(customers.size(), 0);
    }
    
    /*
     * Testing a location with coordinates equal to the Dublin office location
     */
    @Test
    public void testInOfficeCustomers() throws Exception {
        PartyPlanner planner = new PartyPlanner(new File(TEST_PATH + "customers_inoffice.json"));
        List<Customer> customers = planner.getInvitedCustomers();
        
        assertEquals(customers.get(0).getUserId(), 1);
    }

}
