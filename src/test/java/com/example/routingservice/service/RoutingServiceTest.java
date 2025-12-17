package com.example.routingservice.service;

import com.example.routingservice.exception.NoRouteFoundException;
import com.example.routingservice.loader.CountryLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoutingServiceTest {

    @Mock
    private CountryLoader countryLoader; // Mocked dependency

    @InjectMocks
    private RoutingService routingService; // Service under test

    // NEW: Map structure now matches the service's dependency
    private Map<String, List<String>> testGraph;

    @BeforeEach
    void setUp() {
        // Create a simple graph using the Adjacency Map structure
        testGraph = Map.of(
                "ESP", List.of("FRA", "PRT"),
                "FRA", List.of("ESP", "DEU"),
                "PRT", List.of("ESP"),
                "DEU", List.of("FRA", "CZE", "AUT"),
                "CZE", List.of("DEU", "AUT"),
                "AUT", List.of("CZE", "DEU", "ITA"),
                "ITA", List.of("AUT"),
                "CAN", List.of("USA"),
                "USA", List.of("CAN", "MEX"),
                "RUS", List.of("CHN") // Disconnected
        );

        // MOCKING FIX: Mock the new method name and return the new map structure
        when(countryLoader.getAdjacencyMap()).thenReturn(testGraph);
    }

    @Test
    void testDirectRoute() throws NoRouteFoundException {
        // ESP -> FRA (1 hop)
        List<String> route = routingService.findLandRoute("ESP", "FRA");
        assertEquals(List.of("ESP", "FRA"), route);
    }

    @Test
    void testMultiHopRoute() throws NoRouteFoundException {
        // ESP -> ITA (4 hops: ESP, FRA, DEU, AUT, ITA)
        List<String> route = routingService.findLandRoute("ESP", "ITA");
        assertEquals(List.of("ESP", "FRA", "DEU", "AUT", "ITA"), route);
    }

    @Test
    void testStartEqualsDestination() throws NoRouteFoundException {
        List<String> route = routingService.findLandRoute("CZE", "CZE");
        assertEquals(List.of("CZE"), route);
    }

    @Test
    void testUnrouteablePathThrowsException() {
        // CAN to RUS (No land connection)
        assertThrows(NoRouteFoundException.class, () -> {
            routingService.findLandRoute("CAN", "RUS");
        });
    }

    @Test
    void testInvalidCountryCodeThrowsException() {
        // Invalid destination code
        assertThrows(NoRouteFoundException.class, () -> {
            routingService.findLandRoute("CZE", "ZZZ");
        });
    }
}