/*
 // this script used to read the text file such as lund file and draw histogram
 */
package javapackage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shankar
 */
public class TextFileReader {
    private BufferedReader bufferedReader = null;
    private List<String> readLineTokens = new ArrayList<String>();
    private String tokenizer = "\\s+";
    
    public TextFileReader(){
        System.out.println("Open text file to read");
    }
    
    public boolean openFile(String name){
        try{
            
            FileReader fileReader = new FileReader(name);
            bufferedReader = new BufferedReader(fileReader);
            
        }catch(FileNotFoundException ex){
            System.out.println("[TextFileReader] ---> error opening file: " +name);
            this.bufferedReader = null;
            return false;
        }
        return true;
        
    }
    
    public boolean readNext(){
        if(this.bufferedReader == null){
            this.readLineTokens.clear();
            return false;
        }
        try{
            String line= null;
            while((line = bufferedReader.readLine()) != null){
                
                if (line.startsWith("#") == false) break;
                System.out.println("output" + line);
            }
            readLineTokens.clear();
            if (line == null)
                return false;
            String [] tokens = line.split(this.tokenizer);
            for (String token : tokens){
                System.out.println("Single numbers " + token);
                this.readLineTokens.add(token);
            }
        
    }catch (IOException ex){
        Logger.getLogger(TextFileReader.class.getName()).log(Level.SEVERE, null,ex);
    }return true;
    
    }
    
    public int getDataSize(){ return this.readLineTokens.size();}

    
}
