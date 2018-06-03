/*
 read hipo file and plot 
 */
package javapackage;
import static java.lang.Math.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;
import javax.swing.JFrame;
import static kotlin.KotlinPackage.any;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvas;

import org.jlab.io.hipo.*;
import org.jlab.io.base.*;
import org.jlab.clas.physics.GenericKinematicFitter;
import org.jlab.clas.physics.RecEvent;



public class genVsRec{
    private static Object particleArray;
    
    
    
    public static void main(String [] args){
        
        
        JFrame frame = new JFrame("Generated file histogram");
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        frame.setSize(800,500);
        H1F histogram = new H1F("histogram",100,-3.2,3.2);
        H2F histogram2 = new H2F("histogram2", 100, 0, 5, 100, -1,3.2);
        
        // define arraylist and hashmap to store generated data
        
        HashMap <String, ArrayList<Float>> particleData = new HashMap<>();
        // define arraylist and hashmap to store difference of gen and rec data
        ArrayList <Float> recArray = new ArrayList<>();
        HashMap <String, Float> recData = new HashMap<>();
       // List <ArrayList<Float>> group = new ArrayList<ArrayList<>>();
        ArrayList <Float> particleArray = new ArrayList<>();
        
        HipoDataSource reader = new HipoDataSource();
        reader.open("rec.hipo");
        int nevents = reader.getSize();
        System.out.println(nevents);
        
        int counter = 0;
        GenericKinematicFitter fitter = new GenericKinematicFitter(11.0);  
        
        for (int i=0; i < nevents; i++){
            DataEvent event = reader.getNextEvent();
            event.show();
           // System.out.println("Event ");
            if (event.hasBank("REC::Particle")== true){
                DataBank bank1 = event.getBank("MC::Particle");
                
              //  System.out.println(Arrays.toString(bank1.getByte("charge")));
                int colsGen = bank1.rows();
                int rowsGen = bank1.columns();
               // System.out.println(rowsGen);
                DataBank bank2 = event.getBank("REC::Particle");
               
                RecEvent recEvent = fitter.getRecEvent(event);
                int colsRec = bank2.rows();
                
                if ((colsRec >= 4)&&(colsGen == 4)){
                   // bank1.show();
                   // bank2.show();
                    
                    //System.out.print("Number of reconstructed particles: " + rowsRec);
                    //System.out.println("Total number of deltap need to calculate: " + rowsRec*rowsGen);
                    
                    // generated data for single events
                    for (int k=0; k< colsGen; k++){
                        //ArrayList <Float> particleArray_k = new ArrayList<>();
                        float pxGen = bank1.getFloat("px", k);
                        float pyGen = bank1.getFloat("py", k);
                        float pzGen = bank1.getFloat("pz", k);
                        float vxGen = bank1.getFloat("vx", k);
                        float vyGen = bank1.getFloat("vy", k);
                        float vzGen = bank1.getFloat("vz", k);
                        //System.out.println(k + " : px \t" + pxGen + " : py \t" + pyGen + " : pz \t" + pzGen);
                        //System.out.println(" ######## ");                               
                        // get charge and pid for generated events
                        int pid = bank1.getInt("pid", k);
                        
                        // make arraylist of all the properties from above
                        
                            particleArray.add(pxGen);
                            particleArray.add(pyGen);
                            particleArray.add(pzGen);
                            particleArray.add(vxGen);
                            particleArray.add(vyGen);
                            particleArray.add(vzGen);
                            float totalMom;
                            totalMom = (float) Math.sqrt(Math.pow(pxGen, 2) + Math.pow(pyGen, 2) + Math.pow(pzGen, 2));
                            particleArray.add(totalMom);
                            // System.out.println(totalMom);
                            
                            particleData.put(Integer.toString(pid), (ArrayList<Float>) particleArray.clone());
                            //particleArray.removeAll(particleArray);
                            particleArray.clear();
                    }
                    // is not updating hashmap
                    
                    //System.out.println("key value in hashmap " + particleData.get("321"));
                    // check the key and correcponding arraylist in hashmap
                    Set<String> m1Key = particleData.keySet();
                   
                    
                    // reconstructed data for the events
                    for (int j =0; j < colsRec; j++){
                        byte charge2;
                        charge2 = bank2.getByte("charge", j);
                        if (charge2 == 0) continue;
                        System.out.println("charge is :  " + charge2);
                        
                        float pxRec = bank2.getFloat("px", j);
                        float pyRec = bank2.getFloat("py", j);
                        float pzRec = bank2.getFloat("pz", j);
                        float vxRec = bank2.getFloat("vx", j);
                        float vyRec = bank2.getFloat("vy", j);
                        float vzRec = bank2.getFloat("vz", j);
                       // System.out.println(j + " : px \t" + pxRec + " : py \t" + pyRec + " : pz \t" + pzRec + " : vx \t" + vxRec + " : vy \t" + vyRec " : vz \t" + vzRec);
                 
                       // get generated data of all the keys 
                       float delMom=1000;
                       for (Entry<String, ArrayList<Float>> ee: particleData.entrySet() ){
                           String key = ee.getKey();
                           List <Float> values = ee.getValue();
                           float delpx = pxRec - values.get(0);
                           float delpy = pyRec - values.get(1);
                           float delpz = pzRec - values.get(2);
                           float delvx = vxRec - values.get(3);
                           float delvy = vyRec - values.get(4);
                           float delvz = vzRec - values.get(5);
                           
                           //System.out.println("key  " + key + " values 0 " + values.get(0) + "values 1 " + values.get(1) + "values 2 " + values.get(2));
                           
                           float deltaP = (float)Math.sqrt(Math.pow(delpx, 2) + Math.pow(delpy, 2) + Math.pow(delpz, 2) );
                           float totalMomRec;
                           totalMomRec = (float)Math.sqrt(Math.pow(pxRec, 2) + Math.pow(pyRec, 2) + Math.pow(pzRec, 2));
                           float delOverP = deltaP/values.get(6);
                           //recArray.add(delpx);
                           //recArray.add(delpy);
                           //recArray.add(delpz);
                           //recArray.add(deltaP);
                           //recArray.add(totalMomRec);
                           //recArray.add(delOverP);
                           //histogram2.fill(delOverP, values.get(6));
                           recData.put(key,delOverP);
                           //System.out.println(" calculated delta over p values " + " for " +  key + "\t" + delOverP);
                           
                           if ((delOverP < 0.1)&&(delOverP < delMom)){
                               System.out.println("Smallest delta value for event " + delOverP);
                               delMom = delOverP;
                           }
                       }  // for each generated event corresponding all reconstructed events
                       //System.out.println("Another columns on reconstructed data " + "+++++++++++ ");
                       Entry<String, Float> min = null;
                       for (Entry<String, Float> entry : recData.entrySet()) {
                            if (min == null || min.getValue() > entry.getValue()) {
                                min = entry;
                                
                            }
                    }
                    if ((charge2 == 1)&&("321".equals(min.getKey()))&&(min.getValue() < 0.5)){
                         
                        histogram.fill(min.getValue());
                        
                        
                        
                        //System.out.println("key " + min.getKey() + "value  "  + min.getValue());
                    }  
                       
                       
                        
                       
                      recData.clear();          
                    
                    }
                    
                  
                    
                     counter++;
                }    
                
               // System.out.println(rowsRec);
            }
            
         particleData.clear();
         recArray.clear();  
            
          
        }   // here end the single event
        
        
            
            
        
        
        histogram.setTitleX("delp/p for kaon");
        histogram.setTitleY("Counts");
        histogram.setFillColor(34);
        histogram.setOptStat(1111);
        canvas.getPad(0).setTitle("1d histogram");
        canvas.setFont("HanziPen TC");
        canvas.draw(histogram);
        frame.add(canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //canvas.save("pion.png");
        
       
        
        //System.out.println("  procecessed total events  " + counter + "  events");
        
        
        
        
        
        
    }

    private static String to_String(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




}





// get data from generated bank 
    
    //System.out.println("first column momentum " + bank.getFloat("px", 1));
            int colsRec = bank.rows();
            /*if (colsRec >= 4) {
                //for (int k=0; k< colsRec; k++){
                        float pxRec = bank.getFloat("px", 1);
                        System.out.println("x com. momentum " + pxRec);
                //}
            
            }*/
    
    
    
    
    