package com.example.routingservice.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.routingservice.model.Country;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class CountryLoader {

    private final Map<String, List<String>> adjacencyMap = new HashMap<>();

    public Map<String, List<String>> getAdjacencyMap() {
        return adjacencyMap;
    }

    @PostConstruct
    public void loadCountries() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("countries.json");

            List<Country> countries = mapper.readValue(inputStream, new TypeReference<>() {});
            for (Country country : countries) {
                adjacencyMap.put(country.getCca3(), country.getBorders());
            }

            System.out.println("Loaded " + adjacencyMap.size() + " countries.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to load country data", e);
        }
    }
}
