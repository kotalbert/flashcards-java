package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Card:");
        String term = sc.nextLine();
        System.out.println("Definition:");
        String definition = sc.nextLine();
        String answer = sc.nextLine();
        if (definition.equals(answer)) {
            System.out.println("Your answer is right!");
        } else {
            System.out.println("Your answer is wrong...");
        }


    }
}
