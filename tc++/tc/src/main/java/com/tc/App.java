package com.tc;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {
        System.out.println("Reading data. Please hold...");
        ArduinoConnect.readArduino();
        // Thread.sleep(1000);
        // Graph test = new Graph(ArduinoConnect.x, ArduinoConnect.y, "Demo Graph");
        // System.out.println("Generating graph");
        // test.drawGraph();
    }
   
    
}
