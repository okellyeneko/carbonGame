package application;

import java.util.*;

public class MapGraph {
    private final Map<Integer, List<Link>> adjLinks = new HashMap<>();

    public MapGraph() {
    }

    public void addLink(Link link) {
        // Since there may be multiple links between the same points,
        // we simply add the link without checking if an identical one already exists.
        adjLinks.computeIfAbsent(link.getStartPoint(), k -> new ArrayList<>()).add(link);
        // Optionally, for undirected graphs, you can also add the reverse link
    }

    public List<Link> getAdjLinks(int point) {
        return adjLinks.getOrDefault(point, Collections.emptyList());
    }
    
    public List<Link> getAllLinks() {
        List<Link> allLinks = new ArrayList<>();
        for (List<Link> links : adjLinks.values()) {
            allLinks.addAll(links);
        }
        return allLinks;
    }

 /*   // Updated to return a Route object
    public Route findRoute(int start, int end, String criteria) {
        Map<Integer, LinkInfo> bestLinks = new HashMap<>();
        PriorityQueue<LinkInfo> pq = new PriorityQueue<>(Comparator.comparingInt(info -> info.metrics.getOrDefault(criteria, Integer.MIN_VALUE)));
        pq.add(new LinkInfo(start, 0, 0, 0, null)); // Starting point

        while (!pq.isEmpty()) {
            LinkInfo currentInfo = pq.poll();
            int currentPoint = currentInfo.point;

            if (bestLinks.containsKey(currentPoint) && compareLinkInfo(bestLinks.get(currentPoint), currentInfo, criteria)) {
                continue;
            }

            bestLinks.put(currentPoint, currentInfo);

            if (currentPoint == end) {
                break; // Found the best route to the destination
            }

            for (Link adjLink : getAdjLinks(currentPoint)) {
                int newTime = currentInfo.time + adjLink.getTime();
                int newCost = currentInfo.cost + adjLink.getCost();
                int newCarbon = currentInfo.carbonFootprint + adjLink.getCarbonFootprint();

                if (!bestLinks.containsKey(adjLink.getEndPoint()) || !compareLinkInfo(bestLinks.get(adjLink.getEndPoint()), new LinkInfo(adjLink.getEndPoint(), newTime, newCost, newCarbon, currentInfo), criteria)) {
                    pq.add(new LinkInfo(adjLink.getEndPoint(), newTime, newCost, newCarbon, currentInfo));
                }
            }
        }

        // Reconstruct the route from the best links found
        Route route = new Route();
        LinkInfo endInfo = bestLinks.get(end);
        if (endInfo == null) return route; // No route found

        while (endInfo != null && endInfo.previous != null) {
            route.addLink(findLink(endInfo.previous.point, endInfo.point));
            endInfo = endInfo.previous;
        }

        Collections.reverse(route.getLinks()); // Ensure the links are in the correct order
        return route;
    }
*/
    
    public Route findRoute(int start, int end, String criteria) {
        Map<Integer, Route> bestRoutes = new HashMap<>();
        PriorityQueue<Route> pq = new PriorityQueue<>(Comparator.comparingInt(route -> getRouteMetric(route, criteria)));
        Route startRoute = new Route();
        pq.add(startRoute); // Add a dummy start route

        while (!pq.isEmpty()) {
            Route currentRoute = pq.poll();
            int currentPoint = currentRoute.getLinks().isEmpty() ? start : currentRoute.getLinks().get(currentRoute.getLinks().size() - 1).getEndPoint();

            // Early exit if reaching the end
            if (currentPoint == end) {
                return currentRoute;
            }

            if (bestRoutes.containsKey(currentPoint)) {
                continue; // Skip if we've already found a better route to this point
            }
            bestRoutes.put(currentPoint, currentRoute);

            for (Link adjLink : getAdjLinks(currentPoint)) {
                if (!currentRoute.getLinks().isEmpty() && adjLink.getStartPoint() != currentPoint) {
                    continue; // Skip if not a continuous link
                }

                Route newRoute = new Route();
                newRoute.getLinks().addAll(currentRoute.getLinks());
                newRoute.addLink(adjLink);

                pq.add(newRoute);
            }
        }

        return new Route(); // Return an empty route if no route found
    }

    private int getRouteMetric(Route route, String criteria) {
        switch (criteria) {
            case "cost":
                return route.getTotalCost();
            case "time":
                return route.getTotalTime();
            case "carbon":
                return route.getTotalCarbonFootprint();
            default:
                throw new IllegalArgumentException("Unknown criteria: " + criteria);
        }
    }
    
    
    private Link findLink(int start, int end) {
        // This method now needs to account for multiple links between two points
        // For simplicity, we return the first link found; you might want to adjust this
        return adjLinks.getOrDefault(start, Collections.emptyList()).stream()
                .filter(link -> link.getEndPoint() == end)
                .findFirst()
                .orElse(null);
    }

    private boolean compareLinkInfo(LinkInfo a, LinkInfo b, String criteria) {
        // Smaller values are considered better for all criteria
        return a.metrics.getOrDefault(criteria, Integer.MIN_VALUE) <= b.metrics.getOrDefault(criteria, Integer.MIN_VALUE);
    }

    static class LinkInfo {
        int point;
        int time;
        int cost;
        int carbonFootprint;
        LinkInfo previous; // For path reconstruction
        Map<String, Integer> metrics;

        LinkInfo(int point, int time, int cost, int carbonFootprint, LinkInfo previous) {
            this.point = point;
            this.time = time;
            this.cost = cost;
            this.carbonFootprint = carbonFootprint;
            this.previous = previous;
            this.metrics = new HashMap<>();
            metrics.put("time", time);
            metrics.put("cost", cost);
            metrics.put("carbon", carbonFootprint);
        }
    }

    public int getNumberOfPoints() {
        return adjLinks.keySet().size();
    }
}
