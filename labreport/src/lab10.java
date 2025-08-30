// Base class: Employee
class Employee {
    private int id;
    private String name;
    private double salary;

    // Constructor
    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for salary
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Method to calculate salary (to be overridden)
    public void calculateSalary() {
        System.out.println("Salary calculation for employee.");
    }

    // Method to display employee details
    public void displayDetails() {
        System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
    }
}

// Subclass: FullTimeEmployee
class FullTimeEmployee extends Employee {
    private double bonus;

    public FullTimeEmployee(int id, String name, double bonus) {
        super(id, name);
        this.bonus = bonus;
    }

    // Getter and Setter for bonus
    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    // Overriding calculateSalary method
    @Override
    public void calculateSalary() {
        // Full-time salary = base salary + bonus
        double baseSalary = 50000; // Assume base salary
        setSalary(baseSalary + bonus);
    }
}

// Subclass: PartTimeEmployee
class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(int id, String name, int hoursWorked, double hourlyRate) {
        super(id, name);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    // Overriding calculateSalary method
    @Override
    public void calculateSalary() {
        // Part-time salary = hours worked * hourly rate
        setSalary(hoursWorked * hourlyRate);
    }
}

// Main class
public class lab10 {
    public static void main(String[] args) {
        // Create a Full-Time Employee
        FullTimeEmployee ftEmployee = new FullTimeEmployee(1, "konit", 5000);
        ftEmployee.calculateSalary();
        ftEmployee.displayDetails();

        // Create a Part-Time Employee
        PartTimeEmployee ptEmployee = new PartTimeEmployee(2, "sujan", 80, 200);
        ptEmployee.calculateSalary();
        ptEmployee.displayDetails();
    }
}
