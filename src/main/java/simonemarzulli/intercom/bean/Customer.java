package simonemarzulli.intercom.bean;

public class Customer implements Comparable<Customer> {
    private int userId;
    private String name;
    private double latitude;
    private double longitude;

    public Customer(int userId, String name, double latitude, double longitude) {
        this.userId = userId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int compareTo(Customer o) {
        if (this.getUserId() > o.getUserId()) {
            return 1;
        } else if (this.getUserId() < o.getLatitude()) {
            return -1;
        } else {
            return 0;
        }
    }

}
