/*
 plot momentum resolution 
 */

package javapackage;

import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.H1F;



public class plotResolution {
    
    public static void main(String [] args)
    
        {
            ShowBank obj = new ShowBank();
            // global function need to open (can keep in different class)
            obj.open_file("file1.hipo"); 
            obj.hipo_info();
    
            H1F momEleGen = new H1F("momEleGen","mass of electron", 100,0.,11.0);
            H1F momEleRec = new H1F("momEleRec","mass of electron", 100,0.,11.0);
            
            H1F momEleRecGen = new H1F("momEleRecGen","mass of electron", 100,0.,11.0);
            
        
            int particleID = 11;
            double particleMass= 0.000511;
        
                // generated
           /* for (LorentzVector particle : obj.fourVectorGen("MC::Particle", particleID, particleMass)){
                
                momEleGen.fill(particle.p());
                
            }
            
            
            // reconstructed 
            for (LorentzVector particle : obj.fourVectorGen("REC::Particle", particleID, particleMass)){
                
                momEleRec.fill(particle.p());
                
            }
            
            //momEleRecGen = momEleGen.;
            */
            obj.get_electron("REC::Particle");
            
        }
    
    
    
}
