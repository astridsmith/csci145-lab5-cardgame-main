import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Stack;

public class DeckOfCards {
        private static final SecureRandom randomNumbers = new SecureRandom();
        private static final int numberOfCards = 52;
        private Card[] deck = new Card[numberOfCards];

        
        public void shuffle(){
            Card[] newWorkingDeck = new Card[numberOfCards];
            for(Card card : deck){
                boolean placed = false;
                while (!placed) {
                    int randomIndex = randomNumbers.nextInt(numberOfCards); // Random index
                    if (newWorkingDeck[randomIndex] == null) {
                        newWorkingDeck[randomIndex] = card; // place the card if slot is empty
                        placed = true;
                    }
                    // if not null, loop continues to generate new randomIndex
                }
            }
            this.deck = newWorkingDeck; // replace the old ordered deck with the shuffled deck!
        }
        public DeckOfCards(){
            // constructor creates deck object
            String[] faces = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
            String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
            // fill deck
            for(int i = 0; i < deck.length; i++){
                deck[i] = new Card(faces[i % 13], suits[i / 13]);
            }
        }
        
        private int getDeckLength(){
            return this.deck.length;
        }

        public Stack<Card> getPlayableDeck(){
            Stack<Card> gameDeck = new Stack<Card>();
            for(int i = 0; i < getDeckLength(); i++){
                gameDeck.add(this.deck[i]);
            }
            return gameDeck;
        }

        public ArrayList<Card> dealHand(Stack<Card> gameDeck){
            ArrayList<Card> hand = new ArrayList<Card>();
            for(int i = 0; i < 5; i++){
                hand.add(gameDeck.pop()); // deal 5 cards from the deck, add them to the hand arraylist & remove from deck
            }
            return hand;
        }


        @Override
        public String toString() {
            StringBuilder deckString = new StringBuilder();
            for (Card card : deck) {
                deckString.append(card.toString()).append("\n"); // Append each card's string representation
            }
            return deckString.toString();
        }

}