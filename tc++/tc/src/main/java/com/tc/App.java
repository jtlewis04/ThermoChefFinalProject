package com.tc;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {
        System.out.println("Reading data. Please hold...");
        ArduinoConnect.readArduino();
    }
   
    
}
