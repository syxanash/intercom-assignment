package simonemarzulli.intercom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import simonemarzulli.intercom.bean.Customer;
import simonemarzulli.intercom.exceptions.InputFormatException;

/**
 * Class used for reading the list of customers from a JSON file and returning
 * the list of customers within "n" kilometers which will be invited to the
 * party.
 * 
 * @author Simone
 *
 */
public class PartyPlanner {
    private List<Customer> allCustomers;

    private final double minDistance;
    private final double officeLatitude;
    private final double officeLongitude;

    // each degree on a great circle of Earth is 110.57 kilometers
    private static final double DEGREE_CIRCLE_EARTH = 110.57;

    /**
     * Constructor of the class which gets in input the filename containing the
     * customers list in a JSON format.
     * 
     * @param filename
     *            containing the JSON list of customers
     * @throws FileNotFoundException
     * @throws InputFormatException
     */
    public PartyPlanner(File filename, double minDistance, double officeLatitude, double officeLongitude)
            throws FileNotFoundException, InputFormatException {
        Scanner sc = null;

        allCustomers = new ArrayList<Customer>();

        this.minDistance = minDistance;
        this.officeLatitude = officeLatitude;
        this.officeLongitude = officeLongitude;

        try {
            sc = new Scanner(filename);

            // for each line in file store the content as Customer object
            while (sc.hasNextLine()) {
                String record = sc.nextLine();

                JSONObject obj = new JSONObject(record);

                double tempLatitude = Double.parseDouble(obj.getString("latitude"));
                double tempLongitude = Double.parseDouble(obj.getString("longitude"));

                String tempName = obj.getString("name");

                int tempUserId = obj.getInt("user_id");

                allCustomers.add(new Customer(tempUserId, tempName, tempLatitude, tempLongitude));
            }
        } catch (JSONException e) {
            throw new InputFormatException(e.getMessage());
        } finally {
            if (sc != null)
                sc.close();
        }
    }

    /**
     * Method for calculating the great circle distance in kilometers given the
     * coordinates of the two locations. The coordinates in input must be in
     * degrees.
     * 
     * @see <a href=
     *      "https://introcs.cs.princeton.edu/java/12types/GreatCircle.java.html">https://introcs.cs.princeton.edu/java/12types/GreatCircle.java.html</a>
     * @param x1
     *            latitude first location
     * @param y1
     *            longitude first location
     * @param x2
     *            latitude second location
     * @param y2
     *            longitdude second location
     * @return great circle distance in kilometers of the two location
     */

    public static double getGreatCircleDistance(double x1, double y1, double x2, double y2) {
        double distance = 0;

        // great circle distance in radians

        double latx1Rad = Math.toRadians(x1);
        double longx1Rad = Math.toRadians(y1);
        double latx2Rad = Math.toRadians(x2);
        double longx2Rad = Math.toRadians(y2);

        double angle = Math.acos(Math.sin(latx1Rad) * Math.sin(latx2Rad)
                + Math.cos(latx1Rad) * Math.cos(latx2Rad) * Math.cos(longx1Rad - longx2Rad));

        // convert back to degrees
        angle = Math.toDegrees(angle);

        distance = (DEGREE_CIRCLE_EARTH * angle);

        return distance;
    }

    /**
     * Method which checks for every customer in list, the ones within n
     * kilometers of distance from the office and returns them in a list of
     * customers.
     * 
     * @return a list of customers within a certain distance in kilometers from
     *         the office
     */

    public List<Customer> getInvitedCustomers() {
        List<Customer> invitedCustomers = new ArrayList<Customer>();

        // for each customer get the great circle distance in kilometers and
        // save only the customers within "n" kilometers of distance form the
        // office
        for (Customer customer : allCustomers) {
            double distance = getGreatCircleDistance(customer.getLatitude(), customer.getLongitude(), officeLatitude,
                    officeLongitude);

            if (distance <= minDistance) {
                invitedCustomers.add(customer);
            }
        }

        // sort customers in ascending order based on the user_id field
        Collections.sort(invitedCustomers);

        return invitedCustomers;
    }
}
