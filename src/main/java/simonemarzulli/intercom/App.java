package simonemarzulli.intercom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import simonemarzulli.intercom.bean.Customer;
import simonemarzulli.intercom.bean.Office;

public class App {

    private static final String FILE_NAME = "customers.json";
    
    // each degree on a great circle of Earth is 110.57 kilometers
    private static final double DEGREE_CIRCLE_EARTH = 110.57;

    public static void main(String[] args) throws IOException {

        File file = new File(FILE_NAME);
        Scanner sc = null;

        List<Customer> allCustomers = new ArrayList<Customer>();
        Office dublinOffice = new Office("Dublin", 53.339428, -6.257664);

        try {
            sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String record = sc.nextLine();

                JSONObject obj = new JSONObject(record);

                double tempLatitude = Double.parseDouble(obj.getString("latitude"));
                double tempLongitude = Double.parseDouble(obj.getString("longitude"));

                String tempName = obj.getString("name");

                int tempUserId = obj.getInt("user_id");

                allCustomers.add(new Customer(tempUserId, tempName, tempLatitude, tempLongitude));
            }
        } finally {
            if (sc != null)
                sc.close();
        }
        
        List<Customer> invitedCustomers = new ArrayList<Customer>();

        for (Customer customer : allCustomers) {
            // great circle distance in radians

            double custLat = Math.toRadians(customer.getLatitude());
            double custLong = Math.toRadians(customer.getLongitude());
            double officeLat = Math.toRadians(dublinOffice.getLatitude());
            double officeLong = Math.toRadians(dublinOffice.getLongitude());

            double angle = Math.acos(Math.sin(custLat) * Math.sin(officeLat)
                    + Math.cos(custLat) * Math.cos(officeLat) * Math.cos(custLong - officeLong));

            // convert back to degrees
            angle = Math.toDegrees(angle);

            double distance = (DEGREE_CIRCLE_EARTH * angle);

            System.out.println(customer.getUserId() + " " + distance + " in km");
            
            if (distance <= 100.0) {
                invitedCustomers.add(customer);
            }
        }
        
        System.out.println("customers coming");
        
        Collections.sort(invitedCustomers);
        
        for (Customer customer : invitedCustomers) {
            System.out.println(customer.getUserId());
        }
        
    }
}
