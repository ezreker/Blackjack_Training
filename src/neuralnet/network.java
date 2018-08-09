/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnet;

import blackjack.card;
import java.util.ArrayList;

/**
 *
 * @author ZekeR
 */
public class network {
    
    private int score;
    private ArrayList<layer> layers;

    public network(layer layer1, layer layer2){
    //      , int numPlayers, int numDecks) {
    
        this.score = 0;
        this.layers = new ArrayList<layer>();
        
        if(layer1 == null){
            this.layers.add(new layer(12, 9, null));
            this.layers.add(new layer(1, 12, null));
        }
        
        else{
            this.layers.add(layer1);
            this.layers.add(layer2);
        }
    }
    
    public boolean askNet(ArrayList<card> hand, int numAces, card dealerCard){
        
        double[] in = new double[9];
        double[] out;
        for(int i=0;i<7;i++){
            if(i<hand.size()){
                in[i] = hand.get(i).getValue();
            } else {
                in[i] = -1;
            }
        }
        
        in[7] = (double)numAces;
        in[8] = (double)dealerCard.getValue();
        
        out = this.layers.get(0).fireNeurons(in);
        out = this.layers.get(1).fireNeurons(out);
        return out[0]>.5;
    }
    
    public void addWin(int amount){
        this.score += amount;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public network reproduce(){
        layer layer1 = this.layers.get(0).reproduce();
        layer layer2 = this.layers.get(1).reproduce();
        
        network child = new network(layer1, layer2);
        
        return child;
    }
    
    public void resetScore(){
        this.score = 0;
    }
    
}
