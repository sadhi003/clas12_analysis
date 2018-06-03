/*
 
 */
package javapackage;

import java.util.Random;
import javax.swing.JFrame;

import org.jlab.groot.data.H1F;
import org.jlab.groot.graphics.EmbeddedCanvas;

public class BasicDemo {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Generated file histogram");
        EmbeddedCanvas canvas = new EmbeddedCanvas();
        frame.setSize(800,500);
        H1F histogram = new H1F("histogram",100,0,9); 
        LundFileReader file = new LundFileReader(321,9);
        file.printLundInfo();
        file.loadFile("kpppim.lund");
        //System.out.println("ArrayList Size is " + file.getDataSize());
        System.out.println(file.getList().size());
        
        for(int i=0; i< file.getList().size(); i++){
            float momx = (float) Double.parseDouble(file.getList().get(i));
            histogram.fill(momx);
        }
        
        histogram.setTitleX("Generated file");
        histogram.setTitleY("Counts");
        histogram.setFillColor(34);
        histogram.setOptStat(1111);
        canvas.getPad(0).setTitle("Proton Momentum along x");
        canvas.setFont("HanziPen TC");
        canvas.draw(histogram);
        frame.add(canvas);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // another histograms 
        
        
    }

}


