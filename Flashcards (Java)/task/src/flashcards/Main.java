package flashcards;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the number of cards:");
        int cardsNumber = Integer.parseInt(sc.nextLine());
        Deck deck = new Deck();

        for (int i = 0; i < cardsNumber; i++) {
            String term, definition;
            while (true) {
                System.out.println("The term for card #" + (i + 1) + ":");
                term = sc.nextLine();
                if (deck.cards.containsKey(term)) {
                    System.out.printf("The term \"%s\" already exists. Try again:%n", term);
                    continue;
                }
                break;
            }
            while (true) {

                System.out.println("The definition for card #" + (i + 1) + ":");
                definition = sc.nextLine();
                try {
                    // will throw exception if definition already exists
                    deck.addCard(term, definition);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                break;
            }
        }

        for (Map.Entry<String, String> entry : deck.cards.entrySet()) {
            System.out.printf("Print the definition of \"%s\":%n", entry.getKey());
            String userDefinition = sc.nextLine();
            if (userDefinition.equals(entry.getValue())) {
                System.out.println("Correct!");
            } else if (deck.isDefinitionPresent(userDefinition)) {
                System.out.printf("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".%n", entry.getValue(), deck.getTermByDefinition(userDefinition));
            } else {
                System.out.printf("Wrong. The right answer is \"%s\".%n", entry.getValue());
            }
        }


    }
}
