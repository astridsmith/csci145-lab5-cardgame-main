import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // random numbers so that the robot can use them
        SecureRandom randomNumbers = new SecureRandom();
   
        // instruction implimention, uses gamehelpers (seperate file but if I was to make this again I'd put more main stuff in there)
        Scanner instructionsConsent = new Scanner(System.in);
        System.out.println("Would you like a breif overview on how to play? (y/n):\n");
        String instructionsConsentAns = instructionsConsent.nextLine();
        // System.out.println("Debug: ans is " + instructionsConsentAns);
        if (instructionsConsentAns.equals("y") || instructionsConsentAns.equals("Y") || 
        instructionsConsentAns.equals("yes") || instructionsConsentAns.equals("Yes") || 
        instructionsConsentAns.equals("YES")){
            GameHelpers.Instructions();
        }

        // Initialize deck and shuffle it
        DeckOfCards myPlayingCardDeck = new DeckOfCards();
        // System.out.println(" DEBUG: Unshuffled deck:");
        // System.out.println(myPlayingCardDeck.toString());
        myPlayingCardDeck.shuffle();
        // System.out.println("DEBUG: Shuffled deck:");
        // System.out.println(myPlayingCardDeck.toString());

        // turn shuffled deck object into a stack so that it can be used for dealing and drawing purposes
        Stack<Card> gameDeck = myPlayingCardDeck.getPlayableDeck();

        //deal!
        ArrayList<Card> computerHand = myPlayingCardDeck.dealHand(gameDeck);
        ArrayList<Card> userHand = myPlayingCardDeck.dealHand(gameDeck);
        // score vars
        int computerScore = 0;
        int userScore = 0;

        // game flow:
        while (!(gameDeck.isEmpty() && computerHand.isEmpty() && userHand.isEmpty())) {

            // robot turn
            if (!computerHand.isEmpty()){
                int compIndex = randomNumbers.nextInt(computerHand.size());
                String compGuess = computerHand.get(compIndex).getFace();
                System.out.println("Computer asks: Do you have a " + compGuess + "?");
                boolean found = false;
                for (int i = 0; i < userHand.size(); i++){
                    if (userHand.get(i).getFace().equalsIgnoreCase(compGuess)){ // handy lil ignore case thing I found
                        // remove one matching card from the user's hand
                        userHand.remove(i);
                        // also remove one card of the same rank from computer hand 
                        for (int j = 0; j < computerHand.size(); j++){
                            if (computerHand.get(j).getFace().equalsIgnoreCase(compGuess)){
                                computerHand.remove(j);
                                break;
                            }
                        }
                        computerScore++;
                        System.out.println("Computer forms a pair of " + compGuess + "s!");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("User says: Go Fish!");
                    if (!gameDeck.isEmpty()){
                        Card drawn = gameDeck.pop();
                        computerHand.add(drawn);
                        System.out.println("Computer draws a card.");
                    }
                }
            } else {
                System.out.println("Computer has no cards to play.");
            }

            // game end conditional 
            if (gameDeck.isEmpty() && computerHand.isEmpty() && userHand.isEmpty()){
                break;
            }

            // user turn
            if (!userHand.isEmpty()){
                System.out.println("\nYour hand:");
                for (Card c : userHand) {
                    System.out.println(c.toString());
                }
                String userGuess = "";
                boolean validInput = false;
                // prompt until the user enters a rank they *actually* have (frustrating)
                while(!validInput) {
                    System.out.println("\nYour turn: Enter the rank you want to ask for:");
                    userGuess = scanner.nextLine().trim();
                    for (Card c : userHand) {
                        if (c.getFace().equalsIgnoreCase(userGuess)){
                            validInput = true;
                            break;
                        }
                    }
                    if (!validInput){
                        System.out.println("You must have at least one card of that rank in your hand. Try again.");
                    }
                }
                // does computer has a matching card?
                boolean matchFound = false;
                for (int i = 0; i < computerHand.size(); i++){
                    if (computerHand.get(i).getFace().equalsIgnoreCase(userGuess)){
                        
                        computerHand.remove(i);
                        for (int j = 0; j < userHand.size(); j++){
                            if (userHand.get(j).getFace().equalsIgnoreCase(userGuess)){
                                userHand.remove(j);
                                break;
                            }
                        }
                        userScore++;
                        System.out.println("You form a pair of " + userGuess + "s!");
                        matchFound = true;
                        break;
                    }
                }
                if (!matchFound) {
                    System.out.println("Computer says: Go Fish!");
                    if (!gameDeck.isEmpty()){
                        Card drawn = gameDeck.pop();
                        userHand.add(drawn);
                        System.out.println("You draw a card.");
                    }
                }
            } else {
                System.out.println("You have no cards to play.");
            }

            // current scores display (change to ask every time? make it more prominent? idk)
            System.out.println("\nCurrent Scores: You - " + userScore + ", Computer - " + computerScore);
            System.out.println("Cards left in deck: " + gameDeck.size());
            System.out.println("--------------------------------\n");
        }

        // game finish + results
        System.out.println("Game over!");
        System.out.println("Final Scores: you - " + userScore + ", computer - " + computerScore);
        if(userScore > computerScore){
            System.out.println("You win!");
        } else if(userScore < computerScore){
            System.out.println("Computer wins!");
        } else {
            System.out.println("Tie!");
        }
    }
}
