/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author ZekeR
 */
public class card {
    private int face;   //11-13 = face
    private int suit;   //0-4, clubs, diamonds, hearts, spades

    public card(int face, int suit) {
        this.face = face;
        this.suit = suit;
    }
    
    public int getValue(){
        if(face >= 10)
            return 10;
        else if(face == 1)
            return 11;
        else return face;
    }
    
    public String getFace(){
        switch (this.face) {
            case 1:
                return "a";
            case 11:
                return "j";
            case 12:
                return "q";
            case 13:
                return "k";
            default:
                return String.valueOf(this.face);
        }
    }
    
    public String getSuit(){
        switch (this.suit) {
            case 0:
                return "clubs";
            case 1:
                return "diamonds";
            case 2:
                return "hearts";
            case 3:
                return "spades";
            default:
                return "error";
        }
    }
    
    public int getSuitVal() {
    	return this.suit;
    }
    
    public int getFaceVal() {
    	return this.face;
    }
    
    public String getCardImg() {
    	String out = new String();
    	out = this.getFace() + this.getSuit() + ".png";
    	return out;
    }
    
}
