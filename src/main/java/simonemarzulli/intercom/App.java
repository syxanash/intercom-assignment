package simonemarzulli.intercom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import simonemarzulli.intercom.bean.Customer;
import simonemarzulli.intercom.bean.Office;

public class App {

    private static final String FILE_NAME = "customers.json";

    public static void main(String[] args) throws IOException {

        File file = new File(FILE_NAME);
        Scanner sc = null;

        List<Customer> customers = new ArrayList<Customer>();
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

                customers.add(new Customer(tempUserId, tempName, tempLatitude, tempLongitude));
            }
        } finally {
            if (sc != null)
                sc.close();
        }

        for (Customer customer : customers) {
            // great circle distance in radians

            double custLat = Math.toRadians(customer.getLatitude());
            double custLong = Math.toRadians(customer.getLongitude());
            double officeLat = Math.toRadians(dublinOffice.getLatitude());
            double officeLong = Math.toRadians(dublinOffice.getLongitude());

            double angle = Math.acos(Math.sin(custLat) * Math.sin(officeLat)
                    + Math.cos(custLat) * Math.cos(officeLat) * Math.cos(custLong - officeLong));

            // convert back to degrees
            angle = Math.toDegrees(angle);

            // each degree on a great circle of Earth is 110.57 kilometers
            double distance = (110.57 * angle);

            System.out.println(distance + " in km");
        }
    }
}
