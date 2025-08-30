import java.sql.*;
import java.util.Scanner;

public class EmployeeDepartmentCRUD {

    // ✅ Update with your PostgreSQL setup
    private static final String URL = "jdbc:postgresql://localhost:5432/company_dbdm";
    private static final String USER = "postgres";       // your DB username
    private static final String PASSWORD = "kaushal"; // your DB password

    // Load PostgreSQL Driver
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Insert Department
    public static void insertDepartment(String deptId, String deptName) {
        String sql = "INSERT INTO Department (dept_id, dept_name) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, deptId);
            ps.setString(2, deptName);
            ps.executeUpdate();
            System.out.println("✅ Department inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert Employee
    public static void insertEmployee(String empId, String empName, String deptId, double salary) {
        String sql = "INSERT INTO Employee (emp_id, emp_name, dept_id, salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empId);
            ps.setString(2, empName);
            ps.setString(3, deptId);
            ps.setDouble(4, salary);
            ps.executeUpdate();
            System.out.println("✅ Employee inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Employee Salary
    public static void updateEmployeeSalary(String empId, double newSalary) {
        String sql = "UPDATE Employee SET salary = ? WHERE emp_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newSalary);
            ps.setString(2, empId);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Employee salary updated successfully!");
            else
                System.out.println("⚠️ Employee not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Employee
    public static void deleteEmployee(String empId) {
        String sql = "DELETE FROM Employee WHERE emp_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empId);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Employee deleted successfully!");
            else
                System.out.println("⚠️ Employee not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View All Employees with Department
    public static void viewEmployees() {
        String sql = "SELECT e.emp_id, e.emp_name, e.salary, d.dept_name " +
                "FROM Employee e LEFT JOIN Department d ON e.dept_id = d.dept_id";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            System.out.println("\n=== Employee Details ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("emp_id") +
                        ", Name: " + rs.getString("emp_name") +
                        ", Salary: " + rs.getDouble("salary") +
                        ", Department: " + rs.getString("dept_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch Employees by Department
    public static void fetchEmployeesByDept(String deptName) {
        String sql = "SELECT e.emp_id, e.emp_name, e.salary " +
                "FROM Employee e JOIN Department d ON e.dept_id = d.dept_id " +
                "WHERE d.dept_name = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, deptName);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n=== Employees in " + deptName + " Department ===");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getString("emp_id") +
                            ", Name: " + rs.getString("emp_name") +
                            ", Salary: " + rs.getDouble("salary"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main Menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Insert Department\n2. Insert Employee\n3. Update Employee Salary\n4. Delete Employee\n5. View Employees\n6. Fetch Employees by Department\n7. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Dept ID: "); String deptId = sc.nextLine();
                    System.out.print("Dept Name: "); String deptName = sc.nextLine();
                    insertDepartment(deptId, deptName);
                    break;
                case 2:
                    System.out.print("Emp ID: "); String empId = sc.nextLine();
                    System.out.print("Emp Name: "); String empName = sc.nextLine();
                    System.out.print("Dept ID: "); String eDeptId = sc.nextLine();
                    System.out.print("Salary: "); double salary = sc.nextDouble();
                    sc.nextLine();
                    insertEmployee(empId, empName, eDeptId, salary);
                    break;
                case 3:
                    System.out.print("Emp ID to update: "); String upEmpId = sc.nextLine();
                    System.out.print("New Salary: "); double newSalary = sc.nextDouble();
                    sc.nextLine();
                    updateEmployeeSalary(upEmpId, newSalary);
                    break;
                case 4:
                    System.out.print("Emp ID to delete: "); String delEmpId = sc.nextLine();
                    deleteEmployee(delEmpId);
                    break;
                case 5:
                    viewEmployees();
                    break;
                case 6:
                    System.out.print("Dept Name: "); String fDeptName = sc.nextLine();
                    fetchEmployeesByDept(fDeptName);
                    break;
                case 7:
                    System.out.println("👋 Exiting...");
                    sc.close();
                    System.exit(0);
            }
        }
    }
}
