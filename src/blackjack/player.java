/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.ArrayList;

/**
 *
 * @author ZekeR
 */
public class player {
    private int type; //0 = player, 1 = dealer
    private ArrayList<card> hand;
    private int aceCount;
    private int value;
    int id;

    public player(int type, int id) {
        this.type = type;
        this.hand = new ArrayList<>();
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    public void draw(card c){
        this.hand.add(c);
        this.value += c.getValue();
        if(c.getValue() == 11)
            aceCount++;
        if(value>21 && aceCount!=0){
            this.aceCount --;
            this.value -= 10;
        }
    }
    
    public boolean hasBlackjack(){
        if((hand.get(0).getValue() == 10 && hand.get(1).getValue() == 1) ||
                (hand.get(0).getValue() == 1 && hand.get(1).getValue() == 10)){
            return true;
        } else return false;
    }
    
    public boolean isBusted(){
        if(this.value > 21)
            return true;
        return false;
    }
    
    public int getValue(){
        return this.value;
    }
    
    public ArrayList<card> getHand(){
        return this.hand;
    }
    
    public int getNumAces(){
        return this.aceCount;
    }
    
    public int checkWin(int value){
        if(this.value > 21){
            return 0;
        } else if (this.hasBlackjack()){
            return 2;
        } else if(this.value == value){
            return 1;
        } else if(this.value > value){
            return 2;
        } else if(value > 21){
            return 2;
        }
        return 0;
    }
}
