/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapackage;
import static java.lang.Math.*;
import java.util.*;
import org.jlab.groot.ui.TCanvas;
import javax.swing.JFrame;
import org.jlab.groot.data.H1F;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.clas.physics.Particle;
import org.jlab.io.hipo.*;
import org.jlab.io.base.*;
import org.jlab.clas.physics.EventFilter;
import org.jlab.clas.physics.GenericKinematicFitter;
import org.jlab.clas.physics.RecEvent;

/**
 *
 * @author shankar
 */
public class HipoDataEvents {
    public static void main(String [] args){
        
  
        JFrame frame = new JFrame("Generated file histogram");
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        frame.setSize(800,500);
        H1F histogram = new H1F("histogram",100,-0.2,0.2); 
        
        H1F hMM_eKplus = new H1F("hMM_eKplus", "hMM_eKplus", 100, 0.8, 1.6);
        H1F hMM_eKplusp = new H1F("hMM_eKplusp", "hMM_eKplusp", 100, -0.06, 0.18);
        HipoDataSource reader = new HipoDataSource();
        reader.open("rec.hipo");
        int nevents = reader.getSize();
        System.out.println(nevents);
        
        int counter = 0;
        GenericKinematicFitter fitter = new GenericKinematicFitter(11.0);
        EventFilter filter_eKplusX = new EventFilter("11:321:X+:X-:Xn");
        EventFilter filter_eKpluspX = new EventFilter("11:321:2212:X+:X-:Xn");
        
            
            
        while(reader.hasEvent())        
        {        
                counter++;
                DataEvent event = reader.getNextEvent();
              // DataBank bank = event.getBank("REC::particle");
               //System.out.println(bank.getFloat("px", 0));
               
                RecEvent recEvent = fitter.getRecEvent(event);
                recEvent.doPidMatch();
                //System.out.println(recEvent.getReconstructed());
                if(filter_eKplusX.isValid(recEvent.getReconstructed()))
                {
                    Particle X_eKpluspX = recEvent.getReconstructed().getParticle("[b]+[t]-[11,0]-[321,0]-[2212,0]");
                    System.out.println(X_eKpluspX.mass());
                    if(X_eKpluspX.mass() >= 0) hMM_eKplusp.fill(X_eKpluspX.mass()*X_eKpluspX.mass());
                    else if(X_eKpluspX.mass() < 0) hMM_eKplusp.fill(-1.0*X_eKpluspX.mass()*X_eKpluspX.mass());
                }
                
                
                
            }
            System.out.println("Total number of events are \t" + counter);
        
    
        TCanvas can = new TCanvas("can", 800, 300);
        can.divide(2, 1);
        can.cd(0);
        //can.draw(hMM_eKplus);
        can.cd(1);
        can.draw(hMM_eKplusp);
        
        
        
    }    
}
