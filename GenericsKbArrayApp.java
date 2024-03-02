import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.Scanner;

public class GenericsKbArrayApp {
    

    public static final int MAX_CAPACITY = 50100;

    private KnowledgeEntry[] knowledgeBase;
    private int size; // number of entries in the knowledge base

    public GenericsKbArrayApp() {
        knowledgeBase = new KnowledgeEntry[MAX_CAPACITY];
        size = 0; // initially, the knowledge base is empty
    }

    public void loadKnowledgeBase(String filePath) {
        //try (Stream<String> fileStream = Files.lines(Paths.get(filePath))) {
            try {
            //System.out.println("START");
            Stream<String> fileStream = Files.lines(Paths.get(filePath));
            //System.out.println("END");
            int noOfLines = (int) fileStream.count();
            
            System.out.println("number of lines: " + noOfLines);
            if (noOfLines > MAX_CAPACITY) {
                System.err.println("Too many entries in the knowledge base. Adjust MAX_CAPACITY.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\t"); // Assuming tab-separated values
                    if (parts.length == 3) {
                        String term = parts[0].trim();
                        String statement = parts[1].trim();
                        double confidence = Double.parseDouble(parts[2].trim());

                        knowledgeBase[size] = new KnowledgeEntry(term, statement, confidence);
                        size++;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading the knowledge base file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error counting lines in the knowledge base file: " + e.getMessage());
        }
    }    public void updateStatement(String term, String newStatement) {
        for (int i = 0; i < size; i++) {
            if (knowledgeBase[i].getTerm().equals(term)) {
                knowledgeBase[i].setStatement(newStatement);
                return; // Found and updated, exit loop
            }
        }
        // Term not found, handle accordingly (e.g., add a new entry or show an error message)
    }

    public String getStatement(String term) {
        for (int i = 0; i < size; i++) {
            if (knowledgeBase[i].getTerm().equals(term)) {
                return knowledgeBase[i].getStatement() + " " + knowledgeBase[i].getConfidence();
            }
        }
        return "Term not found in the knowledge base";
    }

    // Inner class representing a knowledge entry
    private static class KnowledgeEntry {
        private String term;
        private String statement;
        private double confidence;

        public KnowledgeEntry(String term, String statement, double confidence) {
            this.term = term;
            this.statement = statement;
            this.confidence = confidence;
        }

        public String getTerm() {
            return term;
        }

        public String getStatement() {
            return statement;
        }
        
        public double getConfidence() {
            return confidence;
        }

        public void setStatement(String newStatement) {
            this.statement = newStatement;
        }
    }

    public static void main(String[] args) {
        GenericsKbArrayApp kbApp = new GenericsKbArrayApp();
        kbApp.loadKnowledgeBase("GenericsKB.txt"); // Replace with actual file path

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a term to search: ");
        String termToSearch = scanner.nextLine();

        String statement = kbApp.getStatement(termToSearch);
        System.out.println("Statement for term " + termToSearch + ": " + statement);
    }
}
