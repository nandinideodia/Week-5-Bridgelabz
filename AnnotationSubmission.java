public class AnnotationSubmission {
    // Exercise 1: Use @Override Correctly
    class Animal {
        public void makeSound() {
            System.out.println("Generic animal sound");
        }
    }

    class Dog extends Animal {
        @Override
        public void makeSound() {
            System.out.println("Woof!");
        }
    }

    public class OverrideExample {
        public static void main(String[] args) {
            Dog myDog = new Dog();
            myDog.makeSound(); // Output: Woof!
        }
    }

    // Exercise 2: Use @Deprecated to Mark an Old Method
    class LegacyAPI {
        @Deprecated
        public void oldFeature() {
            System.out.println("Using the old feature");
        }

        public void newFeature() {
            System.out.println("Using the new and improved feature");
        }
    }

    public class DeprecatedExample {
        public static void main(String[] args) {
            LegacyAPI api = new LegacyAPI();
            api.oldFeature(); // Compiler will likely show a warning for using a deprecated method
            api.newFeature();
        }
    }

import java.util.ArrayList;
import java.util.List;

    // Exercise 3: Suppress Unchecked Warnings
    public class SuppressWarningExample {
        @SuppressWarnings("unchecked")
        public static void main(String[] args) {
            List list = new ArrayList(); // No generics, will cause an unchecked warning
            list.add("Hello");
            list.add(123);
            System.out.println(list);
        }
    }

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

    // Exercise 4: Create a Custom Annotation and Use It
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface TaskInfo {
        String priority();
        String assignedTo();
    }

    class TaskManager {
        @TaskInfo(priority = "HIGH", assignedTo = "Alice")
        public void processData() {
            System.out.println("Processing data...");
        }

        @TaskInfo(priority = "MEDIUM", assignedTo = "Bob")
        public void generateReport() {
            System.out.println("Generating report...");
        }
    }

    public class CustomAnnotationExample {
        public static void main(String[] args) throws NoSuchMethodException, SecurityException {
            Class<TaskManager> clazz = TaskManager.class;

            Method processDataMethod = clazz.getMethod("processData");
            TaskInfo processDataAnnotation = processDataMethod.getAnnotation(TaskInfo.class);
            if (processDataAnnotation != null) {
                System.out.println("Task: processData, Priority: " + processDataAnnotation.priority() + ", Assigned To: " + processDataAnnotation.assignedTo());
            }

            Method generateReportMethod = clazz.getMethod("generateReport");
            TaskInfo generateReportAnnotation = generateReportMethod.getAnnotation(TaskInfo.class);
            if (generateReportAnnotation != null) {
                System.out.println("Task: generateReport, Priority: " + generateReportAnnotation.priority() + ", Assigned To: " + generateReportAnnotation.assignedTo());
            }
        }
    }

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;

    // Exercise 5: Create and Use a Repeatable Annotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(BugReports.class)
    @interface BugReport {
        String description();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface BugReports {
        BugReport[] value();
    }

    class BuggyClass {
        @BugReport(description = "NullPointerException on input A")
        @BugReport(description = "Incorrect calculation for negative values")
        public int calculate(int a, int b) {
            if (a < 0) {
                return a + b;
            }
            // Simulate a potential NullPointerException if 'a' is not handled properly elsewhere
            // String s = null;
            // if (a == 1) s.length();
            return a * b;
        }
    }

    public class RepeatableAnnotationExample {
        public static void main(String[] args) throws NoSuchMethodException, SecurityException {
            Class<BuggyClass> clazz = BuggyClass.class;
            Method calculateMethod = clazz.getMethod("calculate", int.class, int.class);

            BugReport[] bugReports = calculateMethod.getAnnotationsByType(BugReport.class);
            System.out.println("Bug Reports for calculate method:");
            for (BugReport report : bugReports) {
                System.out.println("- " + report.description());
            }
        }
    }

// Practice Problems for Custom Annotations in Java - Beginner Level

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

    // 1️⃣ Create an Annotation to Mark Important Methods
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface ImportantMethod {
        String level() default "HIGH";
    }

    class UtilityClass {
        @ImportantMethod
        public void performCriticalTask() {
            System.out.println("Performing a critical task.");
        }

        @ImportantMethod(level = "MEDIUM")
        public void performSecondaryTask() {
            System.out.println("Performing a secondary task.");
        }

        public void performRegularTask() {
            System.out.println("Performing a regular task.");
        }
    }

    public class ImportantMethodAnnotation {
        public static void main(String[] args) throws NoSuchMethodException, SecurityException {
            Class<UtilityClass> clazz = UtilityClass.class;

            for (Method method : clazz.getDeclaredMethods()) {
                ImportantMethod important = method.getAnnotation(ImportantMethod.class);
                if (important != null) {
                    System.out.println("Method: " + method.getName() + ", Importance Level: " + important.level());
                }
            }
        }
    }

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

    // 2️⃣ Create a @Todo Annotation for Pending Tasks
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Todo {
        String task();
        String assignedTo();
        String priority() default "MEDIUM";
    }

    class FeatureDevelopment {
        @Todo(task = "Implement user authentication", assignedTo = "Alice", priority = "HIGH")
        public void authenticateUser() {
            System.out.println("User authentication logic goes here.");
        }

        @Todo(task = "Design database schema", assignedTo = "Bob")
        public void designDatabase() {
            System.out.println("Database schema design logic goes here.");
        }

        @Todo(task = "Write unit tests for core module", assignedTo = "Charlie", priority = "LOW")
        public void writeUnitTests() {
            System.out.println("Unit tests implementation goes here.");
        }

        public void deployApplication() {
            System.out.println("Application deployment logic.");
        }
    }

    public class TodoAnnotationExample {
        public static void main(String[] args) throws NoSuchMethodException, SecurityException {
            Class<FeatureDevelopment> clazz = FeatureDevelopment.class;

            for (Method method : clazz.getDeclaredMethods()) {
                Todo todo = method.getAnnotation(Todo.class);
                if (todo != null) {
                    System.out.println("Task: " + todo.task() + ", Assigned To: " + todo.assignedTo() + ", Priority: " + todo.priority() + " (Method: " + method.getName() + ")");
                }
            }
        }
    }

// Practice Problems for Custom Annotations in Java - Intermediate Level

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

    // 3️⃣ Create an Annotation for Logging Method Execution Time
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface LogExecutionTime {
    }

    interface PerformanceService {
        @LogExecutionTime
        void task1();

        @LogExecutionTime
        String task2(String input);

        void task3();
    }

    class PerformanceServiceImpl implements PerformanceService {
        @Override
        public void task1() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Task 1 executed.");
        }

        @Override
        public String task2(String input) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Processed: " + input;
        }

        @Override
        public void task3() {
            System.out.println("Task 3 executed.");
        }
    }

    class ExecutionTimeInvocationHandler implements InvocationHandler {
        private final Object target;

        public ExecutionTimeInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(LogExecutionTime.class)) {
                long startTime = System.nanoTime();
                Object result = method.invoke(target, args);
                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                System.out.println("Method " + method.getName() + " executed in " + duration + " nanoseconds.");
                return result;
            } else {
                return method.invoke(target, args);
            }
        }
    }

    public class LogExecutionTimeAnnotation {
        public static void main(String[] args) {
            PerformanceService service = new PerformanceServiceImpl();
            PerformanceService proxyService = (PerformanceService) Proxy.newProxyInstance(
                    PerformanceService.class.getClassLoader(),
                    new Class[]{PerformanceService.class},
                    new ExecutionTimeInvocationHandler(service)
            );

            proxyService.task1();
            proxyService.task2("Data");
            proxyService.task3();
        }
    }

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

    // 4️⃣ Create a @MaxLength Annotation for Field Validation
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MaxLength {
        int value();
    }

    class User {
        @MaxLength(15)
        private String username;

        public User(String username) {
            setUsername(username);
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            if (username != null && username.length() > getClass().getDeclaredFields()[0].getAnnotation(MaxLength.class).value()) {
                throw new IllegalArgumentException("Username exceeds maximum length of " + getClass().getDeclaredFields()[0].getAnnotation(MaxLength.class).value());
            }
            this.username = username;
        }
    }

    public class MaxLengthAnnotationExample {
        public static void main(String[] args) {
            try {
                User user1 = new User("shortUser");
                System.out.println("User 1 created: " + user1.getUsername());

                User user2 = new User("thisusernameistoolong"); // Should throw an exception
                System.out.println("User 2 created: " + user2.getUsername());

            } catch (IllegalArgumentException e) {
                System.err.println("Error creating user: " + e.getMessage());
            }
        }
    }

