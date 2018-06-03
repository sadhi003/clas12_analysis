/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapackage;

import org.jlab.clas.physics.Particle;
import org.jlab.clas.physics.PhysicsEvent;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;

/**
 *
 * @author shankar
 */
public class ReconstructionAnalysis {
    private HipoDataSource reader = null;
    private DataBank bank;
    public void hipoReader(String fileName){
        reader = new HipoDataSource();
        reader.open(fileName);
    }
    
    
    
    public void matchGenerated(PhysicsEvent gen, PhysicsEvent rec){
        int nrows = rec.count();
        for (int loop = 0; loop < nrows; loop++){
            Particle rpart = rec.getParticle(loop);
            System.out.println(rpart);
        }
    
    
    
    }   
    
    public void bankInfo(DataEvent event){
        event = reader.getNextEvent();
        
        if (event.hasBank("REC::Particle") == true){
            this.bank = event.getBank("REC::Particle");
            
            int rowsGen = bank.rows();
            int colmGen = bank.columns();
            System.out.print("rows " + rowsGen + "columns " + colmGen);
             
         }
    }
    
    
    public int getSizeOfFile(){
        return this.reader.getSize();
    }
    
    
    public void getBankValues(DataEvent event){
         if (event.hasBank("MC::Particle") == true){
             
         }
    }
        
    
}
