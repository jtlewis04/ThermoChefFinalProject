package com.tc;

import com.fazecast.jSerialComm.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ArduinoConnect {
    private static SerialPort comport;
    private static InputStream data;
    public static ArrayList<Byte> x;
    public static ArrayList<Byte> y;

    public static void readArduino() throws IOException, InterruptedException {
        x = new ArrayList<>();
        y = new ArrayList<>();
        SerialPort[] allAvailableComPorts = SerialPort.getCommPorts();



        // Lists all available serial and connects to the first one
        // Prerequisite to function: Arduino Uno is the only free Serial connection
        for (SerialPort eachComPort : allAvailableComPorts)
            System.out.println("List of all available serial ports: " + eachComPort.getSystemPortName());
        comport = SerialPort.getCommPort(allAvailableComPorts[0].getSystemPortName());
        comport.setComPortTimeouts(0, 0, 0);
        comport.setComPortParameters(9600, 8, 1, 0);
        if (comport.openPort()) {
            System.out.println("Port open");
            for (int i = 0; i < 200; i++) {
                data = comport.getInputStream();
                if (data.available() > 0) {

                    // Creates a byte array to store all bytes in the Serial connection
                    byte[] serialBuffer = new byte[data.available()];
                    // Assigns bytes from Serial conenction to byte array
                    data.readNBytes(serialBuffer, 0, data.available());
                    System.out.println(serialBuffer[0] + " " + serialBuffer[1]);
                    // Adds to x and y ArrayLists
                    x.add(serialBuffer[1]);
                    y.add(serialBuffer[0]);
                }
                // Timeout enables proper reading
                Thread.sleep(50);
            }
        }
    }
}
