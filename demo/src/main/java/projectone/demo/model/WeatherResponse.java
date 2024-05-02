/**
 * Represents a response from a weather API.
 */
package projectone.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Map;

/**
 * Represents a response from a weather API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    /** The coordinates of the location. */
    @JsonProperty("coord")
    private Map<String, Float> coord;

    /** The weather conditions at the location. */
    @JsonProperty("weather")
    private Weather[] weather;

    /** The base of the data. */
    @JsonProperty("base")
    private String base;

    /** The main weather parameters. */
    @JsonProperty("main")
    private Map<String, Float> main;

    /** The visibility at the location. */
    @JsonProperty("visibility")
    private int visibility;

    /** The wind parameters at the location. */
    @JsonProperty("wind")
    private Map<String, Float> wind;

    /** The cloudiness at the location. */
    @JsonProperty("clouds")
    private Map<String, Integer> clouds;

    /** The time of data calculation. */
    @JsonProperty("dt")
    private long dt;

    /** Additional system information. */
    @JsonProperty("sys")
    private Map<String, Object> sys;

    /** The timezone offset from UTC. */
    @JsonProperty("timezone")
    private int timezone;

    /** The city ID. */
    @JsonProperty("id")
    private int id;

    /** The name of the city. */
    @JsonProperty("name")
    private String name;

    /** The response code. */
    @JsonProperty("cod")
    private int cod;

    // Getters and setters

    /**
     * Represents weather information.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {

        /** The weather condition ID. */
        @JsonProperty("id")
        private int id;

        /** The main weather description. */
        @JsonProperty("main")
        private String main;

        /** The detailed weather description. */
        @JsonProperty("description")
        private String description;

        /** The weather icon ID. */
        @JsonProperty("icon")
        private String icon;

        // Getters and setters

        /**
         * Gets the weather condition ID.
         *
         * @return The weather condition ID.
         */
        public int getId() {
            return id;
        }

        /**
         * Gets the main weather parameter.
         *
         * @return The main weather parameter.
         */
        public String getMain() {
            return main;
        }

        /**
         * Gets the weather condition description.
         *
         * @return The weather condition description.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Gets the weather icon ID.
         *
         * @return The weather icon ID.
         */
        public String getIcon() {
            return icon;
        }

        /**
         * Returns a string representation of the Weather object.
         *
         * @return A string representation of the object.
         */
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

    /**
     * Returns the coordinates of the location.
     *
     * @return The coordinates of the location.
     */
    public Map<String, Float> getCoord() {
        return coord;
    }

    /**
     * Returns an array of weather descriptions.
     *
     * @return An array of weather descriptions.
     */
    public Weather[] getWeather() {
        return weather;
    }

    /**
     * Gets the base of the data.
     *
     * @return The base of the data.
     */
    public String getBase() {
        return base;
    }

    /**
     * Gets the main weather parameter.
     *
     * @return The main weather parameter.
     */
    public Map<String, Float> getMain() {
        return main;
    }

    /**
     * Gets the visibility of the weather.
     *
     * @return The visibility of the weather.
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * Gets the map of the wind at the location.
     *
     * @return The map of the wind at the location.
     */
    public Map<String, Float> getWind() {
        return wind;
    }

    /**
     * Gets cloud conditions of the location.
     *
     * @return The cloud conditions of the location.
     */
    public Map<String, Integer> getClouds() {
        return clouds;
    }

    /**
     * Gets the time of data.
     *
     * @return The time of data.
     */
    public long getDt() {
        return dt;
    }

    /**
     * Gets system information.
     *
     * @return The system information.
     */
    public Map<String, Object> getSys() {
        return sys;
    }

    /**
     * Gets the timezone of the location.
     *
     * @return The timezone of the location.
     */
    public int getTimezone() {
        return timezone;
    }

    /**
     * Gets the weather condition ID.
     *
     * @return The weather condition ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the weather.
     *
     * @return The name of the weather.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the code of the weather.
     *
     * @return The code of the weather.
     */
    public int getCod() {
        return cod;
    }

    /**
     * Returns a string representation of the WeatherResponse object.
     *
     * @return A string representation of the object.
     */
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