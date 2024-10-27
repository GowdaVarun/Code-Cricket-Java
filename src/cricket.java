package src;

import java.util.Scanner;

public class cricket {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to Cricket Game!");
        System.out.println("Let's start with a game of Even or Odd!");

        System.out.print("Your choice (Even or Odd): ");
        String choice = s.nextLine();
        System.out.println("Let's Play!");

        int computer = (int) (Math.random() * 6);
        System.out.print("Your roll (0-9): ");
        int player = s.nextInt();
        System.out.println("Computer's roll: " + computer);

        String ch;
        if ((computer + player) % 2 == 0) {
            ch = "Even";
            System.out.println("The result is Even.");
        } else {
            ch = "Odd";
            System.out.println("The result is Odd.");
        }

        int k;
        if (choice.equalsIgnoreCase(ch)) {
            System.out.println("Great choice! Do you want to Bat (1) or Bowl (0)?");
            k = s.nextInt();
            while (k != 0 && k != 1) {
                System.out.println("Invalid choice! Please choose Bat (1) or Bowl (0).");
                k = s.nextInt();
            }
            play(k);
        } else {
            System.out.println("Computer will bat first.");
            play(0);
        }
    }

    public static void play(int x) {
        int n, player = 0;
        int sc = 0;
        Scanner s = new Scanner(System.in);
        int balls = 0;

        System.out.println("\n--- First Innings ---");
        while (balls < 12) {
            System.out.println("Current Score: " + sc);
            System.out.print("Your roll (0-6): ");
            n = (int) (Math.random() * 6);
            player = s.nextInt();
            System.out.println("Computer's roll: " + n);
            if (x == 0) {
                sc += n;
            } else {
                sc += player;
            }
            balls++;
            System.out.println("Balls left: " + (12 - balls));
        }

        System.out.println("First Innings Completed! Your score is " + sc + ".");

        // Second Innings
        System.out.println("\n--- Second Innings ---");
        balls = 0; // Resetting balls for second innings

        do {
            System.out.println("Target to chase: " + sc);
            n = (int) (Math.random() * 6);
            System.out.print("Your roll (0-5): ");
            player = s.nextInt();
            System.out.println("Computer's roll: " + n);
            if (x == 0) {
                sc -= player;
            } else {
                sc -= n;
            }
            balls++;
            System.out.println("Balls left in this innings: " + (12 - balls));
        } while (n != player && sc >= 0 && balls < 12);

        if ((sc >= 0 && x == 0) || (sc < 0 && x == 1))
            System.out.println("Computer Won! Better luck next time.");
        else if ((sc >= 0 && x == 1) || (sc < 0 && x == 0))
            System.out.println("You Won! Congratulations!");
        else
            System.out.println("It's a draw!");
    }
}
