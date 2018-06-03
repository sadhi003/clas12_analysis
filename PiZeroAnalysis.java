/*
 This script is used for pi0 analysis
 */
package javapackage;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;


public class PiZeroAnalysis {
    
    HipoDataSource reader = new HipoDataSource();
    DataEvent event;
    DataBank bank;
    ArrayList <Float> genMom = new ArrayList<>();
    H1F ggIM_dist;
    H1F ggP_dist;
    H1F ggTheta_dist;
    H1F ggPhi_dist;
    H1F ggCMphi_dist;
    H1F momPi0;
    H2F momPi0vsdelP;
    
    
    
    
    
    
    
    
    
    
    
    
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
    
    
    
    
    public static void main(String [] args){
        ShowBank obj = new ShowBank();
        // global function need to open (can keep in different class)
        obj.open_file(); 
        obj.hipo_info();
        
        
        
        // give bank name and get data for specific events
        String bank_name = "REC::Particle";
        //int eventNo = 6;
        //obj.show_single_event(eventNo, bank_name);
        
        
        
        
        
        // look for all events by giving specific bank name
        //String bank_name = "REC::Particle";
        //obj.show_multi_events(bank_name);
        
        // get generated data and plot it
       /* JFrame frame = new JFrame("Pi zero mass distribution");
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        frame.setSize(800,500);
        
        obj.use_PID(bank_name);
        obj.ggIM_dist.setOptStat(1111);
        canvas.draw(obj.ggIM_dist);
        frame.add(canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
        
        /*H1F histogram = new H1F("histogram",100,-3.2,3.2);
        
        String bank_name = "MC::Particle";
        String momName = "pz";
        int pidNo = 0;
        ArrayList<Float> newMom = new ArrayList<>();

        newMom = obj.get_MC_data(bank_name, momName, pidNo);
        
        for (Float newMom1 : newMom) {
            histogram.fill(newMom1);
        }
        */
        
        
        
        
        
        
        
        /*histogram.setTitleX("delp/p for kaon");
        histogram.setTitleY("Counts");
        histogram.setFillColor(34);
        histogram.setOptStat(1111);
        canvas.getPad(0).setTitle("1d histogram");
        canvas.setFont("HanziPen TC");
        canvas.draw(histogram);
        frame.add(canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        */
    }

    
    
    
    
    
    
}