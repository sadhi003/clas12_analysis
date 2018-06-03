/*
main class to test my LundFileReader class
 */
package javapackage;

/**
 *
 * @author shankar
 */
public class Main {
        
        public static void main(String args[]){
            LundFileReader file = new LundFileReader(2212,9);
            
            /* correct space in the lund file
            SpaceRemover sr = new SpaceRemover();
            sr.readFile();*/
            
            file.loadFile("kpppim.txt");
            file.printLundInfo();
            
           //System.out.println("ArrayList Size is " + file.getDataSize());
           
           // test hash map
           
           
           
        }
    
}
