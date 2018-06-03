/*
 read hipo file and plot 
 */
package javapackage;
import static java.lang.Math.*;
import java.lang.reflect.Array;
import java.util.*;
import javax.swing.JFrame;
import org.jlab.groot.data.H1F;
import org.jlab.groot.graphics.EmbeddedCanvas;

import org.jlab.io.hipo.*;
import org.jlab.io.base.*;
import org.jlab.clas.physics.GenericKinematicFitter;
import org.jlab.clas.physics.RecEvent;



public class hipoReader{
    
    
    
    public static void main(String [] args){
        
        
        JFrame frame = new JFrame("Generated file histogram");
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        frame.setSize(800,500);
        H1F histogram = new H1F("histogram",100,-0.2,0.2); 
        List <Float> genMomX = new ArrayList<>();
        List <Float> genMomY = new ArrayList<>();
        List <Float> genMomZ = new ArrayList<>();
        List <Float> delPxA = new ArrayList<>();
        List <Float> delPyA = new ArrayList<>();
        List <Float> delPzA = new ArrayList<>();
        List <Float> totalPGen = new ArrayList<>();
        
        // define arraylist 
        ArrayList <Float> particleArray = new ArrayList<>();
        HashMap <String, ArrayList<Float>> particleData = new HashMap<>();
        
        
        HipoDataSource reader = new HipoDataSource();
        reader.open("rec.hipo");
        int nevents = reader.getSize();
        System.out.println(nevents);
        
        int counter = 0;
        GenericKinematicFitter fitter = new GenericKinematicFitter(11.0);
        for (int i=0; i < nevents; i++){
            DataEvent event = reader.getNextEvent();
            //event.show();
            
            if (event.hasBank("REC::Particle")== true){
                DataBank bank1 = event.getBank("MC::Particle");
                
              //  System.out.println(Arrays.toString(bank1.getByte("charge")));
                int colsGen = bank1.rows();
                int rowsGen = bank1.columns();
               // System.out.println(rowsGen);
                DataBank bank2 = event.getBank("REC::Particle");
               
                RecEvent recEvent = fitter.getRecEvent(event);
                int colsRec = bank2.rows();
                
                if (colsRec >= 3){
                    bank1.show();
                    bank2.show();
                    
                    //System.out.print("Number of reconstructed particles: " + rowsRec);
                    //System.out.println("Total number of deltap need to calculate: " + rowsRec*rowsGen);
                    
                    // generated data for single events
                    for (int k=0; k< colsGen; k++){
                        float pxGen = bank1.getFloat("px", k);
                        float pyGen = bank1.getFloat("py", k);
                        float pzGen = bank1.getFloat("pz", k);
                        genMomX.add(pxGen);
                        genMomY.add(pyGen);
                        genMomZ.add(pzGen);
                        int pid = bank1.getInt("pid", k);
                       // byte charge = bank1.getByte("charge", k);
                        float vxGen = bank1.getFloat("vx", k);
                        float vyGen = bank1.getFloat("vy", k);
                        float vzGen = bank1.getFloat("vz", k);
                        
                        // make arraylist of all the properties from above
                        
                        particleArray.add(pxGen);
                        particleArray.add(pyGen);
                        particleArray.add(pzGen);
                        particleArray.add(vxGen);
                        particleArray.add(vyGen);
                        particleArray.add(vzGen);
                        
                        
                        float totalMom = (float) (Math.pow(pxGen, 2) + Math.pow(pyGen, 2) + Math.pow(pzGen, 2));
                        particleArray.add(totalMom);
                       // System.out.println(totalMom);
                       
                        particleData.put(Integer.toString(pid),particleArray);
                        
                    }
                    
                    ArrayList<Float> testExtract = particleData.get(1);
                    
                    
                    
                    
                    
                    
                    // reconstructed data for the events
                    for (int j =0; j < colsRec; j++){
                        float pxRec = bank2.getFloat("px", j);
                        float pyRec = bank2.getFloat("py", j);
                        float pzRec = bank2.getFloat("pz", j);
                       // System.out.println(j + " : px \t" + pxRec + " : py \t" + pyRec + " : pz \t" + pzRec);
                        
                        for(float x : genMomX){
                            float delpx = pxRec - x;
                            delPxA.add(delpx);
                            //System.out.print(delpx+"\n");
                        }
                        
                        for(float y : genMomY){
                            float delpy = pyRec - y;
                            delPyA.add(delpy);
                           // System.out.print(delpy+"\n");
                        }
                        
                        for(float z : genMomZ){
                            float delpz = pzRec - z;
                            delPzA.add(delpz);
                           // System.out.print(delpz+"\n");
                        }
                        
                        
                       // if (abs(delpx) < 0.05){
                       //     histogram.fill(delpx);
                      //  }
                                
                    
                    }
                    
                   // System.out.println("Total number of delta's calculated " + delPxA.size()*delPyA.size()*delPzA.size());
                    for (int m=0; m < delPxA.size(); m++){
                        
                        float deltaP = (float)Math.sqrt(Math.pow(delPxA.get(m), 2) + Math.pow(delPyA.get(m), 2) + Math.pow(delPzA.get(m), 2) );
                        //System.out.println("For particle \t" + m + " \t calculated deltap \t" + deltaP);
                        
                        if (deltaP < 0.1){
                            //System.out.println( " Delta p less than 100 mev" + deltaP);
                            histogram.fill(deltaP);
                        }
                    }
                    
                    
                     counter++;
                }
                
               // System.out.println(rowsRec);
            }
           genMomX.clear();
           genMomY.clear();
           genMomZ.clear();
           delPxA.clear();
           delPyA.clear();
           delPzA.clear();
        }
        
        
        histogram.setTitleX("delta p for kaon");
        histogram.setTitleY("Counts");
        histogram.setFillColor(34);
        histogram.setOptStat(1111);
        canvas.getPad(0).setTitle("Mom. difference");
        canvas.setFont("HanziPen TC");
        canvas.draw(histogram);
        frame.add(canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
       
        
        //System.out.println("  procecessed total events  " + counter + "  events");
        
        
        
        
        
        
    }




}

class DataReader{
    HipoDataSource reader = new HipoDataSource();
    DataEvent event = reader.getNextEvent();
    DataBank bank1 = event.getBank("REC::particle");
    DataBank bank2 = event.getBank("MC::Particle");
    ArrayList <Float> particleArray = new ArrayList<>();
    HashMap <String, ArrayList<Float>> particleData = new HashMap<>(); 
    
    
    
    
    
    // get charge of the particles
    public byte[] getCharge(){
        return this.bank2.getByte("charge");
    }
    // get pid of the particles 
    public int[] getPID(){
        return this.bank2.getInt("pid");
    }
    
    
}