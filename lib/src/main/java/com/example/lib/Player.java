package com.example.lib;

import java.util.ArrayList;

public class Player {
     private ArrayList<Card> cards = new ArrayList<>(0);
     public void addCard(final Card card) {
          cards.add(card);
     }

     public Card getCard(int i) {
          return cards.get(i);
     }

     public ArrayList<Card> getCards() {
          return cards;
     }

     public String[] getUrls() {
         String[] urls = new String[cards.size()];
         for (int i = 0; i < cards.size(); i++) {
             urls[i] = cards.get(i).getUrl();
         }
         return urls;
     }
     public int getCardLength() {
          return cards.size();
     }
}
