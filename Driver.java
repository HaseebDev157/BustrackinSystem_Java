import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    private static final BusManager manager = new BusManager();

    public static void main(String[] args) {
        loadSampleData();
        System.out.println("===== UNIVERSITY BUS TRACKING SYSTEM =====");

        int choice;
        do {
            showMenu();
            choice = readInt("Choose an option: ");
            handleChoice(choice);
        } while (choice != 0);

        System.out.println("Goodbye!");
    }

    private static void showMenu() {
        System.out.println("\n--- SETUP ---");
        System.out.println(" 1. Add route");
        System.out.println(" 2. Add driver");
        System.out.println(" 3. Add bus");
        System.out.println(" 4. Register student");
        System.out.println("--- ASSIGN ---");
        System.out.println(" 5. Assign route to bus");
        System.out.println(" 6. Assign driver to bus");
        System.out.println(" 7. Assign student to bus");
        System.out.println(" 8. Remove student from bus");
        System.out.println("--- TRACKING ---");
        System.out.println(" 9. Start bus trip");
        System.out.println("10. Move bus to next stop");
        System.out.println("11. Track a bus");
        System.out.println("12. Find a student's bus");
        System.out.println("--- VIEW ---");
        System.out.println("13. View all buses");
        System.out.println("14. View students of a bus");
        System.out.println("15. View routes and drivers");
        System.out.println(" 0. Exit");
    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1:  addRoute(); break;
            case 2:  addDriver(); break;
            case 3:  addBus(); break;
            case 4:  registerStudent(); break;
            case 5:  assignRoute(); break;
            case 6:  assignDriver(); break;
            case 7:  assignStudent(); break;
            case 8:  removeStudent(); break;
            case 9:  startTrip(); break;
            case 10: moveBus(); break;
            case 11: trackBus(); break;
            case 12: findStudentBus(); break;
            case 13: viewAllBuses(); break;
            case 14: viewBusStudents(); break;
            case 15: viewRoutesAndDrivers(); break;
            case 0:  break;
            default: System.out.println("Invalid option.");
        }
    }

    // ----- Input helpers -----

    private static String readText(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            String text = readText(prompt);
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static Bus askForBus() {
        String number = readText("Bus number: ");
        Bus bus = manager.findBus(number);
        if (bus == null) {
            System.out.println("Bus not found.");
        }
        return bus;
    }

    private static Student askForStudent() {
        String rollNo = readText("Student roll number: ");
        Student student = manager.findStudent(rollNo);
        if (student == null) {
            System.out.println("Student not found.");
        }
        return student;
    }

    // ----- Menu actions -----

    private static void addRoute() {
        String name = readText("Route name: ");
        String stopsText = readText("Stops in order, separated by commas (e.g. Gate,Library,Hostel): ");
        String[] stops = stopsText.split("\\s*,\\s*");
        Route route = manager.addRoute(name, stops);
        if (route == null) {
            System.out.println("Could not add route (name already exists, or fewer than 2 stops).");
        } else {
            System.out.println("Route added: " + route);
        }
    }

    private static void addDriver() {
        String name = readText("Driver name: ");
        String phone = readText("Phone: ");
        Driver driver = manager.addDriver(name, phone);
        System.out.println("Driver added: " + driver);
    }

    private static void addBus() {
        String number = readText("Bus number: ");
        int capacity = readInt("Seat capacity: ");
        Bus bus = manager.addBus(number, capacity);
        if (bus == null) {
            System.out.println("Could not add bus (number already exists, or invalid capacity).");
        } else {
            System.out.println("Bus added: " + bus.getBusNumber());
        }
    }

    private static void registerStudent() {
        String rollNo = readText("Roll number: ");
        String name = readText("Student name: ");
        Student student = manager.registerStudent(rollNo, name);
        if (student == null) {
            System.out.println("A student with this roll number already exists.");
        } else {
            System.out.println("Student registered: " + student.getName());
        }
    }

    private static void assignRoute() {
        Bus bus = askForBus();
        if (bus == null) return;
        String routeName = readText("Route name: ");
        Route route = manager.findRoute(routeName);
        if (route == null) {
            System.out.println("Route not found.");
            return;
        }
        System.out.println(manager.assignRoute(bus, route));
    }

    private static void assignDriver() {
        Bus bus = askForBus();
        if (bus == null) return;
        int id = readInt("Driver ID: ");
        Driver driver = manager.findDriver(id);
        if (driver == null) {
            System.out.println("Driver not found.");
            return;
        }
        System.out.println(manager.assignDriver(bus, driver));
    }

    private static void assignStudent() {
        Student student = askForStudent();
        if (student == null) return;
        Bus bus = askForBus();
        if (bus == null) return;
        if (bus.getRoute() != null) {
            System.out.println("Stops: " + String.join(", ", bus.getRoute().getStops()));
        }
        String stop = readText("Pickup stop: ");
        System.out.println(manager.assignStudent(student, bus, stop));
    }

    private static void removeStudent() {
        Student student = askForStudent();
        if (student == null) return;
        System.out.println(manager.removeStudentFromBus(student));
    }

    private static void startTrip() {
        Bus bus = askForBus();
        if (bus == null) return;
        System.out.println(manager.startTrip(bus));
    }

    private static void moveBus() {
        Bus bus = askForBus();
        if (bus == null) return;
        System.out.println(manager.moveBus(bus));
    }

    private static void trackBus() {
        Bus bus = askForBus();
        if (bus == null) return;
        System.out.println("\n--- Tracking " + bus.getBusNumber() + " ---");
        System.out.println("Status       : " + bus.getStatus());
        System.out.println("Current stop : " + bus.getCurrentStop());
        if (bus.getRoute() != null) {
            System.out.println("Route        : " + bus.getRoute());
        }
    }

    private static void findStudentBus() {
        Student student = askForStudent();
        if (student == null) return;
        Bus bus = student.getBus();
        if (bus == null) {
            System.out.println(student.getName() + " is not assigned to any bus.");
            return;
        }
        System.out.println(student.getName() + " rides " + bus.getBusNumber()
                + " (pickup: " + student.getPickupStop() + ")");
        System.out.println("Bus status: " + bus.getStatus() + ", currently at " + bus.getCurrentStop());
    }

    private static void viewAllBuses() {
        if (manager.getBuses().isEmpty()) {
            System.out.println("No buses added yet.");
            return;
        }
        for (Bus bus : manager.getBuses()) {
            System.out.println(bus);
        }
    }

    private static void viewBusStudents() {
        Bus bus = askForBus();
        if (bus == null) return;
        if (bus.getStudents().isEmpty()) {
            System.out.println("No students on " + bus.getBusNumber() + ".");
            return;
        }
        System.out.println("Students on " + bus.getBusNumber() + ":");
        for (Student student : bus.getStudents()) {
            System.out.println("  " + student.getRollNo() + " - " + student.getName()
                    + " (pickup: " + student.getPickupStop() + ")");
        }
    }

    private static void viewRoutesAndDrivers() {
        System.out.println("Routes:");
        for (Route route : manager.getRoutes()) {
            System.out.println("  " + route);
        }
        System.out.println("Drivers:");
        for (Driver driver : manager.getDrivers()) {
            System.out.println("  " + driver);
        }
    }

    // ----- Sample data so you can try the system right away -----

    private static void loadSampleData() {
        manager.addRoute("Main Route", new String[]{"University Gate", "Library", "City Center", "Railway Station"});
        manager.addRoute("North Route", new String[]{"University Gate", "Model Town", "Bus Stand"});

        manager.addDriver("Ahmed Raza", "0300-1111111");
        manager.addDriver("Bilal Hussain", "0300-2222222");

        manager.addBus("BUS-101", 30);
        manager.addBus("BUS-102", 25);

        manager.registerStudent("CS-001", "Ali Khan");
        manager.registerStudent("CS-002", "Sara Ahmed");
        manager.registerStudent("CS-003", "Usman Tariq");
    }
}
