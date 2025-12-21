package flashcards;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Deck deck = new Deck();

        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            String action = sc.nextLine();
            switch (action) {
                case "add":
                    handleAddAction(sc, deck);
                    break;
                case "remove":
                    handleRemoveAction(sc, deck);
                    break;
                case "import":
                    handleImportAction(sc, deck);
                    break;
                case "export":
                    handleExportAction(sc, deck);
                    break;
                    case "ask":
                        handleAskCommand(sc, deck);
                        break;
                case "exit":
                    System.out.println("Bye bye!");
                    return;
                default:
                    System.out.println("Unknown action. Please try again.");
            }
        }


    }

    private static void handleAskCommand(Scanner sc, Deck deck) {
        System.out.println("How many times to ask?");
        int timesToAsk = Integer.parseInt(sc.nextLine());
        List<String> terms = new ArrayList<>(deck.cards.keySet());
        Random random = new Random();
        for (int i = 0; i < timesToAsk; i++) {
            String term = terms.get(random.nextInt(terms.size()));
            String correctDefinition = deck.cards.get(term);
            System.out.println("Print the definition of \"" + term + "\":");
            String userAnswer = sc.nextLine();
            if (userAnswer.equals(correctDefinition)) {
                System.out.println("Correct!");
            } else if (deck.isDefinitionPresent(userAnswer)) {
                String existingTerm = deck.getTermByDefinition(userAnswer);
                System.out.println("Wrong. The right answer is \"" + correctDefinition + "\", but your definition is correct for \"" + existingTerm + "\".");
            } else {
                System.out.println("Wrong. The right answer is \"" + correctDefinition + "\".");
            }
        }
    }

    private static void handleExportAction(Scanner sc, Deck deck) {
        System.out.println("File name:");
        String exportFileName = sc.nextLine();
        int exportedCount = deck.exportToFile(exportFileName);
        System.out.println(exportedCount + " cards have been saved.");
    }

    private static void handleImportAction(Scanner sc, Deck deck) {
        System.out.println("File name:");
        String importFileName = sc.nextLine();
        int importedCount = deck.importFromFile(importFileName);
        System.out.println(importedCount + " cards have been loaded.");
    }

    private static void handleRemoveAction(Scanner sc, Deck deck) {
        System.out.println("Which card?");
        String termToRemove = sc.nextLine();
        if (deck.cards.containsKey(termToRemove)) {
            deck.cards.remove(termToRemove);
            System.out.println("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + termToRemove + "\": there is no such card.");
        }
    }

    private static void handleAddAction(Scanner sc, Deck deck) {
        System.out.println("The card:");
        String term = sc.nextLine();
        System.out.println("The definition of the card:");
        String definition = sc.nextLine();
        try {
            deck.addCard(term, definition);
            System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
