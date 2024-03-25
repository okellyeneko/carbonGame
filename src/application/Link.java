package application;

import java.util.Objects;

public class Link {
    int startPoint;
    int endPoint;
    Transport transport;
    int time; // Time in minutes
    int cost; // Cost in arbitrary units
    int carbonFootprint; // Carbon footprint in arbitrary units

    public Link(int startPoint, int endPoint, Transport transport, int time, int cost, int carbonFootprint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.transport = transport;
        this.time = time;
        this.cost = cost;
        this.carbonFootprint = carbonFootprint;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Link link = (Link) obj;
        return startPoint == link.startPoint &&
               endPoint == link.endPoint &&
               transport == link.transport; // Assuming an enum for transport
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPoint, endPoint, transport);
    }

    // Getters for each field
    public int getStartPoint() {
        return startPoint;
    }

    public int getEndPoint() {
        return endPoint;
    }

    public Transport getTransport() {
        return transport;
    }

    public int getTime() {
        return time;
    }

    public int getCost() {
        return cost;
    }

    public int getCarbonFootprint() {
        return carbonFootprint;
    }
}
