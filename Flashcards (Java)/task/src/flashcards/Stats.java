package flashcards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to track statistics of flashcards.
 */
public class Stats {
    protected final Map<String, Integer> errorCounts;

    public Stats() {
        errorCounts = new HashMap<>();
    }

    public void incrementErrorCount(String term) {
        errorCounts.put(term, errorCounts.getOrDefault(term, 0) + 1);
    }

    public void resetStats() {
        errorCounts.clear();
    }

    public List<String> getHardestCards() {
        List<String> hardestCards = new ArrayList<>();
        int maxErrors = 0;
        for (Map.Entry<String, Integer> entry : errorCounts.entrySet()) {
            int errors = entry.getValue();
            if (errors > maxErrors) {
                maxErrors = errors;
                hardestCards.clear();
                hardestCards.add(entry.getKey());
            } else if (errors == maxErrors && errors > 0) {
                hardestCards.add(entry.getKey());
            }
        }
        return hardestCards;
    }



}