// Practice Problems for Custom Annotations in Java - Advanced Level

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

    // 5️⃣ Implement a Role-Based Access Control with @RoleAllowed
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface RoleAllowed {
        String value();
    }

    interface AdminOperations {
        @RoleAllowed("ADMIN")
        void performAdminTask();

        void viewPublicInfo();
    }

    class AdminOperationsImpl implements AdminOperations {
        @Override
        public void performAdminTask() {
            System.out.println("Admin task performed.");
        }

        @Override
        public void viewPublicInfo() {
            System.out.println("Viewing public information.");
        }
    }

    class RoleBasedAccessControlHandler implements InvocationHandler {
        private final Object target;
        private final String currentUserRole;

        public RoleBasedAccessControlHandler(Object target, String currentUserRole) {
            this.target = target;
            this.currentUserRole = currentUserRole;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RoleAllowed roleAllowedAnnotation = target.getClass().getAnnotation(RoleAllowed.class);
            if (roleAllowedAnnotation != null) {
                String allowedRole = roleAllowedAnnotation.value();
                if (!currentUserRole.equals(allowedRole) && method.isAnnotationPresent(RoleAllowed.class)) {
                    System.out.println("Access Denied for user with role: " + currentUserRole + " to method: " + method.getName());
                    return null; // Or throw an exception
                }
            }
            return method.invoke(target, args);
        }
    }

    public class RoleBasedAccessControlAnnotation {
        public static void main(String[] args) {
            AdminOperations adminOps = new AdminOperationsImpl();

            // Simulate an ADMIN user
            AdminOperations adminProxy = (AdminOperations) Proxy.newProxyInstance(
                    AdminOperations.class.getClassLoader(),
                    new Class[]{AdminOperations.class},
                    new RoleBasedAccessControlHandler(adminOps, "ADMIN")
            );

            adminProxy.performAdminTask(); // Allowed
            adminProxy.viewPublicInfo(); // Allowed

            System.out.println("---");

            // Simulate a REGULAR user
            AdminOperations regularUserProxy = (AdminOperations) Proxy.newProxyInstance(
                    AdminOperations.class.getClassLoader(),
                    new Class[]{AdminOperations.class},
                    new RoleBasedAccessControlHandler(adminOps, "REGULAR")
            );

            regularUserProxy.performAdminTask(); // Access Denied!
            regularUserProxy.viewPublicInfo(); // Allowed
        }
    }

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

    // 6️⃣ Implement a Custom Serialization Annotation @JsonField
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface JsonField {
        String name() default "";
    }

    class UserSerialization {
        @JsonField(name = "user_name")
        private String username;
        @JsonField
        private int age;
        private String password; // Not annotated, should not be serialized

        public UserSerialization(String username, int age, String password) {
            this.username = username;
            this.age = age;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public int getAge() {
            return age;
        }

        public String getPassword() {
            return password;
        }

        public String toJson() throws IllegalAccessException {
            Map<String, Object> jsonMap = new HashMap<>();
            Class<?> clazz = this.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(JsonField.class)) {
                    field.setAccessible(true);
                    JsonField jsonField = field.getAnnotation(JsonField.class);
                    String key = jsonField.name().isEmpty() ? field.getName() : jsonField.name();
                    jsonMap.put(key, field.get(this));
                }
            }
            StringBuilder jsonBuilder = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
                if (!first) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("\"").append(entry.getKey()).append("\":\"");
                jsonBuilder.append(entry.getValue()).append("\"");
                first = false;
            }
            jsonBuilder.append("}");
            return jsonBuilder.toString();
        }
    }


    _________________________________________________________________________

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;

