public class Route {
    private final String name;
    private final String[] stops;

    public Route(String name, String[] stops) {
        this.name = name;
        this.stops = stops;
    }

    public String getName() { 
        return name; 
        }
    public String[] getStops() { 
        return stops; 
        }
    public int getStopCount() { 
        return stops.length; 
        }

    // Returns the stop name as stored (ignoring upper/lower case), or null if not found
    public String findStop(String stopName) {
        for (String stop : stops) {
            if (stop.equalsIgnoreCase(stopName.trim())) {
                return stop;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name + " (" + String.join(" -> ", stops) + ")";
    }
}
