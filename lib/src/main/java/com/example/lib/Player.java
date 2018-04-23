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

     public int getCardLength() {
          return cards.size();
     }
}
