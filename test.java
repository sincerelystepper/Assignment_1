import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Statement {
    String sentence;
    double confidence;

    public Statement(String sentence, double confidence) {
        this.sentence = sentence;
        this.confidence = confidence;
    }
}

class KnowledgeBase {
    private Map<String, Statement> data;

    public KnowledgeBase() {
        data = new HashMap<>();
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t"); // Split by tabs
                if (parts.length == 3) {
                    String term = parts[0].trim();
                    String sentence = parts[1].trim();
                    double confidence = Double.parseDouble(parts[2].trim());
                    data.put(term, new Statement(sentence, confidence));
                }
            }
            System.out.println("Knowledge base loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public Statement getStatement(String term) {
        return data.get(term);
    }

    public void addOrUpdateStatement(String term, String sentence, double confidence) {
        data.put(term, new Statement(sentence, confidence));
        System.out.println("Statement for term '" + term + "' has been updated.");
    }
}

public class dataSet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        KnowledgeBase kb = new KnowledgeBase();

        while (true) {
            System.out.println("\nChoose an action from the menu:");
            System.out.println("1. Load a knowledge base from a file");
            System.out.println("2. Add a new statement to the knowledge base");
            System.out.println("3. Search for an item in the knowledge base by term");
            System.out.println("4. Search for an item in the knowledge base by term and sentence");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter file name: ");
                    String filename = scanner.nextLine();
                    kb.loadFromFile(filename);
                    break;
                case 2:
                    System.out.print("Enter the term: ");
                    String newTerm = scanner.nextLine();
                    System.out.print("Enter the statement: ");
                    String newSentence = scanner.nextLine();
                    System.out.print("Enter the confidence score: ");
                    double newConfidence = scanner.nextDouble();
                    kb.addOrUpdateStatement(newTerm, newSentence, newConfidence);
                    break;
                case 3:
                System.out.print("Enter the term to search: ");
                String searchTerm = scanner.nextLine();
                Statement statement = kb.getStatement(searchTerm);
                if (statement != null) {
                    System.out.println("Statement found: " + statement.sentence);
                    System.out.println("Confidence score: " + statement.confidence);
                } else {
                    System.out.println("No statement found for term '" + searchTerm + "'");
                }

                    break;
                case 4:
                System.out.print("Enter the term: ");
                String term = scanner.nextLine();
                System.out.print("Enter the statement to search for: ");
                String searchStatement = scanner.nextLine();
                Statement foundStatement = kb.getStatement(term);
                if (foundStatement != null && foundStatement.sentence.equals(searchStatement)) {
                    System.out.println("The statement was found and has a confidence score of " + foundStatement.confidence);
                } else {
                    System.out.println("No matching statement found for term '" + term + "' and sentence '" + searchStatement + "'");
                }

                    break;
                case 5:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
