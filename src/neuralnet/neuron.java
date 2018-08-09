/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnet;


/**
 *
 * @author ZekeR
 */
public class neuron {
    
    private final double[] weight;
    private final int inCount;
    
    public neuron(int inCount, double[] seed){
        this.inCount = inCount;
        this.weight = new double[inCount];
        if(seed == null){
            for(int i=0;i<inCount;i++){
                this.weight[i] = (Math.random()-.5)*10;
            }
        }
        else {
            for(int i=0;i<inCount;i++){
                this.weight[i] = seed[i] + ((Math.random())-.5);
            }
        }
    }
    
    public double[] getWeights(){
        return this.weight;
    }
    
    public double fire(double[] in){
        double out = 0;
        for(int i=0;i<this.inCount;i++){
            out += this.weight[i] * in[i];
        }
        
        out = 1/(1+Math.exp(0-out));
        
        return out;
    }
    
    public void adjust(Boolean win){
        
    }
}
