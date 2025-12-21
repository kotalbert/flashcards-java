package flashcards;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the number of cards:");
        int cardsNumber = Integer.parseInt(sc.nextLine());
        List<Card> cards = new ArrayList<>();
        Set<String> terms = new HashSet<>();
        Set<String> definitions = new HashSet<>();

        for (int i = 0; i < cardsNumber; i++) {
            String term, definition;
            while (true) {
                System.out.println("Card #" + (i + 1) + ":");
                term = sc.nextLine();
                if (terms.contains(term)) {
                    System.out.printf("The term \"%s\" already exists. Try again:%n", term);
                    continue;
                }
                terms.add(term);
                break;
            }
            while (true) {

                System.out.println("The definition for card #" + (i + 1) + ":");
                definition = sc.nextLine();
                if (definitions.contains(definition)) {
                    System.out.printf("The definition \"%s\" already exists. Try again:%n", definition);
                    continue;
                }
                definitions.add(definition);
                break;
            }
            if (!term.isEmpty() && !definition.isEmpty()) {
                Card card = new Card(term, definition);
                cards.add(card);
            }
        }

        for (Card card : cards) {
            System.out.printf("Print the definition of \"%s\":%n", card.term());
            String userDefinition = sc.nextLine();
            if (userDefinition.equals(card.definition())) {
                System.out.println("Correct!");
            } else {
                System.out.printf("Wrong. The right answer is \"%s\".%n", card.definition());
            }
        }


    }
}
