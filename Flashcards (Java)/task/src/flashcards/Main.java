package flashcards;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Deck deck = new Deck();
        Stats stats = new Stats();
        Logger log = new Logger();

        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
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
                    handleAskCommand(sc, deck, stats);
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    return;
                case "log":
                    handleLogAction(sc, log);
                    System.out.println("The log has been saved.");
                    break;
                case "hardest card":
                    handleHardestCard(sc, stats);
                    break;
                case "reset stats":
                    stats.resetStats();
                    System.out.println("Card statistics have been reset.");
                default:
                    System.out.println("Unknown action. Please try again.");
            }
        }


    }

    private static void handleLogAction(Scanner sc, Logger log) {
        String logContent = log.getLog();
        System.out.println("File name:");
        String logFileName = sc.nextLine();
        try {
            Files.writeString(Path.of(logFileName), logContent);
        } catch (java.io.IOException e) {
            System.out.println("An error occurred while saving the log.");
        }
    }

    private static void handleHardestCard(Scanner sc, Stats stats) {
        List<String> hardestCards = stats.getHardestCards();
        if (hardestCards.size() == 1) {
            String term = hardestCards.getFirst();
            int errors = stats.errorCounts.get(term);
            System.out.printf("The hardest card is \"%s\". You have %d errors answering it.%n", term, errors);
            return;
        } else if (hardestCards.size() > 1) {
            StringBuilder terms = new StringBuilder();
            for (int i = 0; i < hardestCards.size(); i++) {
                terms.append("\"").append(hardestCards.get(i)).append("\"");
                if (i < hardestCards.size() - 1) {
                    terms.append(", ");
                }
            }
            int errors = stats.errorCounts.get(hardestCards.getFirst());
            System.out.printf("The hardest card is \"%s\". You have %d errors answering it.%n", terms, errors);
            return;
        } else {
            System.out.println("There are no cards with errors.");
        }
    }

    private static void handleAskCommand(Scanner sc, Deck deck, Stats stats) {
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
                stats.incrementErrorCount(term);
                String existingTerm = deck.getTermByDefinition(userAnswer);
                System.out.println("Wrong. The right answer is \"" + correctDefinition + "\", but your definition is correct for \"" + existingTerm + "\".");
            } else {
                stats.incrementErrorCount(term);
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
