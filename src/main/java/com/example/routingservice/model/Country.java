package com.example.routingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    private Name name;        // nested object
    private String cca3;
    private List<String> borders;

    public Country() {}

    public Name getName() { return name; }
    public void setName(Name name) { this.name = name; }

    public String getCca3() { return cca3; }
    public void setCca3(String cca3) { this.cca3 = cca3; }

    public List<String> getBorders() { return borders; }
    public void setBorders(List<String> borders) { this.borders = borders; }

    // optional convenience method to get common name
    public String getCommonName() {
        return name != null ? name.getCommon() : null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        private String common;
        private String official;

        public Name() {}

        public String getCommon() { return common; }
        public void setCommon(String common) { this.common = common; }

        public String getOfficial() { return official; }
        public void setOfficial(String official) { this.official = official; }
    }
}
