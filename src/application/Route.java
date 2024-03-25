package application;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<Link> links; // Stores all links that form this route

    // Constructor to initialize the Route with an empty list of links
    public Route() {
        this.links = new ArrayList<>();
    }

    // Adds a link to the route
    public void addLink(Link link) {
        links.add(link);
    }

    // Getter for links
    public List<Link> getLinks() {
        return links;
    }

    // Calculates and returns the total time of all links in the route
    public int getTotalTime() {
        int totalTime = 0;
        for (Link link : links) {
            totalTime += link.getTime();
        }
        return totalTime;
    }

    // Calculates and returns the total cost of all links in the route
    public int getTotalCost() {
        int totalCost = 0;
        for (Link link : links) {
            totalCost += link.getCost();
        }
        return totalCost;
    }

    // Calculates and returns the total carbon footprint of all links in the route
    public int getTotalCarbonFootprint() {
        int totalCarbonFootprint = 0;
        for (Link link : links) {
            totalCarbonFootprint += link.getCarbonFootprint();
        }
        return totalCarbonFootprint;
    }
    
    public boolean containsLink(Link link) {
        for (Link existingLink : links) {
            
            if (existingLink.equals(link)) {
                return true;
            }
            
            
        }
        return false;
    }

    // Additional methods as needed, such as removing a link, getting the start/end points of the route, etc.
}
