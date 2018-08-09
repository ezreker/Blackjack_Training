/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import gameboard.board;
import neuralnet.network;

/**
 *
 * @author ZekeR
 */
public class game {
    
    private int numDecks;
    private int numPlayers;
    private ArrayList<card> deck;
    private ArrayList<player> players;
    private player dealer;
    private int[] score;
    private boolean display;
    private board board;
    
    public game(int numDecks, int numPlayers, network n, boolean display, board board) throws InterruptedException{
        this.numDecks = numDecks;
        this.numPlayers = numPlayers;
        this.display = display;
        this.board = board;
        deck = generateDeck(numDecks);
        deck = shuffleDeck(deck);
        players = new ArrayList<>();
        dealer = new player(1, -1);

        for(int i=0;i<numPlayers;i++){
            players.add(new player(0, i));     //add players
        }

        deal(deck, players, numPlayers);    //deal cards
        
        for(int i=0;i<numPlayers;i++){
            while(decide(players.get(i), n) == 0){
                draw(deck, players.get(i));
            }
        }
        
        while(dealer.getValue() < 17){
            draw(deck, dealer);
        }
        
        int dealerval = dealer.getValue();
        
        for(int i=0;i<numPlayers;i++){
        	int winloss = players.get(i).checkWin(dealerval);
            n.addWin(winloss);
            if(this.display) {
            	board.changeCash(winloss-1);
            }
        }
        
    }
    
    private void deal(ArrayList<card> deck,
        ArrayList<player> players, int numPlayers) throws InterruptedException{
        
        draw(deck, this.dealer);         //dealer draws one
        
        for(int i=0;i<numPlayers;i++){
            draw(deck, players.get(i));     //player draws one
            draw(deck, players.get(i));     //player draws second
        }
    }
    
    private void draw(ArrayList<card> deck, player p) throws InterruptedException{
        Random r = new Random();
        int numCards = deck.size();
        int src = r.ints(0,numCards).findFirst().getAsInt();
        if(display)
/*            System.out.print("Player " + p.getId() + " drew " + 
                    deck.get(src).getFace() + " of " + deck.get(src).getSuit() + 
                    " - ");*/
        {
        	if(p.getId() == -1)
        		board.addDealerCard(deck.get(src));
        	else board.addPlayerCard(deck.get(src));
        }
        p.draw(deck.get(src));
        
        if(display)
            System.out.println(p.getValue());
        deck.remove(src);
    }
    
    private ArrayList<card> generateDeck(int numDecks){
        
        ArrayList<card> deck = new ArrayList<>();
        
        for(int i=0;i<numDecks;i++){
            for(int j=1;j<14;j++){
                for(int k=0;k<4;k++){
                    deck.add(new card(j,k));
                }
            }
        }
        
        return deck;
    }
    
    private ArrayList<card> shuffleDeck(ArrayList<card> oldDeck) throws InterruptedException{
        
        int numCards = oldDeck.size();
        ArrayList<card> newDeck = new ArrayList<>();
        Random r = new Random();
        int src;
        
        while(numCards > 0){
            src = r.ints(0,numCards).findFirst().getAsInt();
            newDeck.add(oldDeck.get(src));
            oldDeck.remove(src);
            numCards--;
        }
        if(display)
        	board.shuffleDeck();
        
        return newDeck;
    }
    
    private int decide(player p, network n){
        
        if(p.hasBlackjack()){
            if(display) System.out.println("Blackjack!");
            return 1;
        }
        if(p.isBusted()){
            if(display) System.out.println("Busted!");
            return 1;
        }
        if(n.askNet(p.getHand(), p.getNumAces(), dealer.getHand().get(0))){
            if(display) System.out.println("Passed");
            return 1;
        }
        return 0;
/*        Scanner reader = new Scanner(System.in);
        char c = ' ';
        System.out.println("Hit (h) or pass (p)?");
        while(c != 'h' && c != 'p'){
            c = reader.next().charAt(0);
            if(c == 'h')
                return 0;
            else if(c == 'p')
                return 1;
        }*/
    }
    
}
