/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapackage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author shankar
 */
public class TestFile {
    
        public static void main(String [] args) throws IOException {
       
         String fileName = "kpppim.lund";
         List <String> fileTokenizer = new ArrayList<>();
         BufferedReader bufferedReader = null;
         String tokenizer = "\\s+";
         
            
            FileReader fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            
            String line = null;
            String [] tokens;
            
            while((line = bufferedReader.readLine()) != null){
                //System.out.println("output" + line);
                tokens = line.split(tokenizer);
                
                //fileTokenizer.addAll(Arrays.asList(tokens));
               // if (line.startsWith(" ")) continue;
                if (line.length() == 64) continue;
                System.out.println(line);
               // System.out.println(tokens);
                String valueNeeded = tokens[3];
                fileTokenizer.add(valueNeeded);
                System.out.println(fileTokenizer.size());
                
                
            }
    
   
    }
        
        
        
    
}
