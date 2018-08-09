/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import static java.util.Comparator.comparingInt;
import java.util.Random;
import java.util.Scanner;

import gameboard.board;
import neuralnet.network;

/**
 *
 * @author ZekeR
 */
public class blackjack {

    /**
     * @param args the command line arguments
     * @throws InterruptedException 
     */
    
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        int numPlayers;
        int numDecks;
        ArrayList<network> networks;
        game newGame;
        int generations = 1000;                    //modify gen count here
        
//        BufferedReader reader =
//                new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("How many decks is the dealer using? >");
//        numDecks = Integer.parseInt(reader.readLine());
//        System.out.println("How many players are playing? >");
//        numPlayers = Integer.parseInt(reader.readLine());
        
        numDecks = 1;
        numPlayers = 1;
        
        networks = new ArrayList<>();
        
        //create 100 randomly weighted neural networks
        for(int i=0;i<100;i++){
            networks.add(new network(null, null));
        }
        
        for(int i=0;i<generations;i++){
            
            //simulate 100 games in each network
            for(int j=0;j<100;j++){
                for(int k=0;k<100;k++)
                    newGame = new game(numDecks, numPlayers,
                            networks.get(j), false, null);
            }
            
            //sort by success
            Collections.sort(networks, comparingInt(network::getScore));
            
            //remove worst 50 (no gradient)
            for(int j=0;j<50;j++){
                networks.remove(0);
            }
            
            //give each remaining network a child
            for(int j=0;j<50;j++){
                networks.get(j).resetScore();
                networks.add(networks.get(j).reproduce());
            }
            if(i%100 == 0) System.out.println("Generation " + i + " complete");
        }
        
        //simulate each new network
        for(int j=0;j<100;j++){
            for(int k=0;k<100;k++)
                newGame = new game(numDecks, numPlayers, 
                        networks.get(j), false, null);
        }
        
        //sort more new networks
        Collections.sort(networks, comparingInt(network::getScore));
        
        //calculate win%
        double avg = 0;
        int temp;
        for(int i=0;i<100;i++){
            temp = networks.get(i).getScore();
            avg+=temp;
        }
        avg/=20000;
        System.out.println("Trained win % : " + avg);
        
        board myBoard = new board();
        
        //run games with best scoring network
        boolean active = true;
        Scanner scanner = new Scanner(System.in);
        char c;
        
        /*
        while(active){
            c = 'o';
            newGame = new game(numDecks, numPlayers, networks.get(99), true, myBoard);
            System.out.println("Play again? (y/n): ");
            while(c!='y' && c!='n'){
                c = scanner.next().charAt(0);
                if(c == 'y') active = true;
                else if(c == 'n') active = false;
            }
        }*/
        while(true) {
        	myBoard.checkNewGame();
            newGame = new game(numDecks, numPlayers, networks.get(99), true, myBoard);
        }
    }
}
