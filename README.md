# BustrackinSystem_Java
Console-based University Bus Tracking System in Java using OOP. Manage buses, drivers, routes, and students, and track buses stop by stop.
# University Bus Tracking System (Java)

A console-based University Bus Tracking System written in core Java using object-oriented programming. It lets you manage buses, drivers, routes, and students, assign students to buses, and track a bus as it moves from stop to stop.

It uses only core Java and the standard library (`ArrayList`, `Scanner`).

## Features
- Add routes with ordered stops, drivers, buses, and students
- Assign a route and a driver to a bus
- Assign students to a bus with a pickup stop
- Stops a bus from going over its seat capacity
- Stops a driver from driving two buses
- Start a trip and move the bus stop by stop
- Track a bus (status and current stop)
- Find which bus a student rides and where it is now
- Sample data is loaded at startup so you can try it right away

## OOP Concepts Used
| Concept | Where |
|---------|-------|
| Classes and objects | `Bus`, `Driver`, `Route`, `Student` |
| Encapsulation | Private fields with getters and setters |
| Enum | `Bus.Status` (PARKED, RUNNING) |
| Association | A bus has a route, a driver, and a list of students |
| Separation of concerns | `BusManager` holds the rules, `Main` handles input and output |

## Project Structure
```
university-bus-tracking-java/
├── src/
│   ├── Main.java          # Menu and user input
│   ├── BusManager.java    # Rules and records
│   ├── Bus.java
│   ├── Driver.java
│   ├── Route.java
│   └── Student.java
├── .gitignore
└── README.md
```

## How to Run

You need JDK 11 or newer.

Compile:
```bash
mkdir out
javac -d out src/*.java
```

Run:
```bash
java -cp out Main
```

## Sample Data
| Type | Details |
|------|---------|
| Routes | Main Route, North Route |
| Drivers | ID 1 Ahmed Raza, ID 2 Bilal Hussain |
| Buses | BUS-101 (30 seats), BUS-102 (25 seats) |
| Students | CS-001, CS-002, CS-003 |

## Try This First
1. Option 5: assign `Main Route` to `BUS-101`
2. Option 6: assign driver `1` to `BUS-101`
3. Option 7: assign `CS-001` to `BUS-101` with pickup `Library`
4. Option 9: start the trip for `BUS-101`
5. Option 10: move the bus to the next stop (repeat)
6. Option 12: check where the bus of `CS-001` is now

## Future Ideas
- Save and load data from a file
- Bus delay and breakdown status
- Separate login for admin and student
- A GUI or web version


