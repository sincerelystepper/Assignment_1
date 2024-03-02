public class GenericsKbArrayApp {

    public static final int MAX_CAPACITY = 100;

    private KnowledgeEntry[] knowledgeBase;
    private int size; // number of entries in the knowledge base

    public GenericsKbArrayApp() {
        knowledgeBase = new KnowledgeEntry[MAX_CAPACITY];
        size = 0; // initially, the knowledge base is empty
    }

    public void loadKnowledgeBase(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String [] parts = line.split("\t"); // Assuming tab separated values
                if (parts.length == 2) {
                    String term = parts[0].trim();
                    String statement = parts[1].trim();
                    terms[size] = term;
                    statements[size] = statement;
                    size++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the knowledge base file: " + e.getMessage());            
        }

    }

    public void updateStatement(String term, String newStatement) {
        for (int i = 0; i < size; i++) {
            if (knowledgeBase[i].getTerm().equals(term)) {
                knowledgeBase[i].setStatement(newStatement);
                return; // Found and updated, exit loop
            }
        }
        return "Statement not updated";
    
    }

    public String getStatement(String term) {
        for (int i = 0; i < size; i++) {
            if (knowledgeBase[i].getTerm().equals(term)) {
                return knowledgeBase[i].getStatement();
            }
        }
        return "Term not found in the knowledge base";
    }

    // Other methods and members go here

    // Example inner class representing a knowledge entry
    private static class KnowledgeEntry {
        private String term;
        private String statement;

        public KnowledgeEntry(String term, String statement) {
            this.term = term;
            this.statement = statement;
        }

        public String getTerm() {
            return term;
        }

        public String getStatement() {
            return statement;
        }

        public void setStatement(String newStatement) {
            this.statement = newStatement;
        }
    }

    public static void main(String[] args) {
        GenericsKbArrayApp kbApp = new GenericsKbArrayApp();
        kbApp.loadKnowledgeBase("GenericsKB.txt");

        String termToSearch = "Java"; // Replace with other words to search
        String statement = kbApp.getStatement(termToSearch);
        System.out.println("Statement for term " + termToSearch + ": " + statement);
    }
}
