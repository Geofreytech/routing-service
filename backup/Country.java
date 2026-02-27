package com.example.routingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// CRITICAL FIX 1: Ignore all top-level unknown properties (like "tld", "capital")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    // CRITICAL FIX 2: Change the type of the 'name' field to the nested Name class.
    private Name name;

    private String cca3;
    private List<String> borders;

    // Nested class to map the JSON 'name' object
    // It's usually better practice to define this nested class in its own file (Name.java),
    // but defining it here is valid if you prefer.
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name { // NOTE: Must be 'static' if nested
        private String common; // Maps to the inner "common" field

        public String getCommon() {
            return common;
        }
        public void setCommon(String common) {
            this.common = common;
        }
    }

    // --- Getters and Setters for 'name' (Type is now Name, not String) ---
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
    // ----------------------------------------------------------------------

    public String getCca3() {
        return cca3;
    }

    public void setCca3(String cca3) {
        this.cca3 = cca3;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }
}