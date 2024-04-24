package projectone.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    @JsonProperty("coord")
    private Map<String, Float> coord;

    @JsonProperty("weather")
    private Weather[] weather;

    @JsonProperty("base")
    private String base;

    @JsonProperty("main")
    private Map<String, Float> main;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("wind")
    private Map<String, Float> wind;

    @JsonProperty("clouds")
    private Map<String, Integer> clouds;

    @JsonProperty("dt")
    private long dt;

    @JsonProperty("sys")
    private Map<String, Object> sys;

    @JsonProperty("timezone")
    private int timezone;

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("cod")
    private int cod;

    // Getters and setters

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        @JsonProperty("id")
        private int id;

        @JsonProperty("main")
        private String main;

        @JsonProperty("description")
        private String description;

        @JsonProperty("icon")
        private String icon;

        // Getters and setters


        public int getId() {
            return id;
        }

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        @Override
        public String toString() {
            return "Weather{" +
                    "id=" + id +
                    ", main='" + main + '\'' +
                    ", description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

    public Map<String, Float> getCoord() {
        return coord;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Map<String, Float> getMain() {
        return main;
    }

    public int getVisibility() {
        return visibility;
    }

    public Map<String, Float> getWind() {
        return wind;
    }

    public Map<String, Integer> getClouds() {
        return clouds;
    }

    public long getDt() {
        return dt;
    }

    public Map<String, Object> getSys() {
        return sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "coord=" + coord +
                ", weather=" + Arrays.toString(weather) +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility=" + visibility +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dt=" + dt +
                ", sys=" + sys +
                ", timezone=" + timezone +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }
}