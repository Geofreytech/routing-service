package com.example.routingservice.loader;

import com.example.routingservice.model.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CountryLoader {

    // REVERTED: Store the simplified Adjacency Map (CCA3 -> [Borders])
    private Map<String, List<String>> adjacencyMap = new HashMap<>();

    // REVERTED: Getter name matches the new RoutingService constructor
    public Map<String, List<String>> getAdjacencyMap() {
        return adjacencyMap;
    }

    @PostConstruct
    public void loadCountries() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("countries.json");

            if (inputStream == null) {

                throw new RuntimeException("Could not find countries.json in classpath (src/main/resources)");
            }

            List<Country> countries = mapper.readValue(inputStream, new TypeReference<List<Country>>() {});

            // BUILD THE ADJACENCY MAP: map cca3 to its list of borders
            for (Country country : countries) {
                // Ensure no null lists are stored, use empty list if borders is null
                List<String> borders = country.getBorders() != null ? country.getBorders() : Collections.emptyList();
                adjacencyMap.put(country.getCca3(), borders);
            }

            System.out.println("Loaded " + adjacencyMap.size() + " countries.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to load country data", e);
        }
    }
}