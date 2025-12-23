package flashcards;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Deck deck = new Deck();
        Stats stats = new Stats();
        Logger logger = new Logger();

        while (true) {
            String msg = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
            logger.log(msg);
            String action = sc.nextLine();
            logger.log(action);
            switch (action) {
                case "add":
                    handleAddAction(sc, deck, logger);
                    break;
                case "remove":
                    handleRemoveAction(sc, deck, logger);
                    break;
                case "import":
                    handleImportAction(sc, deck, logger);
                    break;
                case "export":
                    handleExportAction(sc, deck, logger);
                    break;
                case "ask":
                    handleAskCommand(sc, deck, stats, logger);
                    break;
                case "exit":
                    logger.log("Bye bye!");
                    return;
                case "log":
                    handleLogAction(sc, logger);
                    break;
                case "hardest card":
                    handleHardestCard(stats, logger);
                    break;
                case "reset stats":
                    stats.resetStats();
                    logger.log("Card statistics have been reset.");
                default:
                    logger.log("Unknown action. Please try again.");
            }
        }


    }

    private static void handleLogAction(Scanner sc, Logger logger) {
        String logContent = logger.getLog();
        System.out.println("File name:");
        String logFileName = sc.nextLine();
        try {
            Files.writeString(Path.of(logFileName), logContent);
        } catch (java.io.IOException e) {
            logger.log("An error occurred while saving the log.");
        }
        logger.log("The log has been saved.");

    }

    private static void handleHardestCard(Stats stats, Logger logger) {
        List<String> hardestCards = stats.getHardestCards();
        if (hardestCards.size() == 1) {
            String term = hardestCards.getFirst();
            int errors = stats.errorCounts.get(term);
            String msg = "The hardest card is \"%s\". You have %d errors answering it.%n".formatted(term, errors);
            logger.log(msg);
        } else if (hardestCards.size() > 1) {
            StringBuilder terms = new StringBuilder();
            for (int i = 0; i < hardestCards.size(); i++) {
                terms.append("\"").append(hardestCards.get(i)).append("\"");
                if (i < hardestCards.size() - 1) {
                    terms.append(", ");
                }
            }
            int errors = stats.errorCounts.get(hardestCards.getFirst());
            String msg = "The hardest card is \"%s\". You have %d errors answering it.%n".formatted(terms, errors);
            logger.log(msg);
        } else {
            logger.log("There are no cards with errors.");
        }
    }

    private static void handleAskCommand(Scanner sc, Deck deck, Stats stats, Logger logger) {
        logger.log("How many times to ask?");
        int timesToAsk = Integer.parseInt(sc.nextLine());
        List<String> terms = new ArrayList<>(deck.cards.keySet());
        Random random = new Random();
        for (int i = 0; i < timesToAsk; i++) {
            String term = terms.get(random.nextInt(terms.size()));
            String correctDefinition = deck.cards.get(term);
            logger.log("Print the definition of \"" + term + "\":");
            String userAnswer = sc.nextLine();
            if (userAnswer.equals(correctDefinition)) {
                logger.log("Correct!");
            } else if (deck.isDefinitionPresent(userAnswer)) {
                stats.incrementErrorCount(term);
                String existingTerm = deck.getTermByDefinition(userAnswer);
                String msg = "Wrong. The right answer is \"" + correctDefinition + "\", but your definition is correct for \"" + existingTerm + "\".";
                logger.log(msg);
            } else {
                stats.incrementErrorCount(term);
                logger.log("Wrong. The right answer is \"" + correctDefinition + "\".");
            }
        }
    }

    private static void handleExportAction(Scanner sc, Deck deck, Logger logger) {
        logger.log("File name:");
        String exportFileName = sc.nextLine();
        int exportedCount = deck.exportToFile(exportFileName);
        logger.log(exportedCount + " cards have been saved.");
    }

    private static void handleImportAction(Scanner sc, Deck deck, Logger logger) {
        logger.log("File name:");
        String importFileName = sc.nextLine();
        int importedCount = deck.importFromFile(importFileName);
        logger.log(importedCount + " cards have been loaded.");
    }

    private static void handleRemoveAction(Scanner sc, Deck deck, Logger logger) {
        logger.log("Which card?");
        String termToRemove = sc.nextLine();
        if (deck.cards.containsKey(termToRemove)) {
            deck.cards.remove(termToRemove);
            logger.log("The card has been removed.");
        } else {
            logger.log("Can't remove \"" + termToRemove + "\": there is no such card.");
        }
    }

    private static void handleAddAction(Scanner sc, Deck deck, Logger logger) {
        logger.log("The card:");
        String term = sc.nextLine();
        logger.log("The definition of the card:");
        String definition = sc.nextLine();
        try {
            deck.addCard(term, definition);
            logger.log("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
        } catch (IllegalArgumentException e) {
            logger.log(e.getMessage());
        }
    }
}
