/*
 Create hashmap to save column and correcspoding value
 */
package javapackage;

import java.util.*;

/**
 *
 * @author shankar
 */
public class HashCreate {
    
    private Map<String, String> lundColumn = new HashMap<>();
    private String column;
    
    public HashCreate(){
        System.out.println("Hash map to provide plotting information ");
    }
    
    public void addParticleColumn(String[] tokens){
         
        for (int i = 0; i < 14; i++){
            this.column = "col" + toString(i);
            lundColumn.put(column, tokens[i]);
            
        }
    }

    public void addInformation(String colName, String description){
        this.lundColumn.put(colName, description);
    }
    
    
    
    
    
    public String getHasMap(String colNum){
        return lundColumn.get(colNum);
    }
    
    private String toString(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
