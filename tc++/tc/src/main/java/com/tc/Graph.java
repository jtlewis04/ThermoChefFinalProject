package com.tc;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
// Dependent on ArduinoConnect.java

public class Graph 
{
        private final int X_AXIS = 1250;
        private final int Y_AXIS = 50;
        private ArrayList<Integer> x;
        private ArrayList<Integer> y;
        PolynomialFunction tempFun;
        private int xSize;
        private int ySize;
        private int degree;
    public Graph(ArrayList<Integer> x, ArrayList<Integer> y, int degree)
    {
        PolynomialCurveFitter polyboy= PolynomialCurveFitter.create(degree);
        WeightedObservedPoints data = new WeightedObservedPoints();
        this.x = x;
        this.y = y;
        this.degree = degree;
        for(int i = 0; i<x.size(); i++)
        {
            data.add(x.get(i),Integer.valueOf(y.get(i)));
            // System.out.println(x.get(i) + " " + y.get(i));
        }
        // Polynomial fit function created
        double[] coef = polyboy.fit(data.toList());
        tempFun = new PolynomialFunction(coef);
        // Drawing the graph
        xSize = x.size();
        ySize = y.size();
    }
        public void drawGraph()
        {
            JFrame frame = new JFrame("Food Temperature over Time: Degree " + degree);
        frame.setSize(7200,7200);
        frame.setVisible(true);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)(g);
                g2d.setColor(Color.BLACK);
                Font font = new Font(Font.DIALOG, Font.PLAIN, 30);
                g2d.setFont(font);
                g2d.drawString("Time (s)", 1300, 1300);
                g2d.translate(35,800);
                g2d.rotate(-Math.PI/2);
                g2d.drawString("Temperature (C)", 0, 0);
                g2d.rotate(Math.PI/2);
                g2d.translate(-35,-800);
                g2d.drawLine(0,X_AXIS,7200,X_AXIS);
                g2d.drawLine(Y_AXIS,0,Y_AXIS,7200);
                g2d.setColor(Color.RED);
                boolean gb = true;
                boolean cb = true;
        for (int x = 0; x < 7200; x++) {
            g2d.drawLine(x+Y_AXIS, X_AXIS-(int) tempFun.value(x), x + 1+Y_AXIS, X_AXIS-(int) tempFun.value(x + 1));
        }
        g2d.setColor(Color.BLACK);
        for(int i = 0; i<10000; i++)
        {
            if((int)tempFun.value(i) == 63 && cb && (int)tempFun.value(i)!=Integer.MAX_VALUE && (int)tempFun.value(i)!=Integer.MIN_VALUE)
            {
                g2d.drawLine((int)(Y_AXIS + tempFun.value(i)),0,(int)(Y_AXIS+tempFun.value(i)),7200);
                if(i<60)
                {
                    g2d.drawString("Red Meat "+i+"s",500,X_AXIS + 25);
                }
                else{
                    g2d.drawString("Red Meat "+i/60+"min " + i%60+"s",500,X_AXIS + 25);
                }
                cb = false;
            }
            if((int)tempFun.value(i) == 71 && gb && (int)tempFun.value(i)!=Integer.MAX_VALUE && (int)tempFun.value(i)!=Integer.MIN_VALUE)
            {
                g2d.drawLine((int)(Y_AXIS + tempFun.value(i)),0,(int)(Y_AXIS+tempFun.value(i)),7200);
                if(i<60)
                {
                    g2d.drawString("Ground Beef "+i+"s",500,X_AXIS + 50);
                }
                else{
                    g2d.drawString("Ground Beef "+i/60+"min " + i%60+"s",500,X_AXIS + 50);
                }
                gb =false;
            }
            if((int)tempFun.value(i) == 74 && (int)tempFun.value(i)!=Integer.MAX_VALUE && (int)tempFun.value(i)!=Integer.MIN_VALUE)
            {
                if(i<60)
                {
                    g2d.drawString("Poultry "+i+"s",500,X_AXIS + 75);
                }
                else{
                    g2d.drawString("Poultry "+i/60+"min " + i%60+"s",500,X_AXIS + 75);
                }
                g2d.drawLine((int)(Y_AXIS + tempFun.value(i)),0,(int)(Y_AXIS+tempFun.value(i)),7200);
                break;
            }
        }
            }
        }; 
        panel.setSize(7200, 7200);
        panel.setVisible(true);
        frame.add(panel);
        for (int x = 0; x < 7200; x++) {
            System.out.println("Time: " + x + " " + "Predicted Temp: " + (int)tempFun.value(x));
        }
        }
    
}
