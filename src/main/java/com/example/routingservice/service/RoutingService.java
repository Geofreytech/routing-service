package com.example.routingservice.service;
import com.example.routingservice.exception.NoRouteFoundException;
import com.example.routingservice.loader.CountryLoader;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class RoutingService {

    private final Map<String, List<String>> graph;

    // ... (Constructor remains the same)
    public RoutingService(CountryLoader loader) {
        this.graph = loader.getAdjacencyMap();
    }

    // New version throws an exception instead of returning null
    public List<String> findLandRoute(String origin, String destination) {

        // 1. Validate input (Note: This could also be a different exception/error)
        if (!graph.containsKey(origin) || !graph.containsKey(destination)) {
            // Throw a specific error for invalid country codes
            throw new NoRouteFoundException("Invalid country code(s) provided.");
        }

        // 2. If origin == destination
        if (origin.equals(destination)) {
            return Collections.singletonList(origin);
        }

        // BFS structures
        Queue<String> queue = new LinkedList<>();
        // Use a map to store the predecessor for path reconstruction
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        // Start BFS
        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            // Check if destination's neighbor list is reachable
            // We check for the destination **before** looping neighbors to ensure the path is accurate
            for (String neighbor : graph.getOrDefault(current, Collections.emptyList())) {

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);

                    // Destination found
                    if (neighbor.equals(destination)) {
                        return buildPath(parent, origin, destination);
                    }
                }
            }
        }

        // 3. No route found -> Throw HTTP 400
        throw new NoRouteFoundException(
                "No land crossing found between " + origin + " and " + destination
        );
    }

    // ... (buildPath method remains the same)
    private List<String> buildPath(Map<String, String> parent, String origin, String destination) {
        // ... (implementation remains the same)
        List<String> path = new ArrayList<>();
        String node = destination;

        while (node != null) {
            path.add(node);
            node = parent.get(node);
        }

        Collections.reverse(path);
        return path;
    }
}