package com.example.lib;

public class Deck {
   private String deckID;

   public Deck(String deckID) {
       setDeckID(deckID);
   }
   public String getDeckID() {
       return deckID;
   }

   public void setDeckID(String deckID) {
       this.deckID = deckID;
   }
}