// Beginner Level

    // Get Class Information
    public class ClassInfo {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the class name (fully qualified): ");
            String className = scanner.nextLine();

            try {
                Class<?> clazz = Class.forName(className);
                System.out.println("\nInformation for class: " + clazz.getName());

                System.out.println("\nConstructors:");
                for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                    System.out.println("  " + constructor);
                }

                System.out.println("\nFields:");
                for (Field field : clazz.getDeclaredFields()) {
                    System.out.println("  " + Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName());
                }

                System.out.println("\nMethods:");
                for (Method method : clazz.getDeclaredMethods()) {
                    System.out.println("  " + Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getSimpleName() + " " + method.getName() + Arrays.toString(method.getParameterTypes()));
                }

            } catch (ClassNotFoundException e) {
                System.err.println("Class not found: " + className);
            }
            scanner.close();
        }
    }

    // Access Private Field
    class Person {
        private int age = 30;

        public int getPublicAge() {
            return age;
        }
    }

    public class AccessPrivateField {
        public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
            Person person = new Person();
            Class<?> personClass = person.getClass();
            Field ageField = personClass.getDeclaredField("age");
            ageField.setAccessible(true); // Allow access to private field

            System.out.println("Original age: " + person.getPublicAge());

            ageField.setInt(person, 35); // Modify the private field
            System.out.println("Modified age (via Reflection): " + person.getPublicAge());

            int retrievedAge = (int) ageField.get(person); // Retrieve the private field value
            System.out.println("Retrieved age (via Reflection): " + retrievedAge);
        }
    }

    // Invoke Private Method
    class Calculator {
        private int multiply(int a, int b) {
            return a * b;
        }
    }

    public class InvokePrivateMethod {
        public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Calculator calculator = new Calculator();
            Class<?> calculatorClass = calculator.getClass();
            Method multiplyMethod = calculatorClass.getDeclaredMethod("multiply", int.class, int.class);
            multiplyMethod.setAccessible(true); // Allow access to private method

            int result = (int) multiplyMethod.invoke(calculator, 5, 3);
            System.out.println("Result of private multiply(5, 3) (via Reflection): " + result);
        }
    }

    // Dynamically Create Objects
    class Student {
        private String name;
        private int rollNumber;

        public Student() {
            System.out.println("Student object created with default constructor.");
        }

        public Student(String name, int rollNumber) {
            this.name = name;
            this.rollNumber = rollNumber;
            System.out.println("Student object created with name: " + name + ", rollNumber: " + rollNumber);
        }

        public String getName() {
            return name;
        }

        public int getRollNumber() {
            return rollNumber;
        }
    }

    public class DynamicObjectCreation {
        public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            String className = "Student";
            Class<?> studentClass = Class.forName(className);

            // Create instance using the default constructor
            Student student1 = (Student) studentClass.newInstance();
            System.out.println("Student 1: " + student1.getName() + ", " + student1.getRollNumber());

            // Create instance using a specific constructor
            Constructor<?> constructor = studentClass.getDeclaredConstructor(String.class, int.class);
            Student student2 = (Student) constructor.newInstance("Alice", 101);
            System.out.println("Student 2: " + student2.getName() + ", " + student2.getRollNumber());
        }
    }

