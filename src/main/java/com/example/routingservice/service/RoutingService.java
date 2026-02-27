package com.example.routingservice.service;

import com.example.routingservice.exception.NoRouteFoundException;
import com.example.routingservice.loader.CountryLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

@Service
public class RoutingService {

    private final Map<String, List<String>> graph;

    /**
     * Constructor injects CountryLoader and builds adjacency graph
     */
    public RoutingService(CountryLoader loader) {
        this.graph = loader.getAdjacencyMap();
    }

    /**
     * Finds a land route between two countries using BFS.
     *
     * @param origin ISO 3166-1 alpha-3 code
     * @param destination ISO 3166-1 alpha-3 code
     * @return list of country codes representing the route
     */
    public List<String> findLandRoute(String origin, String destination) {

        // 1. Validate input
        if (!graph.containsKey(origin) || !graph.containsKey(destination)) {
            throw new NoRouteFoundException("Invalid country code(s) provided.");
        }

        // 2. Origin equals destination
        if (origin.equals(destination)) {
            return Collections.singletonList(origin);
        }

        // BFS data structures
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        // Initialize BFS
        queue.add(origin);
        visited.add(origin);
        parent.put(origin, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (String neighbor : graph.getOrDefault(current, Collections.emptyList())) {

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);

                    // Destination found
                    if (neighbor.equals(destination)) {
                        return buildPath(parent, destination);
                    }
                }
            }
        }

        // 3. No route found
        throw new NoRouteFoundException(
                "No land crossing found between " + origin + " and " + destination
        );
    }

    /**
     * Reconstructs path from parent map.
     */
    private List<String> buildPath(Map<String, String> parent, String destination) {

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
