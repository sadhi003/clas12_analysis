/*
 this class is used to remove blank space infront of data row.
 */
package javapackage;

import java.io.*;

/**
 *
 * @author shankar
 */

public class SpaceRemover {
    private BufferedReader br = null;
    public SpaceRemover(){
        System.out.println("Look the File has space or not");
    }
    
    public void readFile(){
        try {
            FileReader fr = new FileReader("kpppim.lund");
            FileWriter fw = new FileWriter("kpppim.txt");
            br = new BufferedReader(fr);
            String line = null;
            
            while ((line = br.readLine()) != null){
                line = line.trim();
                if (!line.equals("")){
                    fw.write(line);
                    fw.write("\n");
                }
            }
            
            fr.close();
            fw.close();
            
        }catch(IOException ex){
            System.out.println("File can not read and write");
        }
   
    }
    
    
    
    
}
