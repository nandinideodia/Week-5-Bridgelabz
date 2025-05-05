import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.sql.*;


public class CSVSubimission {
    public class ManualParsing {
        public static void main(String[] args){
            String filepath = "sheet1.csv";
            try (BufferedReader br = new BufferedReader(new FileReader(filepath))){
                String line;
                while((line = br.readLine()) != null){
                    String[] columns = line.split(",");
                    System.out.println("Id "+ columns[0]+ " NAme: " + columns[1]);
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public class WriteCSV {
        public static void main(String[] args){
            String filePath = "sheet1.csv";
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
                writer.write("Id, Name, Dept, Money \n");
                writer.write("1, Nandini, Finance, 10 \n");
                writer.write("2, Jiya, Finance, 10 \n");
                writer.write("3, Mother, Finance, 10 \n");
                writer.write("4, FAther, Finance, 10 \n");
                writer.write("5, Piyush, Finance, 10 \n");
                System.out.println("Written successfully");

            }

            catch (IOException e){
                e.printStackTrace();
            }


            try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
                String line;
                while((line = br.readLine()) != null){
                    String[] columns = line.split(",");
                    System.out.println("Id "+ columns[0]+ " NAme: " + columns[1]);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public class CountCSVRecords {
        public static void main(String[] args) {
            String filePath = "your_data.csv"; // Replace with the actual path to your CSV file
            int recordCount = 0;
            boolean isHeaderRow = true;

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (isHeaderRow) {
                        isHeaderRow = false; // Skip the first line (header)
                        continue;
                    }
                    recordCount++;
                }
                System.out.println("Number of records (excluding header): " + recordCount);
            } catch (IOException e) {
                System.err.println("Error reading the CSV file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    public class FilteringCSV {
        public static void main(String[] args){
            String filePath = "sheet1.csv";
            try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
                String line;
                boolean isHeaderRow = true;
                while((line = br.readLine()) != null){
                    if(isHeaderRow){
                        isHeaderRow = false;
                        continue;
                    }
                    try{
                        String[] col = line.split(",");
                        if(col.length >= 4){
                            String name = col[1].trim();
                            int marks = Integer.parseInt(col[3].trim());

                            if(marks >50){
                                System.out.println(line);
                            }
                        }
                    }
                    catch(NumberFormatException e) {
                        System.err.println("Warning: Invalid marks format");
                    }
                }
            }
            catch (IOException e) {
                System.err.println("Error reading the CSV file: " + e.getMessage());
                e.printStackTrace();
            }

        }
    }




    public class SearchEmployee {
        public static void main(String[] args) {
            String filePath = "employees.csv"; // Replace with the actual path
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the name of the employee to search: ");
            String searchName = scanner.nextLine().trim();

            boolean found = false;

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean isHeaderRow = true;
                while ((line = br.readLine()) != null) {
                    if (isHeaderRow) {
                        isHeaderRow = false;
                        continue;
                    }
                    String[] columns = line.split(",");
                    // Assuming the CSV has columns: ID,Name,Department,Salary
                    if (columns.length >= 4) {
                        String name = columns[1].trim();
                        String department = columns[2].trim();
                        String salaryStr = columns[3].trim();

                        if (name.equalsIgnoreCase(searchName)) {
                            try {
                                int salary = Integer.parseInt(salaryStr);
                                System.out.println("Employee Found:");
                                System.out.println("Name: " + name);
                                System.out.println("Department: " + department);
                                System.out.println("Salary: " + salary);
                                found = true;
                                break; // Stop searching once found
                            } catch (NumberFormatException e) {
                                System.err.println("Warning: Invalid salary format for " + name + " - " + salaryStr);
                            }
                        }
                    } else {
                        System.err.println("Warning: Skipping line with insufficient columns - " + line);
                    }
                }

                if (!found) {
                    System.out.println("Employee with name '" + searchName + "' not found.");
                }

            } catch (IOException e) {
                System.err.println("Error reading the CSV file: " + e.getMessage());
                e.printStackTrace();
            } finally {
                scanner.close();
            }
        }
    }


    public class UpdateSalary {
        public static void main(String[] args) {
            String inputFile = "employees.csv"; // Replace with your input file path
            String outputFile = "updated_employees.csv"; // Name for the new output file
            List<String[]> updatedRecords = new ArrayList<>();
            boolean headerProcessed = false;

            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (!headerProcessed) {
                        updatedRecords.add(columns); // Add the header row as is
                        headerProcessed = true;
                        continue;
                    }

                    if (columns.length >= 4) {
                        String department = columns[2].trim();
                        String salaryStr = columns[3].trim();

                        if (department.equalsIgnoreCase("IT")) {
                            try {
                                double salary = Double.parseDouble(salaryStr);
                                double increasedSalary = salary * 1.10; // Increase by 10%
                                columns[3] = String.format("%.2f", increasedSalary); // Format to 2 decimal places
                            } catch (NumberFormatException e) {
                                System.err.println("Warning: Invalid salary format for department IT - " + line);
                            }
                        }
                    } else {
                        System.err.println("Warning: Skipping line with insufficient columns - " + line);
                    }
                    updatedRecords.add(columns); // Add the (potentially updated) record
                }
            } catch (IOException e) {
                System.err.println("Error reading the CSV file: " + e.getMessage());
                e.printStackTrace();
                return; // Exit if there's an error reading
            }

            // Write the updated records to a new CSV file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
                for (String[] record : updatedRecords) {
                    bw.write(String.join(",", record));
                    bw.newLine();
                }
                System.out.println("Successfully updated salaries for the IT department and saved to " + outputFile);
            } catch (IOException e) {
                System.err.println("Error writing to the new CSV file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public class SortEmployeesBySalary {
        public static void main(String[] args) {
            String filePath = "employees.csv"; // Replace with your input file path
            List<String[]> employeeRecords = new ArrayList<>();
            boolean isHeaderRow = true;
            String[] header = null;

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (isHeaderRow) {
                        header = columns;
                        isHeaderRow = false;
                        continue;
                    }
                    employeeRecords.add(columns);
                }
            } catch (IOException e) {
                System.err.println("Error reading the CSV file: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            // Sort the employee records by Salary (descending)
            employeeRecords.sort(new Comparator<String[]>() {
                @Override
                public int compare(String[] emp1, String[] emp2) {
                    try {
                        double salary1 = Double.parseDouble(emp1[3].trim()); // Assuming Salary is the 4th column (index 3)
                        double salary2 = Double.parseDouble(emp2[3].trim());
                        // Sort in descending order (highest salary first)
                        return Double.compare(salary2, salary1);
                    } catch (NumberFormatException e) {
                        // Handle cases where salary might not be a valid number
                        System.err.println("Warning: Invalid salary format encountered. Treating as lower value.");
                        return -1; // Treat invalid salary as lower
                    }
                }
            });

            // Print the header
            if (header != null) {
                System.out.println(String.join(",", header));
            }

            // Print the top 5 highest-paid employees
            System.out.println("\nTop 5 Highest-Paid Employees:");
            int count = 0;
            for (String[] employee : employeeRecords) {
                if (count < 5) {
                    System.out.println(String.join(",", employee));
                    count++;
                } else {
                    break; // Stop after printing 5 employees
                }
            }
        }
    }

    public class ValidateCSV {
        public static void main(String[] args) {
            String filePath = "employees.csv";
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (!columns[3].matches("\\d+")) {
                        System.out.println("Invalid Salary for: " + columns[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class CSVToObjectConverter {
        public static void main(String[] args) {
            String filePath = "students.csv"; // Replace with your CSV file path
            List<Student> studentList = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean isHeaderRow = true;
                while ((line = br.readLine()) != null) {
                    if (isHeaderRow) {
                        isHeaderRow = false;
                        continue;
                    }
                    String[] columns = line.split(",");
                    if (columns.length == 3) { // Assuming 3 columns: ID, Name, Major
                        String id = columns[0].trim();
                        String name = columns[1].trim();
                        String major = columns[2].trim();
                        Student student = new Student(id, name, major);
                        studentList.add(student);
                    } else {
                        System.err.println("Warning: Skipping line with incorrect number of columns - " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading the CSV file: " + e.getMessage());
                e.printStackTrace();
            }

            // Print the list of Student objects
            for (Student student : studentList) {
                System.out.println(student);
            }
        }
    }


    public class MergeCSVFiles {
        public static void main(String[] args) {
            String file1Path = "students1.csv";
            String file2Path = "students2.csv";
            String mergedFilePath = "merged_students.csv";

            Map<String, String[]> student1Data = new HashMap<>();
            String[] header1 = null;
            String[] header2 = null;

            try (BufferedReader br = new BufferedReader(new FileReader(file1Path))) {
                String line;
                boolean isHeader = true;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (isHeader) {
                        header1 = columns;
                        isHeader = false;
                        continue;
                    }
                    if (columns.length >= 3) {
                        student1Data.put(columns[0].trim(), columns); // ID as key
                    } else {
                        System.err.println("Warning: Skipping invalid line in " + file1Path + " - " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading " + file1Path + ": " + e.getMessage());
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file2Path));
                 BufferedWriter bw = new BufferedWriter(new FileWriter(mergedFilePath))) {

                String line;
                boolean isHeader = true;
                while ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (isHeader) {
                        header2 = columns;
                        // Write merged header
                        bw.write(String.join(",", header1) + "," + String.join(",", header2, 1, header2.length)); // Exclude ID from second header
                        bw.newLine();
                        isHeader = false;
                        continue;
                    }
                    if (columns.length >= 3) {
                        String id = columns[0].trim();
                        if (student1Data.containsKey(id)) {
                            String[] student1Info = student1Data.get(id);
                            bw.write(String.join(",", student1Info) + "," + String.join(",", columns, 1, columns.length)); // Exclude ID from second data
                            bw.newLine();
                        } else {
                            System.err.println("Warning: No matching ID in " + file1Path + " for ID: " + id + " in " + file2Path);
                        }
                    } else {
                        System.err.println("Warning: Skipping invalid line in " + file2Path + " - " + line);
                    }
                }
                System.out.println("Successfully merged " + file1Path + " and " + file2Path + " into " + mergedFilePath);


            } catch (IOException e) {
                System.err.println("Error reading " + file2Path + " or writing to " + mergedFilePath + ": " + e.getMessage());
            }
        }
    }



    public class DuplicateDetector {
        public static void main(String[] args) {
            String filePath = "data_with_duplicates.csv"; // Replace with your CSV file path
            Set<String> uniqueIDs = new HashSet<>();
            Set<String> duplicateRecords = new HashSet<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean isHeaderRow = true;
                while ((line = br.readLine()) != null) {
                    if (isHeaderRow) {
                        isHeaderRow = false;
                        continue;
                    }
                    String[] columns = line.split(",");
                    if (columns.length > 0) {
                        String id = columns[0].trim(); // Assuming ID is the first column
                        if (!uniqueIDs.add(id)) {
                            duplicateRecords.add(line);
                        }
                    } else {
                        System.err.println("Warning: Skipping empty line.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading the CSV file: " + e.getMessage());
                e.printStackTrace();
            }

            if (!duplicateRecords.isEmpty()) {
                System.out.println("Duplicate records found (based on ID):");
                for (String record : duplicateRecords) {
                    System.out.println(record);
                }
            } else {
                System.out.println("No duplicate records found based on ID.");
            }
        }
    }

    public class DatabaseToCSVReport {
        // Database credentials
        static final String DB_URL = "jdbc:mysql://localhost:3306/your_database"; // Replace with your DB URL
        static final String USER = "your_user"; // Replace with your DB username
        static final String PASS = "your_password"; // Replace with your DB password

        public static void main(String[] args) {
            String csvFilePath = "employee_report.csv";

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT employee_id, name, department, salary FROM employees")) {

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath))) {
                    // Write headers
                    bw.write("Employee ID,Name,Department,Salary");
                    bw.newLine();

                    // Write data rows
                    while (rs.next()) {
                        int employeeId = rs.getInt("employee_id");
                        String name = rs.getString("name");
                        String department = rs.getString("department");
                        double salary = rs.getDouble("salary");
                        bw.write(employeeId + "," + name + "," + department + "," + salary);
                        bw.newLine();
                    }
                    System.out.println("Successfully generated CSV report: " + csvFilePath);
                } catch (IOException e) {
                    System.err.println("Error writing to CSV file: " + e.getMessage());
                }

            } catch (SQLException e) {
                System.err.println("Database connection error: " + e.getMessage());
            }
        }
    }




}
