
package javapackage;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import javax.swing.JFrame;
import static kotlin.io.IoPackage.reader;
import org.jlab.clas.physics.LorentzVector;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvas;

/**
 *
 * @author shankar
 */
public class pPipPimProject {
    
    public static void main(String [] args){
        ShowBank obj = new ShowBank();
        // global function need to open (can keep in different class)
        obj.open_file("ppippim.hipo"); 
        obj.hipo_info();
        
        String bank_name = "MC::Particle";
        
        //obj.show_single_event(3, bank_name);
        //obj.show_single_event(3, "REC::Event");
        //obj.show_single_event(3, "REC::Particle");
        //obj.show_single_event(3, "RECHB::Particle");
        
        //obj.show_multi_events("REC::Particle");
        
        // get electron
        H1F massEle = new H1F("massEle","mass of electron", 100,0.,10.4);
        H1F phiEle = new H1F("phiEle","phi of electron", 100, -190.0,190.0);
        H1F thetaEle = new H1F("thetaEle","theta of electron",100, 0.,190.0);
        
        H2F phiTheta = new H2F("phiTheta", "theta vs phi", 100,0.0,90.0,100, -190.0,190.0);
        
        H1F momEle = new H1F("momEle","momentum of electron", 100, 0.0,8.);
        
        double phiAngle, thetaAngle;
        obj.get_electron("REC::Particle");
        System.out.println(obj.elecs.size());
        //LorentzVector electronNew = new LorentzVector();
        for (LorentzVector elec : obj.elecs) {
           // electronNew.add(elec);
            System.out.println(elec.mass2());
            massEle.fill(elec.e());
            phiAngle = elec.phi() * (180/3.1415);
            thetaAngle = elec.theta() *(180/3.1415);
            phiEle.fill(phiAngle);
            thetaEle.fill(thetaAngle);
            phiTheta.fill(thetaAngle,phiAngle);
            momEle.fill(elec.p());
            
        }
        
        JFrame frame = new JFrame("Reconstructed electron");
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        canvas.divide(2, 3);
        frame.setSize(800,800);
        
        massEle.setTitleX("electron energy [GeV]");
        massEle.setTitleY("Counts");
        massEle.setFillColor(34);
        massEle.setOptStat(1111);
        canvas.getPad(0).setTitle("1d histogram");
        canvas.setFont("HanziPen TC");
        canvas.cd(0);
        canvas.draw(massEle);
        canvas.cd(1);
        phiEle.setTitleY("Counts");
        phiEle.setFillColor(34);
        phiEle.setOptStat(1111);
        phiEle.setTitleX("phi of electron");
        canvas.draw(phiEle);
        
        canvas.cd(2);
        thetaEle.setTitleY("Counts");
        thetaEle.setFillColor(34);
        thetaEle.setOptStat(1111);
        thetaEle.setTitleX("theta of electron");
        canvas.draw(thetaEle);
        
        canvas.cd(3);
        phiTheta.setTitleX("theta of electron");
        phiTheta.setTitleY("phi of electron");
        canvas.draw(phiTheta);
        
        canvas.cd(4);
        momEle.setTitleY("Counts");
        momEle.setFillColor(34);
        momEle.setOptStat(1111);
        momEle.setTitleX("mom. of electron");
        canvas.draw(momEle);
        
        frame.add(canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
         

    
    }
    
    
    
}