// Intermediate Level

    // Dynamic Method Invocation
    class MathOperations {
        public int add(int a, int b) {
            return a + b;
        }

        public int subtract(int a, int b) {
            return a - b;
        }

        public int multiply(int a, int b) {
            return a * b;
        }
    }

    public class DynamicMethodInvocation {
        public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Scanner scanner = new Scanner(System.in);
            MathOperations mathOps = new MathOperations();
            Class<?> mathOpsClass = mathOps.getClass();

            System.out.print("Enter the operation (add, subtract, multiply): ");
            String operation = scanner.nextLine();
            System.out.print("Enter the first number: ");
            int num1 = scanner.nextInt();
            System.out.print("Enter the second number: ");
            int num2 = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                Method method = mathOpsClass.getMethod(operation, int.class, int.class);
                int result = (int) method.invoke(mathOps, num1, num2);
                System.out.println("Result of " + operation + "(" + num1 + ", " + num2 + "): " + result);
            } catch (NoSuchMethodException e) {
                System.err.println("Method not found: " + operation);
            }
            scanner.close();
        }
    }

    // Retrieve Annotations at Runtime
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Author {
        String name();
    }

    @Author(name = "John Doe")
    class Document {
        // ...
    }

    public class RetrieveAnnotations {
        public static void main(String[] args) {
            Class<?> documentClass = Document.class;
            Annotation[] annotations = documentClass.getAnnotations();

            for (Annotation annotation : annotations) {
                if (annotation instanceof Author) {
                    Author author = (Author) annotation;
                    System.out.println("Author of " + documentClass.getSimpleName() + ": " + author.name());
                }
            }
        }
    }

    // Access and Modify Static Fields
    class Configuration {
        private static String API_KEY = "initial_api_key";

        public static String getApiKey() {
            return API_KEY;
        }
    }

    public class AccessModifyStaticField {
        public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
            Class<?> configClass = Configuration.class;
            Field apiKeyField = configClass.getDeclaredField("API_KEY");
            apiKeyField.setAccessible(true); // Allow access to private static field

            System.out.println("Original API Key: " + Configuration.getApiKey());

            apiKeyField.set(null, "new_secret_key"); // Modify the static field (null for static fields)
            System.out.println("Modified API Key (via Reflection): " + Configuration.getApiKey());

            String retrievedApiKey = (String) apiKeyField.get(null); // Retrieve the static field value
            System.out.println("Retrieved API Key (via Reflection): " + retrievedApiKey);
        }
    }



}
