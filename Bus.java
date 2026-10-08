import java.util.ArrayList;

public class Bus {

    public enum Status { PARKED, RUNNING }

    private final String busNumber;
    private final int capacity;
    private Route route;
    private Driver driver;
    private final ArrayList<Student> students = new ArrayList<>();
    private int currentStopIndex = 0;
    private Status status = Status.PARKED;

    public Bus(String busNumber, int capacity) {
        this.busNumber = busNumber;
        this.capacity = capacity;
    }

    public String getBusNumber() { 
        return busNumber; 
        }
    public int getCapacity() { 
        return capacity; 
        }
    public Route getRoute() {
         return route; 
         }
    public Driver getDriver() {
         return driver;
          }
    public Status getStatus() {
         return status; 
         }
    public ArrayList<Student> getStudents() { 
        return students; 
        }

    public void setRoute(Route route) {
        this.route = route;
        this.currentStopIndex = 0;
        this.status = Status.PARKED;
    }

    public void setDriver(Driver driver) { 
        this.driver = driver; 
        }

    public boolean isFull() { 
        return students.size() >= capacity; 
        }

    public int getSeatsLeft() { 
        return capacity - students.size(); 
        }

    public boolean addStudent(Student student) {
        if (isFull() || students.contains(student)) {
            return false;
        }
        students.add(student);
        return true;
    }

    public boolean removeStudent(Student student) {
        return students.remove(student);
    }

    // ----- Tracking -----

    public String getCurrentStop() {
        if (route == null) {
            return "N/A";
        }
        return route.getStops()[currentStopIndex];
    }

    public void startTrip() {
        currentStopIndex = 0;
        status = Status.RUNNING;
    }

    // Moves the bus to the next stop. Returns false when the bus has reached
    // the last stop (the trip ends and the bus is parked).
    public boolean moveToNextStop() {
        if (currentStopIndex < route.getStopCount() - 1) {
            currentStopIndex++;
            if (currentStopIndex == route.getStopCount() - 1) {
                status = Status.PARKED;
                return false;
            }
            return true;
        }
        status = Status.PARKED;
        return false;
    }

    @Override
    public String toString() {
        String routeName = (route == null) ? "Not assigned" : route.getName();
        String driverName = (driver == null) ? "Not assigned" : driver.getName();
        return busNumber + " | Route: " + routeName
                + " | Driver: " + driverName
                + " | Seats: " + students.size() + "/" + capacity
                + " | Status: " + status
                + " | At: " + getCurrentStop();
    }
}
