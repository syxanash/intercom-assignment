package simonemarzulli.intercom;

import java.io.File;
import java.io.FileNotFoundException;

import simonemarzulli.intercom.bean.Customer;
import simonemarzulli.intercom.exceptions.InputFormatException;

public class App {

    private static final String FILE_NAME = "customers.json";

    public static void main(String[] args) {
        try {
            PartyPlanner planner = new PartyPlanner(new File(FILE_NAME));

            for (Customer customer : planner.getInvitedCustomers()) {
                System.out.println(customer.getUserId() + "\t" + customer.getName());
            }
        } catch (InputFormatException e) {
            System.err.println("Error reading file content:\n\t" + e.getMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println("Please provide an existing JSON file containing the customers!");
            System.exit(1);
        }

    }
}
