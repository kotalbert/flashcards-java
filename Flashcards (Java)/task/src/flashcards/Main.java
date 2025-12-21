package flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the number of cards:");
        int cardsNumber = Integer.parseInt(sc.nextLine());
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < cardsNumber; i++) {
            System.out.println("Card #" + (i + 1) + ":");
            String term = sc.nextLine();
            System.out.println("The definition for card #" + (i + 1) + ":");
            String definition = sc.nextLine();
            Card card = new Card(term, definition);
            cards.add(card);
        }

        for (Card card : cards) {
            System.out.printf("Print the definition of \"%s\":%n", card.getTerm());
            String userDefinition = sc.nextLine();
            if (userDefinition.equals(card.getDefinition())) {
                System.out.println("Correct!");
            } else {
                System.out.printf("Wrong. The right answer is \"%s\".%n", card.getDefinition());
            }
        }


    }
}
