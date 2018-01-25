package simonemarzulli.intercom;

import java.io.File;
import java.io.FileNotFoundException;

import simonemarzulli.intercom.bean.Customer;
import simonemarzulli.intercom.exceptions.InputFormatException;

public class App {

    private static final String FILE_NAME = "customers.json";

    public static void main(String[] args) {

        File file = new File(FILE_NAME);

        try {
            PartyPlanner planner = new PartyPlanner(file);

            for (Customer customer : planner.getInvitedCustomers()) {
                System.out.println(customer.getUserId() + " " + customer.getName());
            }
        } catch (InputFormatException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println("Please provide an existing json file containing the customers!");
            System.exit(1);
        }

    }
}
