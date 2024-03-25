package application;

public class Point {
    private int pointId; // Unique identifier for each point
    private String name; // Human-readable name of the point
    private double latitude; // Latitude part of the coordinate
    private double longitude; // Longitude part of the coordinate

    // Constructor to initialize the Points object
    public Point(int pointId, String name,  double longitude, double latitude) {
        this.pointId = pointId;
        this.name = name;
        this.latitude = 5-latitude;
        this.longitude = longitude;
    } 

    // Getter for pointId
    public int getPointId() {
        return pointId;
    }

    // Setter for pointId
    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for latitude
    public double getLatitude() {
        return latitude;
    }

    // Setter for latitude
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // Getter for longitude
    public double getLongitude() {
        return longitude;
    }

    // Setter for longitude
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Method to return the coordinate as a string
    public String getCoordinates() {
        return String.format("(%f, %f)", latitude, longitude);
    }

    // Additional methods can be added as needed
}
