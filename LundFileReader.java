/*
 develope to read lund format and give data reading PID
 */
package javapackage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
//import javapackage.HashCreate;


/**
 *
 * @author shankar
 */
public class LundFileReader {
    private BufferedReader bufferedReader = null;
    private List<String> readLineTokens = new ArrayList<String>();
    private String tokenizer = "\\s+";
    private int numParticles;
    private final int PID;
    private int column;
  
  // empty constructor  
    public LundFileReader(int particleId, int colNum){
        this.PID = particleId;
        this.column = colNum;
        System.out.println("Open lund file to read!");
    }
    
   
    
    // void method to read the lund file
    public void loadFile(String fileName){
        try{
            FileReader reader = new FileReader(fileName);
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                listData(line);
            }
            reader.close();
            
        }
        catch(IOException ex){
            System.out.println("[TextFileReader] ---> error opening file: " + fileName);
            
        }
    }
    
    
    // read the string line and look for specific column
    public void listData(String lineToParse){
        
        String [] tokens;
        tokens = lineToParse.split(this.tokenizer);
        
        this.numParticles = getNumPar(tokens);
    
       // System.out.println(lundCol.get("0"));
        
        //System.out.println(getParId(tokens));
        if ((tokens.length == 15) && (this.PID == getParId(tokens))){
           // System.out.println(tokens[7]);
            this.readLineTokens.add(tokens[this.column]);
        }
      
        
    }
    
    public int getNumPar(String [] tokens){ 
        int np = 0;
        if (tokens.length == 10){ 
            np = Integer.parseInt(tokens[0]);
        }
        return np;
    }
    
   


    public int getDataSize(){ return this.readLineTokens.size();}

    
    // list data into an array
    public List<String> getList() {
           return this.readLineTokens;
       }    
    
    
    // return particle id column
    public int getParId(String [] tokens){ 
        int pid=0;
        if (tokens.length == 15){ 
            pid = Integer.parseInt(tokens[4]);
            
        }
        return pid;
        
        
    }
    
    
    // print information about lund file
    public void printLundInfo(){
        System.out.println("Here is the information about the lund file header and lund particles.");
        System.out.println("HEADER INFO:*** \n"
                + "COL 1: Total number of lund particles. \n"
                + "LUND PARTICLES INFO:*** \n"
                + "COL 1: index\n"
                + "COL 2: charge\n"
                + "COL 3: type (1 for active)\n"
                + "COL 4: Particle id\n"
                + "COL 7: Momentum x \n"
                + "COL 8: Momentum y\n"
                + "COL 9: Momentum z\n"
                + "COL 12: Vertex x\n"
                + "COL 13: Vertex y\n"
                + "COL 14: Vertex z");
        System.out.println("Based on what you need hit corresponding column number\n"
                + " such as col1, col2 etc for lund particles");
    }
    
    
    
   

}
