import java.util.ArrayList;

// Keeps all buses, drivers, routes and students, and applies the rules of the system.
public class BusManager {
    private final ArrayList<Bus> buses = new ArrayList<>();
    private final ArrayList<Driver> drivers = new ArrayList<>();
    private final ArrayList<Route> routes = new ArrayList<>();
    private final ArrayList<Student> students = new ArrayList<>();
    private int nextDriverId = 1;

    // ----- Adding records (return null if the record already exists / is invalid) -----

    public Route addRoute(String name, String[] stops) {
        if (findRoute(name) != null || stops.length < 2) {
            return null;
        }
        Route route = new Route(name, stops);
        routes.add(route);
        return route;
    }

    public Driver addDriver(String name, String phone) {
        Driver driver = new Driver(nextDriverId++, name, phone);
        drivers.add(driver);
        return driver;
    }

    public Bus addBus(String busNumber, int capacity) {
        if (findBus(busNumber) != null || capacity <= 0) {
            return null;
        }
        Bus bus = new Bus(busNumber, capacity);
        buses.add(bus);
        return bus;
    }

    public Student registerStudent(String rollNo, String name) {
        if (findStudent(rollNo) != null) {
            return null;
        }
        Student student = new Student(rollNo, name);
        students.add(student);
        return student;
    }

    // ----- Searching -----

    public Bus findBus(String busNumber) {
        for (Bus bus : buses) {
            if (bus.getBusNumber().equalsIgnoreCase(busNumber.trim())) {
                return bus;
            }
        }
        return null;
    }

    public Route findRoute(String name) {
        for (Route route : routes) {
            if (route.getName().equalsIgnoreCase(name.trim())) {
                return route;
            }
        }
        return null;
    }

    public Driver findDriver(int id) {
        for (Driver driver : drivers) {
            if (driver.getId() == id) {
                return driver;
            }
        }
        return null;
    }

    public Student findStudent(String rollNo) {
        for (Student student : students) {
            if (student.getRollNo().equalsIgnoreCase(rollNo.trim())) {
                return student;
            }
        }
        return null;
    }

    public ArrayList<Bus> getBuses() { 
        return buses; 
        }
    public ArrayList<Driver> getDrivers() {
         return drivers; 
         }
    public ArrayList<Route> getRoutes() { 
        return routes;
         }
    public ArrayList<Student> getStudents() { 
        return students; 
        }

    // ----- Assigning (each returns a message to show the user) -----

    public String assignRoute(Bus bus, Route route) {
        if (!bus.getStudents().isEmpty()) {
            return "Cannot change the route: remove the students from this bus first.";
        }
        bus.setRoute(route);
        return "Route '" + route.getName() + "' assigned to " + bus.getBusNumber() + ".";
    }

    public String assignDriver(Bus bus, Driver driver) {
        for (Bus other : buses) {
            if (other.getDriver() == driver && other != bus) {
                return driver.getName() + " is already driving " + other.getBusNumber() + ".";
            }
        }
        bus.setDriver(driver);
        return driver.getName() + " assigned to " + bus.getBusNumber() + ".";
    }

    public String assignStudent(Student student, Bus bus, String pickupStop) {
        if (student.getBus() != null) {
            return student.getName() + " is already assigned to " + student.getBus().getBusNumber() + ".";
        }
        if (bus.getRoute() == null) {
            return bus.getBusNumber() + " has no route yet. Assign a route first.";
        }
        String stop = bus.getRoute().findStop(pickupStop);
        if (stop == null) {
            return "'" + pickupStop + "' is not a stop on " + bus.getRoute().getName() + ".";
        }
        if (bus.isFull()) {
            return bus.getBusNumber() + " is full.";
        }
        bus.addStudent(student);
        student.assignTo(bus, stop);
        return student.getName() + " assigned to " + bus.getBusNumber() + " (pickup: " + stop + ").";
    }

    public String removeStudentFromBus(Student student) {
        Bus bus = student.getBus();
        if (bus == null) {
            return student.getName() + " is not assigned to any bus.";
        }
        bus.removeStudent(student);
        student.unassign();
        return student.getName() + " removed from " + bus.getBusNumber() + ".";
    }

    // ----- Tracking -----

    public String startTrip(Bus bus) {
        if (bus.getRoute() == null) {
            return "Cannot start: no route assigned.";
        }
        if (bus.getDriver() == null) {
            return "Cannot start: no driver assigned.";
        }
        bus.startTrip();
        return bus.getBusNumber() + " started its trip from " + bus.getCurrentStop() + ".";
    }

    public String moveBus(Bus bus) {
        if (bus.getStatus() != Bus.Status.RUNNING) {
            return bus.getBusNumber() + " is not running. Start the trip first.";
        }
        boolean stillRunning = bus.moveToNextStop();
        if (stillRunning) {
            return bus.getBusNumber() + " reached " + bus.getCurrentStop() + ".";
        }
        return bus.getBusNumber() + " reached the final stop (" + bus.getCurrentStop()
                + "). Trip finished and bus parked.";
    }
}
