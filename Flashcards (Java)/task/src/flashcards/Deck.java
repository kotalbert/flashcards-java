package flashcards;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Deck {
    protected final Map<String, String> cards;

    public Deck() {
        cards = new HashMap<>();
    }

    public void addCard(String term, String definition) {
        if (cards.containsKey(term)) {
            throw new IllegalArgumentException("The term \"" + term + "\" already exists. Try again.");
        }
        if (isDefinitionPresent(definition)) {
            String existingTerm = getTermByDefinition(definition);
            String errorMessage = String.format("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".", definition, existingTerm);
            throw new IllegalArgumentException(errorMessage);
        }
        cards.put(term, definition);
    }

    protected String getTermByDefinition(String definition) {
        for (Map.Entry<String, String> entry : cards.entrySet()) {
            if (entry.getValue().equals(definition)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Check if definition already present in values of cards map.
     *
     * @param definition definition to check
     * @return term for the given definition
     */
    protected boolean isDefinitionPresent(String definition) {
        for (String value : cards.values()) {
            if (value.equals(definition)) {
                return true;
            }
        }
        return false;

    }


    public int importFromFile(String importFileName) {
        try {
            Path path = Path.of(importFileName);
            String[] lines = java.nio.file.Files.readAllLines(path).toArray(new String[0]);
            for (String line : lines) {
                String[] parts = line.split(":");
                String term = parts[0];
                String definition = parts[1];
                cards.put(term, definition);
            }
            return lines.length;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }

    public int exportToFile(String exportFileName) {
        try {
            Path path = Path.of(exportFileName);
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : cards.entrySet()) {
                sb.append(entry.getKey()).append(":").append(entry.getValue()).append(System.lineSeparator());
            }
            java.nio.file.Files.writeString(path, sb.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return 0;
    }
}
