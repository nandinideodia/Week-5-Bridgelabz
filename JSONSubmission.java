import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Arrays;

public class JSONSubmission {
    public class CreateStudentJson {
        public static void main(String[] args) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode studentJson = mapper.createObjectNode();
            studentJson.put("name", "Alice");
            studentJson.put("age", 20);
            studentJson.putPOJO("subjects", Arrays.asList("Math", "Science", "English"));

            try {
                String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(studentJson);
                System.out.println(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    class Car {
        public String model;
        public String brand;
        public int year;

        public Car(String model, String brand, int year) {
            this.model = model;
            this.brand = brand;
            this.year = year;
        }

        // Default constructor is needed for Jackson
        public Car() {
        }

        public String getModel() {
            return model;
        }

        public String getBrand() {
            return brand;
        }

        public int getYear() {
            return year;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public class ConvertCarToJson {
        public static void main(String[] args) {
            Car myCar = new Car("Sedan", "Toyota", 2022);
            ObjectMapper mapper = new ObjectMapper();

            try {
                String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(myCar);
                System.out.println(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public class ExtractFieldsFromJson {
        public static void main(String[] args) {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File("data.json"); // Replace with your JSON file path

            try {
                JsonNode rootNode = mapper.readTree(jsonFile);

                if (rootNode.isArray()) {
                    for (JsonNode node : rootNode) {
                        JsonNode nameNode = node.get("name");
                        JsonNode emailNode = node.get("email");

                        if (nameNode != null && emailNode != null) {
                            System.out.println("Name: " + nameNode.asText() + ", Email: " + emailNode.asText());
                        }
                    }
                } else if (rootNode.isObject()) {
                    JsonNode nameNode = rootNode.get("name");
                    JsonNode emailNode = rootNode.get("email");
                    if (nameNode != null && emailNode != null) {
                        System.out.println("Name: " + nameNode.asText() + ", Email: " + emailNode.asText());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public class MergeJsonObjects {
        public static void main(String[] args) {
            ObjectMapper mapper = new ObjectMapper();
            String json1 = "{\"name\": \"David\", \"city\": \"New York\"}";
            String json2 = "{\"age\": 35, \"occupation\": \"Engineer\"}";

            try {
                JsonNode node1 = mapper.readTree(json1);
                JsonNode node2 = mapper.readTree(json2);

                ObjectNode mergedNode = mapper.createObjectNode();
                mergedNode.setAll((ObjectNode) node1);
                mergedNode.setAll((ObjectNode) node2);

                String mergedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mergedNode);
                System.out.println(mergedJson);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class ValidateJsonStructure {
        public static void main(String[] args) {
            String jsonString = "{\"name\": \"Eve\", \"age\": 28}";
            String schemaString = "{\n" +
                    "  \"type\": \"object\",\n" +
                    "  \"properties\": {\n" +
                    "    \"name\": {\n" +
                    "      \"type\": \"string\"\n" +
                    "    },\n" +
                    "    \"age\": {\n" +
                    "      \"type\": \"integer\",\n" +
                    "      \"minimum\": 18\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"required\": [\"name\", \"age\"]\n" +
                    "}";

            ObjectMapper mapper = new ObjectMapper();
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.Version7);

            try {
                JsonNode jsonNode = mapper.readTree(jsonString);
                JsonSchema schema = factory.createSchema(schemaString);
                ValidationResult result = schema.validate(jsonNode);

                if (result.getErrors().isEmpty()) {
                    System.out.println("JSON is valid against the schema.");
                } else {
                    System.out.println("JSON is NOT valid against the schema:");
                    result.getErrors().forEach(System.out::println);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    class Product {
        public String name;
        public double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        // Default constructor for Jackson
        public Product() {
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    public class ConvertListToJsonArray {
        public static void main(String[] args) {
            List<Product> products = new ArrayList<>();
            products.add(new Product("Laptop", 1200.00));
            products.add(new Product("Mouse", 25.00));
            products.add(new Product("Keyboard", 75.00));

            ObjectMapper mapper = new ObjectMapper();

            try {
                String jsonArray = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(products);
                System.out.println(jsonArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public class FilterJsonByAge {
        public static void main(String[] args) {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File("data.json"); // Assuming the same data.json as in problem 3

            try {
                JsonNode rootNode = mapper.readTree(jsonFile);

                if (rootNode.isArray()) {
                    for (JsonNode node : rootNode) {
                        JsonNode ageNode = node.get("age");
                        if (ageNode != null && ageNode.asInt() > 25) {
                            System.out.println("User older than 25: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class PrintKeysAndValues {
        public static void main(String[] args) {
            ObjectMapper mapper = new ObjectMapper();
            File jsonFile = new File("sample.json"); // Replace with your JSON file

            try {
                JsonNode rootNode = mapper.readTree(jsonFile);
                printNode(rootNode, "");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void printNode(JsonNode node, String indent) {
            if (node.isObject()) {
                Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    System.out.println(indent + "Key: " + field.getKey());
                    printNode(field.getValue(), indent + "  ");
                }
            } else if (node.isArray()) {
                int index = 0;
                for (JsonNode arrayNode : node) {
                    System.out.println(indent + "Index: " + index++);
                    printNode(arrayNode, indent + "  ");
                }
            } else {
                System.out.println(indent + "Value: " + node.asText());
            }
        }
    }


    public class ValidateEmailField {
        public static void main(String[] args) {
            String jsonString = "{\"email\": \"test@example.com\"}";
            String schemaString = "{\n" +
                    "  \"type\": \"object\",\n" +
                    "  \"properties\": {\n" +
                    "    \"email\": {\n" +
                    "      \"type\": \"string\",\n" +
                    "      \"format\": \"email\"\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"required\": [\"email\"]\n" +
                    "}";

            ObjectMapper mapper = new ObjectMapper();
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.Version7);

            try {
                JsonNode jsonNode = mapper.readTree(jsonString);
                JsonSchema schema = factory.createSchema(schemaString);
                ValidationResult result = schema.validate(jsonNode);

                if (result.getErrors().isEmpty()) {
                    System.out.println("Email is valid.");
                } else {
                    System.out.println("Email is NOT valid:");
                    result.getErrors().forEach(System.out::println);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public class MergeJsonFiles {
        public static void main(String[] args) {
            ObjectMapper mapper = new ObjectMapper();
            File file1 = new File("data1.json"); // Replace with your first JSON file
            File file2 = new File("data2.json"); // Replace with your second JSON file

            try {
                JsonNode node1 = mapper.readTree(file1);
                JsonNode node2 = mapper.readTree(file2);

                ObjectNode mergedNode = mapper.createObjectNode();
                if (node1.isObject()) {
                    mergedNode.setAll((ObjectNode) node1);
                }
                if (node2.isObject()) {
                    mergedNode.setAll((ObjectNode) node2);
                }

                String mergedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mergedNode);
                System.out.println(mergedJson);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
