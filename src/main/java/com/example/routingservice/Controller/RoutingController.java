package com.example.routingservice.Controller;

import com.example.routingservice.exception.NoRouteFoundException;
import com.example.routingservice.service.RoutingService; // <-- CORRECTED IMPORT
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Contains @RestController, @RequestMapping, etc.
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/routing")
public class RoutingController {

    // Dependency injection using the correct type name
    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    /**
     * REST endpoint to calculate the land route.
     */
    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<Map<String, List<String>>> getRoute(
            @PathVariable String origin,
            @PathVariable String destination) {

        List<String> route = routingService.findLandRoute(
                origin.toUpperCase(),
                destination.toUpperCase()
        );

        return ResponseEntity.ok(Map.of("route", route));
    }

    /**
     * Global Exception Handler for the NoRouteFoundException (HTTP 400).
     */
    @ExceptionHandler(NoRouteFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNoRouteFound(NoRouteFoundException ex) {
        return Map.of("error", ex.getMessage());
    }
}