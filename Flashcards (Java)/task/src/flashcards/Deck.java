package flashcards;

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



}
