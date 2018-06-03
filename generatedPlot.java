
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
public class generatedPlot{
    
    public static void main(String [] args){
        ShowBank obj = new ShowBank();
        // global function need to open (can keep in different class)
        obj.open_file("ppippim.hipo"); 
        obj.hipo_info();
        
        
        double phiAngle, thetaAngle;
       // obj.get_electron("REC::Particle");
        //ArrayList<LorentzVector> proton = new ArrayList<>(); 
        //proton = ;
        H1F energyParticle = new H1F("energyParticle","mass of electron", 100,0.,11.0);
        H1F phiParticle = new H1F("phiParticle","phi of electron", 100, -190.0,190.0);
        H1F thetaParticle = new H1F("thetaParticle","theta of electron",100, 0.,100.0);
        
        H2F phiTheta = new H2F("phiTheta", "theta vs phi", 100,0.0,90.0,100, -190.0,190.0);
        H2F betaVsP = new H2F("betaVsP", "beta vs P", 100,0.0,9.0,100, 0.0,1.3);
        H1F massParticle = new H1F("massParticle","mass of electron", 100,-1.0,1.0);
        H1F momParticle = new H1F("momParticle","momentum of electron", 100, 0.0,11.);
        
        int particleID = 211;
        double particleMass= 0.139;
        
        for (LorentzVector particle : obj.fourVectorGen("REC::Particle", particleID, particleMass)){
            energyParticle.fill(particle.e());
            phiAngle = particle.phi() * (180/3.1415);
            thetaAngle = particle.theta() *(180/3.1415);
            phiParticle.fill(phiAngle);
            thetaParticle.fill(thetaAngle);
            phiTheta.fill(thetaAngle,phiAngle);
            momParticle.fill(particle.p());
            massParticle.fill(particle.mass2());
        }
        
        
        JFrame frame = new JFrame("Reconstructed " + Integer.toString(particleID));
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        canvas.divide(2, 3);
        frame.setSize(800,800);
        
        energyParticle.setTitleX(Integer.toString(particleID) + " energy [GeV]");
        energyParticle.setTitleY("Counts");
        energyParticle.setFillColor(34);
        energyParticle.setOptStat(1111);
        canvas.getPad(0).setTitle("1d histogram");
        canvas.setFont("HanziPen TC");
        canvas.cd(0);
        canvas.draw(energyParticle);
        canvas.cd(1);
        phiParticle.setTitleY("Counts");
        phiParticle.setFillColor(34);
        phiParticle.setOptStat(1111);
        phiParticle.setTitleX("phi of " + Integer.toString(particleID));
        canvas.draw(phiParticle);
        
        canvas.cd(2);
        thetaParticle.setTitleY("Counts");
        thetaParticle.setFillColor(34);
        thetaParticle.setOptStat(1111);
        thetaParticle.setTitleX("theta of " + Integer.toString(particleID));
        canvas.draw(thetaParticle);
        
        canvas.cd(3);
        phiTheta.setTitleX("theta of electron");
        phiTheta.setTitleY("phi of " + Integer.toString(particleID));
        canvas.draw(phiTheta);
        
        canvas.cd(4);
        momParticle.setTitleY("Counts");
        momParticle.setFillColor(34);
        momParticle.setOptStat(1111);
        momParticle.setTitleX("mom. of " + Integer.toString(particleID));
        canvas.draw(momParticle);
        
        canvas.cd(5);
        massParticle.setTitleY("Counts");
        massParticle.setFillColor(34);
        massParticle.setOptStat(1111);
        massParticle.setTitleX("mass square of " + Integer.toString(particleID));
        canvas.draw(massParticle);
        
        frame.add(canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    
    }
    
    
    
}
