public class Student {
    private final String rollNo;
    private final String name;
    private Bus bus;
    private String pickupStop;

    public Student(String rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    public String getRollNo() { 
        return rollNo; }
    public String getName() { return name; 
    }
    public Bus getBus() { 
        return bus; 
        }
    public String getPickupStop() { 
        return pickupStop; 
        }

    public void assignTo(Bus bus, String pickupStop) {
        this.bus = bus;
        this.pickupStop = pickupStop;
    }

    public void unassign() {
        this.bus = null;
        this.pickupStop = null;
    }

    @Override
    public String toString() {
        String busInfo = (bus == null) ? "No bus" : bus.getBusNumber() + ", pickup: " + pickupStop;
        return rollNo + " - " + name + " [" + busInfo + "]";
    }
}
