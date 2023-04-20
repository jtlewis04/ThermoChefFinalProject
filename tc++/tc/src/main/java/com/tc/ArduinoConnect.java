package com.tc;

import com.fazecast.jSerialComm.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.String;

public class ArduinoConnect {
    private static SerialPort comport;
    private static InputStream data;
    public static ArrayList<Integer> x;
    public static ArrayList<Integer> y;
    public static boolean read = true;

    public static void readArduino() throws IOException, InterruptedException {
        x = new ArrayList<>();
        y = new ArrayList<>();
        SerialPort[] allAvailableComPorts = SerialPort.getCommPorts();



        // Lists all available serial and connects to the first one
        // Prerequisite to function: Arduino Uno is the only free Serial connection
        for (SerialPort eachComPort : allAvailableComPorts)
            System.out.println("List of all available serial ports: " + eachComPort.getSystemPortName());
        comport = SerialPort.getCommPort(allAvailableComPorts[0].getSystemPortName());
        // No timeout
        // 9600 for Serial and expect 8 bits
        comport.setComPortTimeouts(0, 0, 0);
        comport.setComPortParameters(9600, 8, 1, 0);
        if (comport.openPort()) {
            System.out.println("Port open");
            out:
            while(read) {
                data = comport.getInputStream();
                if (data.available() > 0) {

                    // Creates a byte array to store all bytes in the Serial connection
                    
                    byte[] serialBuffer = new byte[data.available()];
                    // Assigns bytes from Serial connection to byte array
                    data.readNBytes(serialBuffer, 0, data.available());
                    if(serialBuffer.length>2){
                    if(serialBuffer[1]==28 && serialBuffer[2] == 32)
                    {
                        System.out.println("Two hour limit reached");
                        break out;
                    }
                    // Graph if receive 10,000 from Arduino
                    if(serialBuffer[1]==39 && serialBuffer[2] == 16)
                    {
                        System.out.println("Generating graph");
                        Graph graph1 = new Graph(x,y,1);
                        Graph graph2 = new Graph(x,y,2);
                        Graph graph3 = new Graph(x,y,3);
                        graph1.drawGraph();
                        graph2.drawGraph();
                        graph3.drawGraph();
                    }                    
                    }
                    // Correct time bytes once they are over 256
                    if(serialBuffer.length==3)
                    {
                        int combinedByte = (Byte.toUnsignedInt(serialBuffer[1])<<8) | Byte.toUnsignedInt(serialBuffer[2]);
                        System.out.println("Time: " + combinedByte);
                        System.out.println(serialBuffer[0] + " C");
                        x.add(combinedByte);
                        y.add(Byte.toUnsignedInt(serialBuffer[0]));
                    }
                    else
                    {
                        System.out.println(("Time: " + Byte.toUnsignedInt(serialBuffer[1])));
                        System.out.println((serialBuffer[0] + " C"));

                        x.add(Byte.toUnsignedInt(serialBuffer[1]));
                        y.add(Byte.toUnsignedInt(serialBuffer[0]));
                    }
                }
                // Timeout enables proper reading
                Thread.sleep(100);
            }
        }
    }
}
