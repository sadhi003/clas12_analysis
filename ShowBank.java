/*
    this part of class show the bank in the hipo file
    
 */
package javapackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.H1F;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;


public class ShowBank {
    
    
    HipoDataSource reader = new HipoDataSource();
    DataEvent event;
    DataBank bank;
    ArrayList <Float> genMom = new ArrayList<>();
    H1F ggIM_dist;
    H1F ggIM_dist2;
    LorentzVector electron;
    ArrayList<LorentzVector> elecs = new ArrayList<>();
    ArrayList<LorentzVector> particleName = new ArrayList<>(); 
    
    List<int> listOfEvent = new ArrayList<int>();

    
    // open hipo file
    public void open_file(String fileName){
        // read file if it exits otherwise through exceptions
        reader.open(fileName);
        
       
    }
    
    
    // look for hipo information
    public void hipo_info(){
        if(reader.hasEvent()){
            System.out.println(" +++ Hipo file has events +++ total events " + reader.getSize());
        }
    }
    
    // Function to get information about a single event
    public void show_single_event(int eventNo, String bankinfo){
        event = reader.gotoEvent(eventNo);
        System.out.println("Getting a single event information; event number is " + eventNo);
        event.show();
        bank = event.getBank(bankinfo);
        bank.show();
        
    }
    
    
    // read multiple events from the banks 
    public void show_multi_events(String bankinfo){
        int nevents = reader.getSize();
        for (int i =0; i< nevents; i++){
            event = reader.getNextEvent();
            //event.show();
            bank = event.getBank(bankinfo);
            bank.show();
            
        }
    }
    
    
    
    
    
    // get information about generated bank, single mom. information 
    
    public ArrayList<Float> get_MC_data(int noFinalParticles, String bankinfo, String momType, int genPIDCol){
        
        int nevents = reader.getSize();
        for (int i =0; i< nevents; i++){
            event = reader.getNextEvent();
            bank = event.getBank(bankinfo);
            //bank.show();
            int colsRec = bank.rows();
            if (colsRec == noFinalParticles) {
                        float momentum = bank.getFloat(momType,genPIDCol);
                        int pid = bank.getInt("pid", genPIDCol);
                        System.out.println(momType + "\t" + "of particle" + "\t" + pid + "\t"+ momentum);
                        genMom.add(momentum);
            }            
        }
        return genMom;
    }
    
    
    
    
    
    // reconstruction by hitbased tracking and time based tracking
    public void get_electron(String bankinfo){
            int nevents = reader.getSize();
            ggIM_dist2  = new H1F("ggIM_dist2", "electron mass", 100, -1.0, 1.3);
            //System.out.println(nevents);
            int counter=0;
            for (int i=0; i< nevents; i++){
                event = reader.getNextEvent();
                if(event.hasBank(bankinfo)){
                    bank = event.getBank(bankinfo);                    
                    //bank.show();
                    //System.out.println(bank.rows());
                   // System.out.println("length of array list" + pidArray.length);
                    for(int k = 0; k < bank.rows(); k++)
                    {
                        
                        int pidE = bank.getInt("pid", k);
                        if (pidE == 11){
                        // see the different events number that has electron pid    
                            
                        System.out.println("event number " + i);
                        float px = bank.getFloat("px", k);
                        float py = bank.getFloat("py", k);
                        float pz = bank.getFloat("pz", k);
                        double e = Math.sqrt(px*px + py*py + pz*pz + 0.511*0.511);
                        
                        //System.out.println("px " + px + "py " + py + "pz " + pz + "pid " + pidE);
                        elecs.add(new LorentzVector(px, py, pz, e));                    
                         counter +=1;
                        }
                        
                        
                }
                    
                }
               // System.out.println("Total number of events " + counter);
                
            }
            
            
    }
    
    // get pid info
    public boolean hasPID(int pidParticle, int k){
            
            int pidNo = bank.getInt("pid",k);
            return pidNo == pidParticle;     
        }
    
    public boolean hasCharge(byte chargePar, int k){
        byte charge = bank.getByte("charge", k);
        return charge == chargePar;
    }
    
    
 
    // get generated files
    public ArrayList<LorentzVector> fourVectorGen(String bankName, int pidNo, double mass){
        int nevents = reader.getSize();
        for (int i =0; i< nevents; i++){
            event = reader.getNextEvent();
            //event.show();
            if (event.hasBank(bankName)){
                   bank = event.getBank(bankName);
                   //if (bank.rows() == 4){
                       for (int k=0; k< bank.rows(); k++){
                           if (this.hasPID(pidNo,k)){
                               float px = bank.getFloat("px", k);
                               float py = bank.getFloat("py", k);
                               float pz = bank.getFloat("pz", k);
                               double e = Math.sqrt(px*px + py*py + pz*pz + mass*mass);
                        
                                //System.out.println("px " + px + "py " + py + "pz " + pz + "pid " + pidE);
                                particleName.add(new LorentzVector(px, py, pz, e));                    
                           }
                       }
                  // }
            }
            
            
            
        }
        return particleName;
    }
    
    
    
    
     // get generated files
    public ArrayList<LorentzVector> fourVectorRec(String bankName, int pidNo, double mass){
        int nevents = reader.getSize();
        for (int i =0; i< nevents; i++){
            event = reader.getNextEvent();
            //event.show();
            if (event.hasBank(bankName)){
                   bank = event.getBank(bankName);
                       for (int k=0; k< bank.rows(); k++){
                           
                            if (this.hasPID(pidNo,k)){
                               float px = bank.getFloat("px", k);
                               float py = bank.getFloat("py", k);
                               float pz = bank.getFloat("pz", k);
                               float beta = bank.getFloat("beta",k);
                               double e = Math.sqrt(px*px + py*py + pz*pz + mass*mass);
                        
                                //System.out.println("px " + px + "py " + py + "pz " + pz + "pid " + pidE);
                                particleName.add(new LorentzVector(px, py, pz, e));                    
                           }
                       }
                   
            }
            
            
            
        }
        return particleName;
    }
    
    
    
    
    
    
    public void use_PID(String bankName){
        
        ggIM_dist = new H1F("ggIM_dist", "ggIM_dist", 100, 0.0, 0.3);
        while(reader.hasEvent())
        {
            ArrayList<LorentzVector> photons = new ArrayList<>();
            System.out.println(reader.getSize());
            event = reader.getNextEvent();
            if(event.hasBank(bankName))
            {
                bank = event.getBank(bankName);
                bank.show();
                for(int k = 0; k < bank.rows(); k++)
                {
                        int pid = bank.getInt("pid", k);
                        float px = bank.getFloat("px", k);
                        float py = bank.getFloat("py", k);
                        float pz = bank.getFloat("pz", k);
                        double p = Math.sqrt(px*px + py*py + pz*pz);
                        if(pid == 22) photons.add(new LorentzVector(px, py, pz, p));
                }

                if(photons.size() == 2)
                {
                        // add two photons information in the lorentzvector
                        LorentzVector twoPhotons = new LorentzVector(photons.get(0));
                        twoPhotons.add(photons.get(1));
                        
                        ggIM_dist.fill(twoPhotons.mass());
                }
               
            }
        }

    }
    
    
    
    



}




