package com.tc;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Graph 
{
        private final int X_AXIS = 300;
        private final int Y_AXIS = 50;
        private ArrayList<Byte> x;
        private ArrayList<Byte> y;
        String name;
        PolynomialFunction tempFun;
    public Graph(ArrayList<Byte> x, ArrayList<Byte> y, String name)
    {
        PolynomialCurveFitter polyboy= PolynomialCurveFitter.create(2);
        WeightedObservedPoints data = new WeightedObservedPoints();
        this.x = x;
        this.y = y;
        this.name = name;
        for(int i = 0; i<x.size(); i++)
        {
            data.add(x.get(i),y.get(i));
            // System.out.println(x.get(i) + " " + y.get(i));
        }
        // Polynomial fit function created
        double[] coef = polyboy.fit(data.toList());
        tempFun = new PolynomialFunction(coef);
        // Drawing the graph
    }
        public void drawGraph()
        {
            JFrame frame = new JFrame("Graph of " + name);
        frame.setSize(500,500);
        frame.setVisible(true);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawLine(0,X_AXIS,500,X_AXIS);
                g.drawLine(Y_AXIS,0,Y_AXIS,500);
                g.setColor(Color.RED);
        for (int x = 0; x < 50; x++) {
            g.drawLine(x+Y_AXIS, X_AXIS-(int) tempFun.value(x), x + 1+Y_AXIS, X_AXIS-(int) tempFun.value(x + 1));
            System.out.println("Time: " + x + " " + "Predicted Temp: " + (int)tempFun.value(x));
        }
            }
        };
        panel.setSize(500, 500);
        panel.setVisible(true);
        frame.add(panel);
        }
    
}
