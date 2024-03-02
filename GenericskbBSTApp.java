import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Statement {
    String item;
    String statement;

    public Statement(String item, String statement) {
        this.item = item;
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }
}

class TreeNode {
    Statement statement;
    TreeNode left;
    TreeNode right;

    public TreeNode(Statement statement) {
        this.statement = statement;
        this.left = null;
        this.right = null;
    }
}

public class GenericskbBSTApp {
    private TreeNode root;

    public GenericskbBSTApp() {
        root = null;
    }

    public void insertItem(String item) {
        root = insertItemRecursive(root, item);
    }

    private TreeNode insertItemRecursive(TreeNode node, String item) {
        if (node == null) {
            return new TreeNode(new Statement(item, ""));
        }
        if (item.compareTo(node.statement.item) < 0) {
            node.left = insertItemRecursive(node.left, item);
        } else if (item.compareTo(node.statement.item) > 0) {
            node.right = insertItemRecursive(node.right, item);
        }
        return node;
    }

    public String searchItem(String item) {
        return searchItemRecursive(root, item);
    }

    private String searchItemRecursive(TreeNode node, String item) {
        if (node == null) {
            return null;
        }
        if (node.statement.item.equals(item)) {
            return node.statement.statement;
        } else if (item.compareTo(node.statement.item) < 0) {
            return searchItemRecursive(node.left, item);
        } else {
            return searchItemRecursive(node.right, item);
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("GenericsKB.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String item = parts[0].trim();
                    String statement = parts[1].trim();
                    insertItem(item);
                    // You can also store the statement if needed
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
    }

    public static void main(String[] args) {
        GenericskbBSTApp kbApp = new GenericskbBSTApp();

        // Load data from an external file (e.g., "knowledge_base.txt")
        kbApp.loadFromFile("GenericsKB.txt");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a term to search: ");
        String termToSearch = scanner.nextLine();

        //String statement = kbApp.getStatement();
        //System.out.println("Statement for term: " + termToSearch + " " + statement);

        System.out.println("file loaded successfully: ");
        // Search for items
        System.out.println(kbApp.searchItem("criminologist")); // Output: null (no statement yet)
        System.out.println("Not reached yet: ");
    }
}
